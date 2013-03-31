package org.metricminer.worker.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.StatusLine;

public class Status {

	private final StatusLine statusLine;
	
	private static final Map<Integer, Code> CODES = new HashMap<>();

	private Code code;
	
	{
		CODES.put(201, Code.CREATED);
	}

	public Status(StatusLine statusLine) {
		this.statusLine = statusLine;
		code = CODES.get(statusLine.getStatusCode());
	}

	public static Status from(StatusLine statusLine) {
		return new Status(statusLine);
	}
	
	public boolean is(Code code) {
		return this.code == code; 
	}
	
	public enum Code {
		CREATED
	}



}
