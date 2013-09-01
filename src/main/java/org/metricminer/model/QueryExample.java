package org.metricminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class QueryExample {
	
	@Id @GeneratedValue
	private Long id;
	
	@Type(type = "text")
	private String query;
	
	private String name;
	
	@Type(type = "text")
	private String description;
	
	@Deprecated
	QueryExample() {
	}

	public QueryExample(String query, String name, String description) {
		this.query = query;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getQuery() {
		return query;
	}
	
	public String getQueryAsJson() {
		return query.replace("\n", "\\n");
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	

}
