package org.metricminer.tasks.stats;

import org.hibernate.Session;
import org.metricminer.model.QueryResult;
import org.metricminer.model.StatisticalTest;
import org.metricminer.model.StatisticalTestResult;
import org.metricminer.model.User;
import org.metricminer.stats.r.RScriptExecutor;
import org.metricminer.tasks.RunnableTask;

public class ExecuteRScriptTask implements RunnableTask {

    private final RScriptExecutor rScriptExecutor;
    private final StatisticalTest statiscalTest;
    private final QueryResult secondQuery;
    private final QueryResult firstQuery;
    private final Session session;
	private final User author;
	private final String name;

    public ExecuteRScriptTask(Session session, RScriptExecutor rScriptExecutor, 
    		StatisticalTest statiscalTest, QueryResult firstQuery, QueryResult secondQuery, User author, String name) {
        this.session = session;
        this.rScriptExecutor = rScriptExecutor;
        this.statiscalTest = statiscalTest;
        this.firstQuery = firstQuery;
        this.secondQuery = secondQuery;
		this.author = author;
		this.name = name;
    }

    @Override
    public void run() {
        try {
            StatisticalTestResult result = rScriptExecutor.execute(statiscalTest, firstQuery, secondQuery, author, name);
            session.save(result);
        } catch (Exception e) {
            throw new RuntimeException("Could not execute statiscal test", e);
        }
    }

}
