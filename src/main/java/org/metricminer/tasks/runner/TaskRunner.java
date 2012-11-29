package org.metricminer.tasks.runner;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.TaskDao;
import org.metricminer.model.Task;
import org.metricminer.tasks.RunnableTaskFactory;
import org.metricminer.tasks.TaskQueueStatus;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.ioc.PrototypeScoped;
import br.com.caelum.vraptor.tasks.scheduler.Scheduled;

@PrototypeScoped
@Scheduled(cron = "0/10 * * * * ?")
public class TaskRunner implements br.com.caelum.vraptor.tasks.Task {

    TaskDao taskDao;
    Task taskToRun;
    Session daoSession;
    Session taskSession;
    StatelessSession statelessSession;
    Logger log;
    private TaskQueueStatus queueStatus;
    private MetricMinerConfigs config;

    public TaskRunner(TaskQueueStatus status, SessionFactory sessionFactory, Container iocContainer) {
        this.queueStatus = status;
        this.config = status.getConfigs();
        this.daoSession = sessionFactory.openSession();
        this.taskSession = sessionFactory.openSession();
        this.statelessSession = sessionFactory.openStatelessSession();
        this.taskDao = new TaskDao(daoSession);
        log = Logger.getLogger(TaskRunner.class);
    }

    @Override
    public void execute() {
        try {
            taskToRun = taskDao.getFirstQueuedTask();
            if (shouldNotStartTaskYet()) {
                if (taskHasFailedDependency()) {
                    failTask();
                }
                closeOpenedSessions();
            } else {
                log.info("Starting task: " + taskToRun);
                runTask();
                finishTask();
            }
        } catch (Throwable e) {
            handleError(e);
        } finally {
            closeOpenedSessions();
        }
    }

    private void failTask() {
        log.error(taskToRun + " failed because a dependency for this task also failed.");
        taskToRun.setFailed();
        update(taskToRun);
    }


    private void runTask() throws InstantiationException,
            IllegalAccessException {
        taskToRun.setStarted();
        update(taskToRun);
        queueStatus.addRunningTask(taskToRun, Thread.currentThread());
        RunnableTaskFactory runnableTaskFactory = (RunnableTaskFactory) taskToRun
                .getRunnableTaskFactoryClass().newInstance();
        taskSession.beginTransaction();
        log.debug("Running task");
        runnableTaskFactory.build(taskToRun, taskSession, statelessSession,
                config).run();
    }

    private void finishTask() {
        taskToRun.setFinished();
        Transaction transaction = taskSession.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
        queueStatus.finishCurrentTask(taskToRun);
        Transaction tx = daoSession.beginTransaction();
        taskDao.update(taskToRun);
        log.info("Finished running task: " + taskToRun);
        tx.commit();
        System.gc();
    }

    private void handleError(Throwable e) {
        taskToRun.setFailed();
        queueStatus.finishCurrentTask(taskToRun);
        Transaction tx = daoSession.beginTransaction();
        taskDao.update(taskToRun);
        tx.commit();
        log.error("Error while running task " + taskToRun, e);
    }

    private void closeOpenedSessions() {
        if (daoSession.isOpen())
            daoSession.close();
        if (taskSession.isOpen())
            taskSession.close();
        try {
            statelessSession.close();
        } catch (SessionException e) {
            log.error("Failed to close StatelessSession", e);
        }
    }
    
    private void update(Task t) {
        daoSession.beginTransaction();
        daoSession.update(t);
        daoSession.getTransaction().commit();
    }
    
    private boolean taskHasFailedDependency() {
        return taskToRun != null && taskToRun.hasFailedDependencies();
    }
    
    private boolean shouldNotStartTaskYet() {
        return !queueStatus.mayStartTask() || taskToRun == null
                || !taskToRun.isDependenciesFinished();
    }

}
