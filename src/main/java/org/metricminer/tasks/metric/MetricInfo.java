package org.metricminer.tasks.metric;

import org.metricminer.tasks.metric.common.MetricResult;

public class MetricInfo {

	private String name;
	private Class<? extends MetricResult> resultEntity;

	public MetricInfo(String name, Class<? extends MetricResult> resultEntity) {
		this.name = name;
		this.resultEntity = resultEntity;
	}

	public String getName() {
		return name;
	}

	public Class<? extends MetricResult> getResultEntity() {
		return resultEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((resultEntity == null) ? 0 : resultEntity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetricInfo other = (MetricInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (resultEntity == null) {
			if (other.resultEntity != null)
				return false;
		} else if (!resultEntity.equals(other.resultEntity))
			return false;
		return true;
	}

}
