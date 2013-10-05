package org.metricminer.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.metricminer.tasks.metric.cc.CCResult;
import org.metricminer.tasks.metric.common.MetricResult;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetricComponent {
	String name();

	Class<? extends MetricResult> result();
}
