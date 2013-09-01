package org.metricminer.controller;

import org.metricminer.infra.dao.QueryExampleDao;
import org.metricminer.model.QueryExample;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class QueryExampleController {
	
	private QueryExampleDao dao;
	private Result result;

	public QueryExampleController(QueryExampleDao dao, Result result) {
		this.dao = dao;
		this.result = result;
	}
	
	@Get("newExampleQuery")
	public void form() {
	}
	
	@Post("newExampleQuery")
	public void save(QueryExample example) {
		dao.save(example);
		result.redirectTo(QueryController.class).queryForm();
	}

}
