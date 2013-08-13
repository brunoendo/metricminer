package org.metricminer.infra.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.metricminer.infra.dto.KeyValueEntry;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class CommitDao {

	private final Session session;

	public CommitDao(Session session) {
		this.session = session;
	}
	
	public Long totalCommits() {
		Query query = session.createQuery("select count(id) from Commit");
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<KeyValueEntry> commitCountByAuthor(int maxResults) {
		Query query = session.createQuery("select new "+KeyValueEntry.class.getCanonicalName()+"(count(c.id), author) from Commit " +
				"c join c.author author group by author order by count(c.id) desc");
		query.setMaxResults(maxResults);
		return query.list();
	}
	
}
