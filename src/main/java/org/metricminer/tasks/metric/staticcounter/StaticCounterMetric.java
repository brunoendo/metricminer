package org.metricminer.tasks.metric.staticcounter;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.Metric;
import org.metricminer.tasks.metric.common.MetricResult;

public class StaticCounterMetric implements Metric {

	private SourceCode source;
	
	private Set<String> staticAttributesName;
	private int staticAttributesCounter;
	
	private Set<String> staticMethodsName;
	private int staticMethodsCounter;

	@Override
	public Collection<MetricResult> results() {
		String attributeNames = StringUtils.join(staticAttributesName.toArray(), ",");
		String methodNames = StringUtils.join(staticMethodsName.toArray(), ",");
		MetricResult staticAttributesResult = new StaticCounterResult(
				source,
				attributeNames,
				staticAttributesCounter,
				methodNames,
				staticMethodsCounter);

		return Arrays.asList(staticAttributesResult);
	}

	@Override
	public void calculate(SourceCode source, InputStream is) {
		try {
			this.source = source;
			
			CompilationUnit cunit = JavaParser.parse(is);

			StaticCounterMetricVisitor nameVisitor = new StaticCounterMetricVisitor();
			nameVisitor.visit(cunit, null);
			staticAttributesName = nameVisitor.staticAttributesName;
			staticAttributesCounter = nameVisitor.staticAttributesCounter;
			
			staticMethodsCounter = nameVisitor.staticMethodsCounter;
			staticMethodsName = nameVisitor.staticMethodsName;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean matches(String name) {
		return name.endsWith(".java");
	}

	@Override
	public Class<?> getFactoryClass() {
		return StaticCounterMetricFactory.class;
	}

	public int getNumberOfStaticAttributes() {
		return staticAttributesCounter;
	}

	public Set<String> getStaticAttributesName() {
		return staticAttributesName;
	}

	public int getNumberOfStaticMethods() {
		return staticMethodsCounter;
	}

	public Set<String> getStaticMethodsName() {
		return staticMethodsName;
	}

}