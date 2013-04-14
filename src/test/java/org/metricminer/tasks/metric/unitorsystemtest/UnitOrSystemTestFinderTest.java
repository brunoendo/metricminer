package org.metricminer.tasks.metric.unitorsystemtest;

import static br.com.aniche.msr.tests.ParserTestUtils.toInputStream;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.model.SourceCode;
import org.mockito.Mockito;

public class UnitOrSystemTestFinderTest {
	private UnitOrSystemTestFinder metric;
	private SourceCode source;

	@Before
	public void setUp() {
		this.metric = new UnitOrSystemTestFinder();
		this.source = Mockito.mock(SourceCode.class);
	}
	
	@Test
	public void shouldTellThatIsAnIntegrationTestIfConstructorReceivesConcreteStuff() {
		metric.calculate(source,
				toInputStream(
					"import a.b.Dep1;" +
					"import a.b.Dep2;" +
					"class BlaTest {" +
						"@Test " +
						"public void aSimpleTest() {" +
							"Dep1 x = new Dep1();" +
							"Dep2 y = new Dep2();" +
							"Bla obj = new Bla(x, y);" +
							"obj.method1();" +
							"assertEquals(obj.isAbc());" +
						"}" +
					"}"
								));
		
		assertTrue(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	@Test
	public void shouldTellThatIsAnIntegrationTestIfConstructorReceivesConcreteStuffWithCot() {
		metric.calculate(source,
				toInputStream(
					"import a.b.Dep1;" +
					"import a.b.Dep2;" +
					"class BlaTest {" +
						"@Test " +
						"public void aSimpleTest() {" +
							"Dep1 x = new Dep1(new TT());" +
							"Dep2 y = new Dep2(new KK());" +
							"Bla obj = new Bla(x, y);" +
							"obj.method1();" +
							"assertEquals(obj.isAbc());" +
						"}" +
					"}"
						));
		
		assertTrue(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	
	@Test
	public void shouldTellThatIsAUnitTestIfConstructorReceivesConcreteStuffFromSamePackage() {
		metric.calculate(source,
				toInputStream(
						"class BlaTest {" +
							"@Test " +
							"public void aSimpleTest() {" +
								"Dep1 x = new Dep1();" +
								"Dep2 y = new Dep2();" +
								"Bla obj = new Bla(x, y);" +
								"obj.method1();" +
								"assertEquals(obj.isAbc());" +
							"}" +
						"}"
						));
		
		assertFalse(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	
	@Test
	public void shouldTellThatIsAUnitTestIfConstructorReceivesConcreteStuffWithCtorFromSamePackage() {
		metric.calculate(source,
				toInputStream(
						"class BlaTest {" +
							"@Test " +
							"public void aSimpleTest() {" +
								"Dep1 x = new Dep1(new TT());" +
								"Dep2 y = new Dep2(new ZZ());" +
								"Bla obj = new Bla(x, y);" +
								"obj.method1();" +
								"assertEquals(obj.isAbc());" +
							"}" +
						"}"
						));
		
		assertFalse(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	
	@Test
	public void shouldTellThatIsAUnitTestIfConstructorReceivesConcreteAndPrimitiveStuffFromSamePackage() {
		metric.calculate(source,
				toInputStream(
						"class BlaTest {" +
							"@Test " +
							"public void aSimpleTest() {" +
								"Dep1 x = new Dep1();" +
								"Bla obj = new Bla(x, 1, 3.5, \"bla\");" +
								"obj.method1();" +
								"assertEquals(obj.isAbc());" +
							"}" +
						"}"
						));
		
		assertFalse(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	@Test
	public void shouldTellThatIsAnIntegrationUnitTestIfConstructorReceivesConcreteAndPrimitiveStuff() {
		metric.calculate(source,
				toInputStream(
						"import a.b.Dep1;" +
						"class BlaTest {" +
							"@Test " +
							"public void aSimpleTest() {" +
								"Dep1 x = new Dep1();" +
								"Bla obj = new Bla(x, 1, 3.5, \"bla\");" +
								"obj.method1();" +
								"assertEquals(obj.isAbc());" +
							"}" +
						"}"
						));
		
		assertTrue(metric.getTests().get("aSimpleTest").isIntegration());
		
	}
	
	@Test
	public void shouldUnderstandTwoTestsInARow() {
		metric.calculate(source,
				toInputStream(
					"import a.b.Dep1;" +
					"class BlaTest {" +
					
						"@Test " +
						"public void aSimpleTest1() {" +
							"Dep1 x = new Dep1();" +
							"Bla obj = new Bla(x, 1, 3.5, \"bla\");" +
							"obj.method1();" +
							"assertEquals(obj.isAbc());" +
						"}" +
					
						"@Test " +
						"public void aSimpleTest2() {" +
							"Dep2 x = new Dep2();" +
							"Bla obj = new Bla(x, 1, 3.5, \"bla\");" +
							"obj.method1();" +
							"assertEquals(obj.isAbc());" +
						"}" +
						
					"}"
						));
		
		assertTrue(metric.getTests().get("aSimpleTest1").isIntegration());
		assertFalse(metric.getTests().get("aSimpleTest2").isIntegration());
		
	}
}
