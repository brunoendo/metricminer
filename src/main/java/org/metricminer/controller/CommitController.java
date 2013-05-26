package org.metricminer.controller;

import java.util.List;

import org.metricminer.infra.dao.CommitDao;
import org.metricminer.infra.dto.KeyValueEntry;
import org.metricminer.view.KeyValueData;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class CommitController {
	private final CommitDao commits;
	private final Result result;

	public CommitController(CommitDao commits, Result result) {
		this.commits = commits;
		this.result = result;
	}

	@Get("/commits/byAuthor")
	public void commitsByAuthor() {
		List<KeyValueEntry> commitCountByAuthor = commits.commitCountByAuthor(10);
		result.use(KeyValueData.class).of(commitCountByAuthor)
			.withKeyName("Author")
			.withValueName("# of commits")
			.serialize();
		
	}
}
