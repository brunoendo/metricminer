package org.metricminer.infra.dao;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.builder.ProjectBuilder;
import org.metricminer.model.Artifact;
import org.metricminer.model.ArtifactKind;
import org.metricminer.model.Author;
import org.metricminer.model.CalculatedMetric;
import org.metricminer.model.Commit;
import org.metricminer.model.CommitMessage;
import org.metricminer.model.Diff;
import org.metricminer.model.Modification;
import org.metricminer.model.ModificationKind;
import org.metricminer.model.Project;
import org.metricminer.model.Task;
import org.metricminer.model.TaskBuilder;
import org.metricminer.tasks.metric.lcom.LComMetricFactory;

public class ProjectDaoTest extends DaoTest {
    
    private ProjectDao projectDao;

    @Before
    public void setUp() {
        projectDao = new ProjectDao(session);
    }

	@Test
	public void shouldGetCommitCount() {
		Project projectWithThreeCommits = aProjectWithCommits(3,
				Calendar.getInstance());
		Project projectWithTwoCommits = aProjectWithCommits(5,
				Calendar.getInstance());
		Project projectWithTenCommits = aProjectWithCommits(10,
				Calendar.getInstance());
		session.flush();
		assertEquals((Long) 3l,
				projectDao.commitCountFor(projectWithThreeCommits));
		assertEquals((Long) 5l,
				projectDao.commitCountFor(projectWithTwoCommits));
		assertEquals((Long) 10l,
				projectDao.commitCountFor(projectWithTenCommits));
	}

	@Test
	public void shouldGetCommitersCount() throws Exception {
		Project project = new ProjectBuilder().build();
		Author author1 = new Author();
		Author author2 = new Author();
		session.save(project);
		session.save(author1);
		session.save(author2);
		addCommitsOfAuthor(2, project, author1, Calendar.getInstance());
		addCommitsOfAuthor(2, project, author2, Calendar.getInstance());

		Project project2 = new ProjectBuilder().build();
		Author author3 = new Author();
		Author author4 = new Author();
		Author author5 = new Author();
		Author author6 = new Author();
		session.save(project2);
		session.save(author3);
		session.save(author4);
		session.save(author5);
		session.save(author6);
		addCommitsOfAuthor(10, project2, author3, Calendar.getInstance());
		addCommitsOfAuthor(5, project2, author4, Calendar.getInstance());
		addCommitsOfAuthor(21, project2, author5, Calendar.getInstance());
		addCommitsOfAuthor(33, project2, author6, Calendar.getInstance());

		assertEquals((Long) 2l, projectDao.commitersCountFor(project));
		assertEquals((Long) 4l, projectDao.commitersCountFor(project2));
	}

	@Test
	public void shouldGetCommitCountForLastMonths() {
		Project project = new ProjectBuilder().build();
		Author author = new Author();
		session.save(author);
		session.save(project);
		Calendar calendar;
		for (int i = 0; i < 12; i++) {
			calendar = Calendar.getInstance();
			calendar.set(2012, i, 10);
			addCommitsOfAuthor(i, project, author, calendar);
		}
		// some old commits
		calendar = Calendar.getInstance();
		calendar.set(2011, 10, 11);
		addCommitsOfAuthor(5, project, author, calendar);
		calendar = Calendar.getInstance();
		calendar.set(2011, 9, 11);
		addCommitsOfAuthor(5, project, author, calendar);
		session.flush();

		Map<Calendar, Long> lastCommits = projectDao
				.commitCountForLastMonths(project);
		assertEquals(12, lastCommits.size());
		for (Entry<Calendar, Long> entry : lastCommits.entrySet()) {
			int month = entry.getKey().get(Calendar.MONTH);
			assertEquals(month, (long) entry.getValue());
		}

	}
	
	@Test
	public void shouldFindTenProjectCount() throws Exception {
		for (int i = 0; i < 10; i++)
			session.save(new ProjectBuilder().build());
		Long totalProjectCount = projectDao.totalProjects();
		assertEquals(new Long(10L), totalProjectCount);
	}
	
	@Test
	public void shouldGetLastTenProjects() throws Exception {
		session.save(new ProjectBuilder().withName("this should not appear").build());
		for (int i = 0; i < 10; i++) {
			Calendar date = Calendar.getInstance();
			date.add(Calendar.SECOND, i);
			session.save(new ProjectBuilder()
			    .withCreationDate(date)
			    .withName("new project " + i)
			    .build());
		}
		List<Project> projects = projectDao.tenNewestProjects();
		int i = 9;
		for (Project project : projects) {
			assertEquals("new project " + i, project.getName());
			i--;
		}
	}
	
	@Test
    public void shouldGetTotalPages() throws Exception {
	    for (int i = 0; i < 100; i++) {
            session.save(new ProjectBuilder().withName("new project " + i).build());
        }
	    assertEquals((int)Math.ceil(100.0/ProjectDao.PAGE_SIZE), projectDao.totalPages());
    }
	
	@Test
	public void shouldGetFileCountPerCommit() throws Exception {
	    Project project = new ProjectBuilder().build();
        Author author1 = new Author();
        Commit commit = new Commit("message", author1, Calendar.getInstance(), new CommitMessage(""), new Diff(""), "",
                project);
        Artifact artifact1 = new Artifact("name", ArtifactKind.CODE, project);
        Artifact artifact2 = new Artifact("name", ArtifactKind.CODE, project);
        Modification modification1 = new Modification("", commit, artifact1, ModificationKind.NEW);
        commit.addModification(modification1);
        Modification modification2 = new Modification("", commit, artifact2, ModificationKind.NEW);
        commit.addModification(modification2);
        session.save(project);
        session.save(author1);
        session.save(artifact1);
        session.save(artifact2);
        session.save(modification1);
        session.save(modification2);
        session.save(commit);

	    Map<Commit, Long> countPerCommit = projectDao.fileCountPerCommitForLastSixMonths(project);
	    ArrayList<Entry<Commit, Long>> results = new ArrayList<Entry<Commit, Long>>(countPerCommit.entrySet());
	    assertEquals(1, results.size());
	    assertEquals(Long.valueOf(2l), results.get(0).getValue());
	}
	
	@Test
    public void shouldDeleteProject() throws Exception {
	    Project project = new ProjectBuilder().withName("some project").build();
	    Task task = new TaskBuilder().build();
	    CalculatedMetric calculatedMetric = new CalculatedMetric(project, 
	    		LComMetricFactory.class);
        project.addTask(task);
        project.addCalculatedMetric(calculatedMetric);
	    session.save(project);
	    session.save(task);
	    session.save(calculatedMetric);
	    session.flush();
	    projectDao.delete(project.getId());
	    session.flush();
    }

	private Project aProjectWithCommits(int totalCommits, Calendar commitDate) {
		Project project = new ProjectBuilder().build();
		session.save(project);
		Author author = new Author();
		session.save(author);
		addCommitsOfAuthor(totalCommits, project, author, commitDate);
		return project;
	}
	

	private void addCommitsOfAuthor(int totalCommits, Project project,
			Author author, Calendar commitDate) {
		for (int i = 0; i < totalCommits; i++) {
			session.save(new Commit("" + i, author, commitDate, new CommitMessage(""), new Diff(""), "",
					project));
		}
	}
	
	

}
