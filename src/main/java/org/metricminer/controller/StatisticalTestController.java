package org.metricminer.controller;

import java.util.List;

import org.metricminer.infra.dao.QueryResultDAO;
import org.metricminer.infra.dao.StatisticalTestDao;
import org.metricminer.infra.dao.TaskDao;
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

    public StatisticalTestController(StatisticalTestDao statisticalTestDao, TaskDao taskDao,
            Result result, QueryResultDAO queryResultDao) {
        this.taskDao = taskDao;
        this.statisticalTestDao = statisticalTestDao;
        this.result = result;
        this.queryResultDao = queryResultDao;
    }
    
    @Get("/stats/add")
    public void statisticalTestTaskForm() {
        List<QueryResult> results = queryResultDao.list();
        List<StatisticalTest> tests = statisticalTestDao.list();
        result.include("results", results);
        result.include("tests", tests);
    }

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
        taskDao.save(task);
        result.redirectTo(StatisticalTestController.class).detailStatiscalTestExecution(task.getId());
    }
    
    @Get("/stat/{taskId}")
    public void detailStatiscalTestExecution(Long taskId) {
        Task task = taskDao.findById(taskId);
        result.include("task", task);
        long firstQueryId = Long.parseLong(task.getTaskConfigurationValueFor(TaskConfigurationEntryKey.FIRST_QUERY_RESULT));
        long secondQueryId = Long.parseLong(task.getTaskConfigurationValueFor(TaskConfigurationEntryKey.SECOND_QUERY_RESULT));
        long statisticalTestId = Long.parseLong(task.getTaskConfigurationValueFor(TaskConfigurationEntryKey.STATISTICAL_TEST));
        result.include("firstDataset", 
                queryResultDao.findById(firstQueryId));
        result.include("secondDataset", 
                queryResultDao.findById(secondQueryId));
        result.include("statisticalTest", 
                statisticalTestDao.findById(statisticalTestId));
    }
}
