package org.metricminer.model;

import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class QueryResult {
	@Id
	@GeneratedValue
	private Long id;
	private String csvFilename;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar executedDate = Calendar.getInstance();
	@ManyToOne
	private Query query;
	@Embedded
	private QueryResultStatus status;
	
	QueryResult() {
	}

	public QueryResult(String csvFilename, Query query) {
		this.csvFilename = csvFilename;
        this.query = query;
	}
	
	public QueryResult(Query query) {
		this(null, query);
	}

	public String getFilename() {
		return csvFilename;
	}
	
	public Long getId() {
		return id;
	}
	
	public Calendar getExecutedDate() {
		return executedDate;
	}
	
	public void fail(String message) {
	    status = QueryResultStatus.failed(message);
	}
	
	public void success() {
	    status = QueryResultStatus.success();
	}
	
	public QueryResultStatus getStatus() {
        return status;
    }
	
	public boolean hasFailed() {
	    return this.status.isFail();
	}
	
	public Query getQuery() {
        return query;
    }

}
