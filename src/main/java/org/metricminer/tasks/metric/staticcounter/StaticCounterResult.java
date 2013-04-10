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

	private int staticAtrributesCount;

	public StaticCounterResult(
			SourceCode sourceCode,
			String attributesName,
			int staticAtrributesCount) {
		
		this.sourceCode = sourceCode;
		this.attributesName = attributesName;
		this.staticAtrributesCount = staticAtrributesCount;
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

	public int getStaticAtrributesCount() {
		return staticAtrributesCount;
	}
}