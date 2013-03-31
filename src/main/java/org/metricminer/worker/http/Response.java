package org.metricminer.worker.http;

import org.apache.http.HttpResponse;

public class Response {

	private final HttpResponse httpResponse;

	public Response(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public Status status() {
		return Status.from(httpResponse.getStatusLine());
	}

}
