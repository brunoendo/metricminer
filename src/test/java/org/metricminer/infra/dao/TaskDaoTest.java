package org.metricminer.infra.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.builder.ProjectBuilder;
import org.metricminer.model.Project;
import org.metricminer.model.Query;
import org.metricminer.model.Task;
import org.metricminer.model.TaskBuilder;
import org.metricminer.model.TaskConfigurationEntryKey;
import org.metricminer.model.TaskStatus;

public class TaskDaoTest extends DaoTest {

	private static TaskDao taskDao;

	@Before
	public void setUp() {
	    taskDao = new TaskDao(session);
	}

	@Test
	public void shouldGetLastTenFinishedTasks() throws Exception {
		String oldestTaskName = "should not appear";
		Project project = new ProjectBuilder().build();
		Task task = new TaskBuilder().withName(oldestTaskName)
				.forProject(project).build();
		task.setFinished();
		session.save(project);
		session.save(task);
		Thread.sleep(1000);
		for (int i = 0; i < 10; i++) {
			project = new ProjectBuilder().build();
			task = new TaskBuilder().withName("task " + i)
					.forProject(project).build();
			task.setFinished();
			session.save(project);
			session.save(task);
		}
		session.flush();

		List<Task> lastTasks = taskDao.lastTasks(10);

		assertEquals(10, lastTasks.size());
		for (Task t : lastTasks) {
			assertNotSame(oldestTaskName, t.getName());
		}
	}
	
	@Test
    public void shouldGetStartedTasks() throws Exception {
	    Project project = new Project();
        Task task = new TaskBuilder().forProject(project).build();
        task.setStarted();
        session.save(project);
        session.save(task);
        
        task = new TaskBuilder().forProject(project).build();
        task.setFinished();
        session.save(task);
        
        List<Task> tasksRunning = taskDao.tasksRunning();
        
        assertEquals(1, tasksRunning.size());
        assertEquals(TaskStatus.STARTED, tasksRunning.get(0).getStatus());
    }
	
	@Test
    public void shouldFindTaskScheduledToRunQuery() throws Exception {
	    Project project = new Project();
        Task queuedTaskToRunQuery = new TaskBuilder().forProject(project).build();
        queuedTaskToRunQuery.addTaskConfigurationEntry(TaskConfigurationEntryKey.QUERY_ID, "1");
        session.save(project);
        session.save(queuedTaskToRunQuery);
        
        Query queryScheduled = new Query(1l);
        session.save(queryScheduled);
        
        Task otherTask = new TaskBuilder().forProject(project).build();
        queuedTaskToRunQuery.addTaskConfigurationEntry(TaskConfigurationEntryKey.QUERY_ID, "2");
        session.save(project);
        session.save(otherTask);
        
        Query otherScheduled = new Query(2l);
        session.save(otherScheduled);
        
        session.flush();
        
        List<Task> tasks = taskDao.findTasksScheduledToRunQuery(queryScheduled);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
    }

}
