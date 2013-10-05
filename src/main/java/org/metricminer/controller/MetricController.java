package org.metricminer.controller;

import java.util.List;

import org.metricminer.infra.dao.MetricDao;
import org.metricminer.tasks.metric.MetricInfo;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class MetricController {
	
	private MetricDao metrics;
	private Result result;

	public MetricController(MetricDao metrics, Result result) {
		this.metrics = metrics;
		this.result = result;
	}
	
	public void listMetrics() {
		List<MetricInfo> metricsResults = metrics.listAvaiableResults();
		result.include("metricsResults", metricsResults);
	}

}
