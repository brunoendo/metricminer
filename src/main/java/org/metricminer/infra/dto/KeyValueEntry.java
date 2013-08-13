package org.metricminer.infra.dto;

import org.metricminer.model.Author;

public class KeyValueEntry {

	private final Author key;
	private final Long value;

	public KeyValueEntry(Long count, Author key) {
		this.value = count;
		this.key = key;
	}

	public Long getValue() {
		return value;
	}

	public Author getKey() {
		return key;
	}

}
