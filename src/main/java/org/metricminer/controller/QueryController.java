package org.metricminer.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.metricminer.infra.dao.QueryDao;
import org.metricminer.infra.dao.QueryExampleDao;
import org.metricminer.infra.dao.QueryResultDAO;
import org.metricminer.infra.dao.TaskDao;
import org.metricminer.infra.interceptor.LoggedUserAccess;
import org.metricminer.infra.session.UserSession;
import org.metricminer.infra.validator.QueryValidator;
import org.metricminer.model.Query;
import org.metricminer.model.QueryExample;
import org.metricminer.model.QueryResult;
import org.metricminer.model.Task;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;

@Resource
public class QueryController {

    private final TaskDao taskDao;
    private final Result result;
    private final QueryDao queryDao;
    private final QueryResultDAO queryResultDAO;
    private final QueryValidator queryValidator;
    private final UserSession userSession;
	private Validator validator;
	private QueryExampleDao queryExampleDao;

    public QueryController(TaskDao taskDao, QueryDao queryDao,
            QueryResultDAO queryResultDAO, Result result,
            QueryValidator queryValidator, UserSession userSession, 
            Validator validator, QueryExampleDao queryExampleDao) {
        this.taskDao = taskDao;
        this.queryDao = queryDao;
        this.queryResultDAO = queryResultDAO;
        this.result = result;
        this.queryValidator = queryValidator;
        this.userSession = userSession;
		this.validator = validator;
		this.queryExampleDao = queryExampleDao;
    }

    @LoggedUserAccess
    @Get("/queries/new")
    public void queryForm() {
    	List<QueryExample> examples = queryExampleDao.list();
    	result.include("examples", examples);
    }

    @LoggedUserAccess
    @Post("/queries")
    public void save(Query query) {
        queryValidator.validate(query);
        validator.onErrorRedirectTo(QueryController.class).queryForm();
        
        query.setAuthor(userSession.getUser());
        queryDao.save(query);
        taskDao.saveTaskToExecuteQuery(query);
        result.include("included", true);
        result.redirectTo(QueryController.class).detailQuery(query.getId());
    }


    @Get("/queries")
    public void listQueries() {
        List<Query> queries = queryDao.list();
        Collections.sort(queries);
        result.include("queries", queries);
    }


    @Get("/query/{queryId}")
    public void detailQuery(Long queryId)  {
        Query query = queryDao.findBy(queryId);
        List<Task> tasksToRunThisQuery = taskDao.findTasksScheduledToRunQuery(query);
        
        result.include("query", query);
        result.include("scheduledToRun", !tasksToRunThisQuery.isEmpty());
    }

	@Get("/query/download/{resultId}/zip")
	public Download downloadZip(Long resultId) {
		QueryResult result = queryResultDAO.findById(resultId);
		String csvFilename = result.getFilenameAsZip();
		return new FileDownload(new File(csvFilename), "application/zip", "result.zip");
	}
	
	@Get("/query/download/{resultId}/csv")
	public Download downloadCsv(Long resultId) {
		QueryResult result = queryResultDAO.findById(resultId);
		String csvFilename = result.getFilenameAsCsv();
		return new FileDownload(new File(csvFilename), "text/csv", "result.csv");
	}

    @LoggedUserAccess
    @Post("/query/run")
    public void runQuery(Long queryId) {
        Query query = queryDao.findBy(queryId);
        taskDao.saveTaskToExecuteQuery(query);
        result.include("toRun", true);
        result.redirectTo(this).detailQuery(queryId);
    }
}