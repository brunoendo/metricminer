package org.metricminer.tasks.query;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.model.Author;
import org.metricminer.model.Query;

public class QueryProcessorTest {

	private Query query;
	private QueryProcessor processor;

	@Before
	public void setUp() {
		query = new Query();
		processor = new QueryProcessor();
	}

	@Test
	public void shouldReplaceAuthorNameForMD5Function() throws Exception {
		query.setSqlQuery("select AuthorName(  ) from Author");
		Query prcessedQuery = processor.process(query, 1);
		
		assertTrue(prcessedQuery.getSqlQuery().startsWith("select MD5("+Author.NAME_COLUMN+") from Author"));
	}
	
	@Test
	public void shouldReplaceAuthorEmailForMD5Function() throws Exception {
		query.setSqlQuery("select AuthorEmail () from Author");
		Query prcessedQuery = processor.process(query, 1);
		
		assertTrue(prcessedQuery.getSqlQuery().startsWith("select MD5("+Author.EMAIL_COLUMN+") from Author"));
	}
	
	@Test
	public void shouldLimitQuery() {
		query.setSqlQuery("select * from LOC");
		
		Query processedQuery = processor.process(query, 1);
		assertTrue(processedQuery.getSqlQuery().contains("LIMIT 0, " + QueryProcessor.PER_PAGE));

		processedQuery = processor.process(query, 3);
		assertTrue(processedQuery.getSqlQuery().contains("LIMIT " + (QueryProcessor.PER_PAGE*2) + ", " + QueryProcessor.PER_PAGE));
		
	}
	
}
