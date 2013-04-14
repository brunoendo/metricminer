package org.metricminer.tasks.metric.unitorsystemtest;

public class TestInfo {

	private String name;
	private boolean isIntegration;
	
	public TestInfo(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIntegration() {
		return isIntegration;
	}
	public void setIntegration(boolean isIntegration) {
		this.isIntegration = isIntegration;
	}
	
	
}
