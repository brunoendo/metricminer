package org.metricminer;

public class MetricMinerExeption extends RuntimeException {

    private static final long serialVersionUID = -6840938331242924281L;

    public MetricMinerExeption() {
		super();
	}

	public MetricMinerExeption(Exception e) {
		super(e);
	}

	public MetricMinerExeption(String message, Exception e) {
		super(message, e);
	}

	public MetricMinerExeption(String message) {
		super(message);
	}

}
