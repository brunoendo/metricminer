package org.metricminer.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class QueryResult {
	@Id
	@GeneratedValue
	private Long id;
	private String csvFilename;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar executedDate;
	private QueryResultStatus status;
	
	public QueryResult() {
	    executedDate = Calendar.getInstance();
	}

	public QueryResult(String csvFilename) {
	    this();
		this.csvFilename = csvFilename;
	}
	
	public String getCsvFilename() {
		return csvFilename;
	}
	
	public Long getId() {
		return id;
	}
	
	public Calendar getExecutedDate() {
		return executedDate;
	}
	
	public void fail(String message) {
	    status = QueryResultStatus.FAILED.withMessage(message);
	}
	
	public void success() {
	    status = QueryResultStatus.SUCESS;
	}
	
	public QueryResultStatus getStatus() {
        return status;
    }
	
	public boolean hasFailed() {
	    return QueryResultStatus.FAILED == this.status;
	}

}
