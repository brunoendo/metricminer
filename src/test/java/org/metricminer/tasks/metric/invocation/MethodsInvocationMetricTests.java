package org.metricminer.tasks.metric.invocation;

import static br.com.aniche.msr.tests.ParserTestUtils.classDeclaration;
import static br.com.aniche.msr.tests.ParserTestUtils.toInputStream;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.model.SourceCode;
import org.mockito.Mockito;

public class MethodsInvocationMetricTests {

	private MethodsInvocationMetric metric;
	private SourceCode source;

	@Before
	public void setUp() {
		this.metric = new MethodsInvocationMetric();
		this.source = Mockito.mock(SourceCode.class);
	}

	@Test
	public void shouldCountMethodsInvocationPerMethod() {
		metric.calculate(source,
				toInputStream(
						classDeclaration(
								"public void method() {"+
								"a();"+
								"b();"+
								"}"
								)));
	
		assertEquals(2, metric.getMethods().get("method/0").size());
	}
	
	@Test
	public void shouldCountMethodsInvocationPerMethodRegardlessOfVisibility() {
		metric.calculate(source,
				toInputStream(
						classDeclaration(
								"public void method1() {"+
								"a();"+
								"b();"+
								"}"+
								"private void method2() {"+
								"a();"+
								"b();"+
								"}"+
								"protected void method3() {"+
								"a();"+
								"b();"+
								"}"+
								"void method4() {"+
								"a();"+
								"b();"+
								"}"
								)));
	
		assertEquals(2, metric.getMethods().get("method1/0").size());
		assertEquals(2, metric.getMethods().get("method2/0").size());
		assertEquals(2, metric.getMethods().get("method3/0").size());
		assertEquals(2, metric.getMethods().get("method4/0").size());
	}
	
	@Test
	public void shouldNotCountRepeatedInvocations() {
		metric.calculate(source,
				toInputStream(
						classDeclaration(
								"public void method() {"+
								"x++;"+
								"a();"+
								"a();"+
								"}"
								)));
	
		assertEquals(1, metric.getMethods().get("method/0").size());
	}
	
	@Test
	public void shouldCountBigSequenceInvocations() {
		metric.calculate(source,
				toInputStream(
						classDeclaration(
								"public void method() {"+
								"a().b().c();"+
								"}"
								)));
	
		assertEquals(3, metric.getMethods().get("method/0").size());
	}
}
