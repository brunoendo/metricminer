package org.metricminer.tasks.metric.unitorsystemtest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.MetricResult;

@Entity
public class UnitOrSystemTestResult implements MetricResult {

	@Id @GeneratedValue
	private Long id;
	
	private String testName;
	private boolean isUnit;

	@ManyToOne
	private SourceCode sourceCode;
	
	public UnitOrSystemTestResult(SourceCode source, String testName, boolean isUnit) {
		this.sourceCode = source;
		this.testName = testName;
		this.isUnit = isUnit;
	}

	public Long getId() {
		return id;
	}

	public String getTestName() {
		return testName;
	}

	public boolean isUnit() {
		return isUnit;
	}

	public SourceCode getSourceCode() {
		return sourceCode;
	}
	
	
}
