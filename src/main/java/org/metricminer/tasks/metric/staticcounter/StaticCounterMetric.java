package org.metricminer.tasks.metric.staticcounter;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.Metric;
import org.metricminer.tasks.metric.common.MetricResult;

public class StaticCounterMetric implements Metric {

	private SourceCode source;
	
	private Set<String> attributesName;
	private int attributesCounter;
	
	private Set<String> methodsName;
	private int methodsCounter;

	@Override
	public Collection<MetricResult> results() {
		Collection<MetricResult> results = new ArrayList<MetricResult>();

		if(this.attributesCounter > 0 || this.methodsCounter > 0) {
			String attributesName = StringUtils.join(this.attributesName.toArray(), ",");
			String methodsName = StringUtils.join(this.methodsName.toArray(), ",");
			
			MetricResult staticAttributesResult = new StaticCounterResult(
					source,
					attributesName,
					attributesCounter,
					methodsName,
					methodsCounter);
			
			results.add(staticAttributesResult);
		}

		return results;
	}

	@Override
	public void calculate(SourceCode source, InputStream is) {
		try {
			this.source = source;
			
			CompilationUnit cunit = JavaParser.parse(is);

			StaticCounterMetricVisitor nameVisitor = new StaticCounterMetricVisitor();
			nameVisitor.visit(cunit, null);
			
			attributesName = nameVisitor.attributesName;
			attributesCounter = nameVisitor.attributesCounter;
			
			methodsCounter = nameVisitor.methodsCounter;
			methodsName = nameVisitor.methodsName;
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
		return attributesCounter;
	}

	public Set<String> getStaticAttributesName() {
		return attributesName;
	}

	public int getNumberOfStaticMethods() {
		return methodsCounter;
	}

	public Set<String> getStaticMethodsName() {
		return methodsName;
	}

}