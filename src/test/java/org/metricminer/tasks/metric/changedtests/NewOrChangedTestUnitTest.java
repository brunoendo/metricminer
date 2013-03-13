package org.metricminer.tasks.metric.changedtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static br.com.aniche.msr.tests.ParserTestUtils.classDeclaration;
import static br.com.aniche.msr.tests.ParserTestUtils.toInputStream;


import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.model.Artifact;
import org.metricminer.model.Commit;
import org.metricminer.model.Modification;
import org.metricminer.model.ModificationKind;
import org.metricminer.model.SourceCode;
import org.mockito.Mockito;

public class NewOrChangedTestUnitTest {

	private NewOrChangedTestUnit metric;
	private Artifact artifact;
	private Commit commit;

	@Before
	public void setUp() {
		commit = Mockito.mock(Commit.class);
		artifact = Mockito.mock(Artifact.class);
		metric = new NewOrChangedTestUnit();
	}
	
	@Test
	public void shouldDetectANewTest() {
		
		String diff = 
				"+ @Test\n" + 
				"+ public void someTest() {\n" +
				"+ // something\n" +
				"+ }";
		
		SourceCode sc = new SourceCode(new Modification(diff, commit, artifact, ModificationKind.NEW), "source");
		
		metric.calculate(sc, toInputStream(classDeclaration(diff.replace("+", ""))));
		
		Set<String> tests = metric.newTests();
		assertTrue(tests.contains("someTest"));
	}

	@Test
	public void shouldDetectIfNotNewTest() {
		
		String diff = 
				"@Test\n" + 
				"public void someTest() {\n" +
				"// something\n" +
				"+ // bla bla bla\n" +
				"}";
		
		SourceCode sc = new SourceCode(new Modification(diff, commit, artifact, ModificationKind.NEW), "source");
		
		metric.calculate(sc, toInputStream(classDeclaration(diff.replace("+", ""))));
		
		Set<String> tests = metric.newTests();
		assertFalse(tests.contains("someTest"));
	}
	
	@Test
	public void shouldDetectIfTestWasChanged() {
		
		String diff = 
				"+ // new\n";
		
		String fullTestClass = "class Test {\n" +
				"@Test\n" +
				"public void someTest() {\n" +
				"   // new\n" +
				"}\n" +
				"}\n";

		SourceCode sc = new SourceCode(new Modification(diff, commit, artifact, ModificationKind.NEW), fullTestClass);
		
		metric.calculate(sc, toInputStream(fullTestClass));
		
		Set<String> newTests = metric.newTests();
		assertFalse(newTests.contains("someTest"));

		Set<String> modifiedTests = metric.modifiedTests();
		assertTrue(modifiedTests.contains("someTest"));
	}
}

