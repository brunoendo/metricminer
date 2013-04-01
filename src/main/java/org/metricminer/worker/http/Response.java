package org.metricminer.worker.http;

import org.apache.http.HttpResponse;

public class Response {

	private final HttpResponse httpResponse;
	private Status status;

	public Response(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		this.status = Status.from(httpResponse.getStatusLine());
	}

	public Status status() {
		return status;
	}
	
	@Override
	public String toString() {
		return status.toString();
	}

}
