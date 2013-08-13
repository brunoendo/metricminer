package org.metricminer.tasks.metric.unitorsystemtest;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.ClassInfoVisitor;
import org.metricminer.tasks.metric.common.Metric;
import org.metricminer.tasks.metric.common.MetricResult;

public class UnitOrSystemTestFinder implements Metric {

	private SourceCode source;
	private UnitOrSystemTestVisitor visitor;
	private Map<String, TestInfo> tests;
	private ClassInfoVisitor classInfo;

	@Override
	public Collection<MetricResult> results() {
		List<MetricResult> result = new ArrayList<MetricResult>();
		for(Map.Entry<String, TestInfo> test : tests.entrySet()) {
			result.add(new UnitOrSystemTestResult(source, test.getValue().getName(), !test.getValue().isIntegration()));
		}
		return result;
	}

	@Override
	public void calculate(SourceCode source, InputStream is) {
		this.source = source;
		try {
			CompilationUnit cunit = JavaParser.parse(is);
			
			classInfo = new ClassInfoVisitor();
			classInfo.visit(cunit, null);
			
			visitor = new UnitOrSystemTestVisitor(classInfo.getName());
			visitor.visit(cunit, null);
			
			tests = visitor.getTests();
			
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }		
	}

	@Override
	public boolean matches(String name) {
		return name.endsWith("Test.java") || name.endsWith("Tests.java");
	}

	@Override
	public Class<?> getFactoryClass() {
		return UnitOrSystemTestFactory.class;
	}

	public Map<String, TestInfo> getTests() {
		return tests;
	}

}
