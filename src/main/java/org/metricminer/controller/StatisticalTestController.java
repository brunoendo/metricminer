package org.metricminer.controller;

import java.util.List;

import org.metricminer.infra.dao.QueryResultDAO;
import org.metricminer.infra.dao.StatisticalTestDao;
import org.metricminer.infra.dao.TaskDao;
import org.metricminer.infra.interceptor.LoggedUserAccess;
import org.metricminer.infra.session.UserSession;
import org.metricminer.model.QueryResult;
import org.metricminer.model.StatisticalTest;
import org.metricminer.model.Task;
import org.metricminer.model.TaskBuilder;
import org.metricminer.model.TaskConfigurationEntryKey;
import org.metricminer.tasks.stats.ExecuteRScriptTaskFactory;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class StatisticalTestController {
    
    private final StatisticalTestDao statisticalTestDao;
    private final TaskDao taskDao;
    private final Result result;
    private final QueryResultDAO queryResultDao;
	private final UserSession userSession;

    public StatisticalTestController(StatisticalTestDao statisticalTestDao, TaskDao taskDao,
            Result result, QueryResultDAO queryResultDao, UserSession userSession) {
        this.taskDao = taskDao;
        this.statisticalTestDao = statisticalTestDao;
        this.result = result;
        this.queryResultDao = queryResultDao;
		this.userSession = userSession;
    }
    
    @LoggedUserAccess
    @Get("/stats/add")
    public void statisticalTestTaskForm() {
        List<QueryResult> results = queryResultDao.allSucceded();
        List<StatisticalTest> tests = statisticalTestDao.list();
        result.include("results", results);
        result.include("tests", tests);
    }

    @LoggedUserAccess
    @Post("/stats/")
    public void addStatisticalTestExecution(Long statisticalTestId, Long firstQueryResultId,
            Long secondQueryResultId, String name) {
        Task task = new TaskBuilder()
            .forProject(null)
            .withName(name)
            .withRunnableTaskFactory(new ExecuteRScriptTaskFactory())
            .build();
        task.addTaskConfigurationEntry(TaskConfigurationEntryKey.FIRST_QUERY_RESULT, 
                firstQueryResultId.toString());
        task.addTaskConfigurationEntry(TaskConfigurationEntryKey.SECOND_QUERY_RESULT, 
                secondQueryResultId.toString());
        task.addTaskConfigurationEntry(TaskConfigurationEntryKey.STATISTICAL_TEST, 
                statisticalTestId.toString());
        task.addTaskConfigurationEntry(TaskConfigurationEntryKey.AUTHOR_ID, 
        		userSession.getUser().getId().toString());
        taskDao.save(task);
        
        result.include("added", true);
        result.redirectTo(this).listStats();
    }
    
    @Get("/stats")
    public void listStats() {
    	result.include("results", statisticalTestDao.results());
    }
    
    @Get("/stats/results/{resultId}")
    public void showResult(Long resultId) {
    	result.include("result", statisticalTestDao.findResult(resultId));
    }
}
