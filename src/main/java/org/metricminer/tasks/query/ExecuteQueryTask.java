package org.metricminer.tasks.query;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.QueryDao;
import org.metricminer.model.Query;
import org.metricminer.model.QueryResult;
import org.metricminer.model.Task;
import org.metricminer.model.TaskConfigurationEntryKey;
import org.metricminer.tasks.RunnableTask;

public class ExecuteQueryTask implements RunnableTask {

    private final Long queryId;
    private final QueryExecutor queryExecutor;
    private final QueryDao queryDao;
	private final MetricMinerConfigs config;

    public ExecuteQueryTask(Task task, QueryExecutor queryExecutor, QueryDao queryDao, MetricMinerConfigs config) {
        this.queryDao = queryDao;
		this.config = config;
        this.queryId = Long.parseLong(task.getTaskConfigurationValueFor(TaskConfigurationEntryKey.QUERY_ID)); 
        this.queryExecutor = queryExecutor;
    }

    @Override
    public void run() {
        Query query = queryDao.findBy(queryId);
        String csvFileName = config.getQueriesResultsDir() + "/result-" + query.getId() + "-" + query.getResultCount() + ".csv";
        FileOutputStream outputStream = createFile(csvFileName);
        try {
            queryExecutor.execute(query, outputStream);
            QueryResult result = new QueryResult(csvFileName);
            query.addResult(result);
            result.success();
        } catch (Exception e){
            QueryResult result = new QueryResult();
            result.fail(ExceptionUtils.getStackTrace(e));
            query.addResult(result);
        }
        queryDao.update(query);
    }

    private FileOutputStream createFile(String tmpFileName) {
        try {
            return new FileOutputStream(tmpFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
