package org.metricminer.tasks.runner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.metricminer.config.ClassScan;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.TaskDao;
import org.metricminer.model.Task;
import org.metricminer.tasks.RunnableTask;
import org.metricminer.tasks.RunnableTaskFactory;
import org.metricminer.tasks.TaskQueueStatus;
import org.metricminer.tasks.ThreadInspector;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.ioc.PrototypeScoped;

public class TasksRunnerTest {

	private Session mockedDaoSession;
	private TaskRunner taskRunner;
	private TaskDao mockedDao;
	private Session mockedTaskSession;
	private StatelessSession mockedStatelessSession;
    private Container iocContainer;

	@Before
	public void setUp() {
		SessionFactory sf = mock(SessionFactory.class);
		ServletContext context = mock(ServletContext.class);
		when(context.getRealPath("/WEB-INF/metricminer.properties"))
				.thenReturn("src/test/resources/metricminer.properties");
		
		mockedDaoSession = mock(Session.class);
		mockedTaskSession = mock(Session.class);
		mockedStatelessSession = mock(StatelessSession.class);
		mockedDao = mock(TaskDao.class);
		Transaction mockedTransaction = mock(Transaction.class);
		when(mockedDaoSession.beginTransaction()).thenReturn(mockedTransaction);
        when(mockedDaoSession.getTransaction()).thenReturn(mockedTransaction);
        when(mockedTaskSession.getTransaction()).thenReturn(mockedTransaction);
		
        MetricMinerConfigs configs = new MetricMinerConfigs(new ClassScan(), context);
        TaskQueueStatus status = new TaskQueueStatus(configs, new ThreadInspector());
        
        iocContainer = mock(Container.class);
        taskRunner = new TaskRunner(status, sf, iocContainer);
        
		taskRunner.daoSession = mockedDaoSession;
		taskRunner.taskSession = mockedTaskSession;
		taskRunner.taskDao = mockedDao;
		taskRunner.statelessSession = mockedStatelessSession;
	}

	@Test
	public void shouldRunATaskWithoutDependencies() throws Exception {
		Task mockedTask = mockTask(true, false);

		taskRunner.execute();
		verify(mockedTask).setStarted();
		verify(mockedTask, never()).setFailed();
	}


	@Test
	public void shouldNotRunATaskWithIncompleteDependencies() throws Exception {
	    Task mockedTask = mockTask(false, false);

		taskRunner.execute();
		verify(mockedTask, never()).setStarted();
		verify(mockedTask, never()).setFailed();
	}
	
	@Test
	public void shouldFailATaskWithFailedDependencies() throws Exception {
	    Task mockedTask = mockTask(false, true);
	    
	    taskRunner.execute();
	    verify(mockedTask, never()).setStarted();
	    verify(mockedTask).setFailed();
	}
	
	private Task mockTask(boolean hasIncompleteDependency, boolean hasFailedDependency) throws InstantiationException, IllegalAccessException {
	    Task mockedTask = mock(Task.class);
	    when(mockedDao.getFirstQueuedTask()).thenReturn(mockedTask);
	    when(mockedTask.isDependenciesFinished()).thenReturn(hasIncompleteDependency);
	    when(mockedTask.hasFailedDependencies()).thenReturn(hasFailedDependency);
        when(mockedTask.getRunnableTaskFactoryClass()).thenReturn(TestRunnableTaskFactory.class);
        when(iocContainer.instanceFor(TestRunnableTaskFactory.class)).thenReturn(new TestRunnableTaskFactory());
	    return mockedTask;
	}

}

@Component @PrototypeScoped
class TestRunnableTaskFactory implements RunnableTaskFactory {
    
    @Override
    public RunnableTask build(Task task, Session session,
            StatelessSession statelessSession, MetricMinerConfigs config) {
        return new TestRunnableTask();
    }
    
}
class TestRunnableTask implements RunnableTask{
    @Override
    public void run() {
        System.out.println("running test task");
    }
}
