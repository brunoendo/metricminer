package org.metricminer.tasks.metric;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.metricminer.config.ClassScan;
import org.metricminer.tasks.MetricComponent;
import org.metricminer.tasks.metric.common.MetricResult;
import org.metricminer.tasks.metric.lcom.LComResult;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class AvaiableMetricResults {
	
	private ClassScan classScan;

	public AvaiableMetricResults(ClassScan classScan) {
		this.classScan = classScan;
	}

	private ArrayList<MetricInfo> metricsAvaiable = new ArrayList<MetricInfo>();

	public AvaiableMetricResults add(Class<?> ... results) {
		for (Class<?> resultClass : results) {
			MetricComponent annotation = resultClass.getAnnotation(MetricComponent.class);
			Class<? extends MetricResult> resultEntity = annotation.result();
			String name = annotation.name();
			metricsAvaiable.add(new MetricInfo(name, resultEntity));
		}
		return this;
	}

	public ArrayList<MetricInfo> getMetrics() {
		return metricsAvaiable;
	}
	
	@PostConstruct
	public void scan() throws ClassNotFoundException {
		Set<String> classes = classScan.findAll(MetricComponent.class);
		for (String className : classes) {
			add(Class.forName(className));
		}
	}
	
	

}
