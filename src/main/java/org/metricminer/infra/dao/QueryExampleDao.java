package org.metricminer.infra.dao;

import java.util.List;

import org.hibernate.Session;
import org.metricminer.model.QueryExample;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class QueryExampleDao {
	
	private Session session;

	public QueryExampleDao(Session session) {
		this.session = session;
	}

	public void save(QueryExample query) {
		session.save(query);
	}

	@SuppressWarnings("unchecked")
	public List<QueryExample> list() {
		return session.createCriteria(QueryExample.class).list();
	}

}
