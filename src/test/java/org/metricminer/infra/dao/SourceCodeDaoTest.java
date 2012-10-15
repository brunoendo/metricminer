package org.metricminer.infra.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.AssertFalse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.builder.ProjectBuilder;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.model.Artifact;
import org.metricminer.model.ArtifactKind;
import org.metricminer.model.Commit;
import org.metricminer.model.Modification;
import org.metricminer.model.ModificationKind;
import org.metricminer.model.Project;
import org.metricminer.model.SourceCode;

public class SourceCodeDaoTest extends DaoTest {

	private SourceCodeDao sourceCodeDAO;

	@Before
	public void setUp() {
		sourceCodeDAO = new SourceCodeDao(statelessSession);
	}

	@Test
	public void shouldGetAllSourceCodeIdsFromProject() throws Exception {
		MetricMinerConfigs config = mock(MetricMinerConfigs.class);
		when(config.getRepositoriesDir()).thenReturn("/tmp");
		Project project = new ProjectBuilder().withName("project").build();
		Project otherProject = new ProjectBuilder().withName("other project").build();

		saveProjectData(project, otherProject);
		
		commit(); // don't know why

		Map<Long, String> idsMap = sourceCodeDAO
				.listSourceCodeIdsAndNamesFor(project, 0);
		List<Entry<Long, String>> idsProject = new ArrayList<Entry<Long, String>>(
				idsMap.entrySet());
		idsMap = sourceCodeDAO.listSourceCodeIdsAndNamesFor(otherProject, 0);
		List<Entry<Long, String>> idsOtherProject = new ArrayList<Entry<Long, String>>(
				idsMap.entrySet());

		assertEquals(35, idsProject.size());
		assertEquals(12, idsOtherProject.size());
		for (Entry<Long, String> entry : idsOtherProject) {
			assertNotNull(entry.getValue());
		}
	}
	
	@Test
	public void shouldGetSourceCodesFromIds() throws Exception {
		MetricMinerConfigs config = mock(MetricMinerConfigs.class);
		when(config.getRepositoriesDir()).thenReturn("/tmp");
		Project project = new ProjectBuilder().withName("project").build();
		Project otherProject = new ProjectBuilder().withName("other project").build();
		saveProjectData(project, otherProject);
		
		
		commit(); // don't know why

		Map<Long, String> idsAndNamesMap = sourceCodeDAO
				.listSourceCodeIdsAndNamesFor(project, 0);
		ArrayList<Entry<Long, String>> entries = new ArrayList<Entry<Long, String>>(
				idsAndNamesMap.entrySet());
		List<SourceCode> sources = sourceCodeDAO.listSourcesOf(project, entries
				.get(0).getKey(), entries.get(entries.size() - 1).getKey());

		assertEquals(entries.size(), sources.size());
	}

    private void commit() {
        session.flush();
        session.clear();
        session.getTransaction().commit();
    }

	private void saveProjectData(Project project, Project otherProject) {
		session.save(project);
		session.save(otherProject);
		Artifact A = new Artifact("A.java", ArtifactKind.CODE, project);
		session.save(A);
		Artifact B = new Artifact("B.java", ArtifactKind.CODE, project);
		session.save(B);
		Artifact C = new Artifact("C.java", ArtifactKind.CODE, otherProject);
		session.save(C);
		saveSourceCodesFor(A, 10);
		saveSourceCodesFor(B, 25);
		saveSourceCodesFor(C, 12);
	}
	
	@Test
    public void shouldFindSourceCodeById() throws Exception {
        SourceCode sourceCode = new SourceCode(null, "");
        statelessSession.insert(sourceCode);
        SourceCode sc = sourceCodeDAO.findByIdAndSourceSize(1L);
        assertEquals(Long.valueOf(1L), sc.getId());
    }
	
	@Test
	public void shouldFindSourceCodeByIdsArray() throws Exception {
		ArrayList<Long> ids = new ArrayList<Long>();
		
		ids.add(insertSourceWithContents("first").getId());
		ids.add(insertSourceWithContents("second").getId());
		insertSourceWithContents("not in query");
		ids.add(insertSourceWithContents("third").getId());
		
		
		List<SourceCode> sources = sourceCodeDAO.findWithIds(ids);
		assertEquals(3, sources.size());
		assertFalse(sources.get(0).getSource().equals("not in query"));
		assertFalse(sources.get(1).getSource().equals("not in query"));
		assertFalse(sources.get(2).getSource().equals("not in query"));
	}

	private SourceCode insertSourceWithContents(String name) {
		SourceCode sourceCode = new SourceCode(null, name);
		statelessSession.insert(sourceCode);
		return sourceCode;
	}

	private void saveSourceCodesFor(Artifact a, int n) {
		for (int i = 0; i < n; i++) {
			Commit commit = new Commit();
			Modification modification = new Modification("lala", commit, a, ModificationKind.DEFAULT);
			session.save(commit);
			session.save(modification);
			session.save(new SourceCode(modification, "some code " + i));
		}
	}
}
