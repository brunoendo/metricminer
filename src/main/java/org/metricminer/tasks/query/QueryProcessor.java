package org.metricminer.tasks.query;

import org.metricminer.model.Author;
import org.metricminer.model.Query;

public class QueryProcessor {

	public static final int PER_PAGE = 100000;
	private static final String NAME_FUNCTION = "AuthorName";
	private static final String EMAIL_FUNCTION = "AuthorEmail";

	public Query process(Query originalQuery, int page) {
		Query query = new Query();
		query.setName(originalQuery.getName());
		
		String sql = originalQuery.getSqlQuery();
		sql = sql.replaceAll(NAME_FUNCTION + "\\s*\\(\\s*\\)", "MD5(" + Author.NAME_COLUMN + ")");
		sql = sql.replaceAll(EMAIL_FUNCTION + "\\s*\\(\\s*\\)", "MD5(" + Author.EMAIL_COLUMN + ")");
		sql = paginate(query, page).getSqlQuery();
		if (sql.trim().endsWith(";")) {
			sql = sql.trim().substring(0, sql.trim().length() - 1);
		}
		
		query.setSqlQuery(sql);
		return query;
	}
	
	public Query paginate(Query originalQuery, int page) {
		Query query = new Query();
		query.setSqlQuery(originalQuery.getSqlQuery() + " LIMIT " + next(page) + ", " + PER_PAGE);
		return query;
	}

	private int next(int page) {
		return (page-1)*PER_PAGE;
	}

}
