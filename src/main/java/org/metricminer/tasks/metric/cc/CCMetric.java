package org.metricminer.tasks.metric.cc;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.metricminer.antlr4.java.AntLRVisitor;
import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.Metric;
import org.metricminer.tasks.metric.common.MetricResult;

public class CCMetric implements Metric {

    private CCListener visitor;
	private SourceCode sourceCode;

    public String header() {
        return "path;project;class;cc;average cc";
    }

    public Collection<MetricResult> results() {
        return Arrays.asList((MetricResult) new CCResult(sourceCode, cc(), avgCc()));
    }

    public void calculate(SourceCode sourceCode, InputStream is) {
		this.sourceCode = sourceCode;
		try {
			visitor = new CCListener();
	        new AntLRVisitor().visit(visitor, is);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public double avgCc() {
        double avgCc = visitor.getAvgCc();
        if (Double.isNaN(avgCc))
            avgCc = -1.0;
        return avgCc;
    }

    public int cc() {
        return visitor.getCc();
    }

    public int cc(String method) {
        return visitor.getCc(method);
    }

    @Override
    public boolean matches(String name) {
        return name.endsWith(".java");
    }

	@Override
	public Class<?> getFactoryClass() {
		return CCMetricFactory.class;
	}
}
