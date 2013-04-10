package org.metricminer.tasks.metric.staticcounter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.metricminer.model.SourceCode;
import org.metricminer.tasks.metric.common.MetricResult;

@Entity
public class StaticCounterResult implements MetricResult {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private SourceCode sourceCode;

	private String attributesName;
	private String methodsName;
	
	private int atrributesCount;
	private int methodsCount;
	
	public StaticCounterResult(
			SourceCode sourceCode,
			String attributesName,
			int attributesCount,
			String methodsName,
			int methodsCount) {
		
		this.sourceCode = sourceCode;
		this.attributesName = attributesName;
		this.atrributesCount = attributesCount;
		this.methodsName = methodsName;
		this.methodsCount = methodsCount;
	}

	public Long getId() {
		return id;
	}

	public SourceCode getSourceCode() {
		return sourceCode;
	}

	public String getAttributesName() {
		return attributesName;
	}

	public int getAttributesCount() {
		return atrributesCount;
	}

	public String getMethodsName() {
		return methodsName;
	}

	public void setMethodsName(String methodsName) {
		this.methodsName = methodsName;
	}

	public int getMethodsCount() {
		return methodsCount;
	}

	public void setStaticMethodsCount(int staticMethodsCount) {
		this.methodsCount = staticMethodsCount;
	}
}