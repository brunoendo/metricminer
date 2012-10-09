package org.metricminer.tasks.metric.common;

import java.io.InputStream;
import java.util.Collection;

import org.metricminer.model.SourceCode;


public interface Metric {
    Collection<MetricResult> resultsToPersistOf(SourceCode source);

    void calculate(InputStream is);

    boolean matches(String name);
    
    Class<?> getFactoryClass();
}
