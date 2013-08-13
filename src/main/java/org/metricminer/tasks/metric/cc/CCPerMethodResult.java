package org.metricminer.tasks.metric.cc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.MetricResult;

@Entity
public class CCPerMethodResult implements MetricResult {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private SourceCode sourceCode;

	private int cc;
	private String methodName;

	public CCPerMethodResult() {
	}

	public CCPerMethodResult(SourceCode sourceCode, String methodName, int cc) {
		this.sourceCode = sourceCode;
		this.methodName = methodName;
		this.cc = cc;
	}

	public Long getId() {
		return id;
	}

	public SourceCode getSourceCode() {
		return sourceCode;
	}

	public int getCc() {
		return cc;
	}

	public String getMethodName() {
		return methodName;
	}

}
