package org.metricminer.tasks.metric.cc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import org.metricminer.antlr4.java.AntLRVisitor;
import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.Metric;
import org.metricminer.tasks.metric.common.MetricResult;


public class CCPerMethodMetric implements Metric {

    private CCListener visitor;
	private SourceCode sourceCode;

    public String header() {
        return "path;project;class;method;cc";
    }

    public void calculate(SourceCode sourceCode, InputStream is) {
        this.sourceCode = sourceCode;
        
		try {
			visitor = new CCListener();
	        new AntLRVisitor().visit(visitor, is);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<MetricResult> results() {
    	
    	Collection<MetricResult> list = new ArrayList<MetricResult>();
        for (Entry<String, Integer> method : visitor.getCcPerMethod().entrySet()) {
        	int cc = method.getValue();
        	String methodName = method.getKey();
        	
        	list.add(new CCPerMethodResult(sourceCode, methodName, cc));
        }
        
        return list;
    }

    @Override
    public boolean matches(String name) {
        return name.endsWith(".java");
    }

	@Override
	public Class<?> getFactoryClass() {
		return null;
	}


}
