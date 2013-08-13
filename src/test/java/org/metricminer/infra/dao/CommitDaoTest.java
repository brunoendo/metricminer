package org.metricminer.infra.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.metricminer.infra.dto.KeyValueEntry;
import org.metricminer.model.Author;
import org.metricminer.model.Commit;

public class CommitDaoTest extends DaoTest {

	@Test
	public void shouldListCommitCountByUser() {
		CommitDao commitDao = new CommitDao(session);
		String commitId = "hash";
		Author author1 = new Author("Developer 1", "dev1@metricminer.org");
		Author author2 = new Author("Developer 2", "dev2@metricminer.org");
		
		session.save(author1);
		session.save(new Commit(commitId, author1, null, null, null, null,  null));
		session.save(new Commit(commitId, author1, null, null, null, null,  null));
		session.save(new Commit(commitId, author1, null, null, null, null,  null));
		
		session.save(author2);
		session.save(new Commit(commitId, author2, null, null, null, null,  null));
		session.save(new Commit(commitId, author2, null, null, null, null,  null));
		
		List<KeyValueEntry> countAndUsers = commitDao.commitCountByAuthor(2);
		
		assertEquals(3l, countAndUsers.get(0).getValue().longValue());
		assertEquals(author1, countAndUsers.get(0).getKey());
		
		assertEquals(2l, countAndUsers.get(1).getValue().longValue());
		assertEquals(author2, countAndUsers.get(1).getKey());
	}

}
