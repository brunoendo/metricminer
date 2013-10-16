package org.metricminer.infra.dao.dto;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.model.Author;

public class AuthorAndCommitCount {

	private Author author;
	private long commits;

	public AuthorAndCommitCount(Author author, long commits) {
		this.author = author;
		this.commits = commits;
	}
	
	public Author getAuthor() {
		return author;
	}

	public long getCommits() {
		return commits;
	}

	public static List<AuthorAndCommitCount> build(List<Object[]> results) {
		ArrayList<AuthorAndCommitCount> authors = new ArrayList<AuthorAndCommitCount>();
		for (Object[] row : results) {
			Author a = (Author) row[0];
			long commits = (Long) row[1];
			authors.add(new AuthorAndCommitCount(a, commits));
		}
		return authors;
	}
	
	

}
