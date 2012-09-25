package org.metricminer.tasks.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.OutputStream;

import org.junit.Test;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.QueryDao;
import org.metricminer.model.Query;
import org.metricminer.model.Task;
import org.metricminer.model.TaskConfigurationEntryKey;
import org.metricminer.model.User;
import org.mockito.Mockito;

import br.com.caelum.vraptor.simplemail.Mailer;

public class ExecuteQueryTaskTest {

    @Test
    public void shouldSaveErrorMessageWhenQueryFail() {
        File file = new File("tmp/");
        file.mkdir();
        
        Task task = mock(Task.class);
        when(task.getTaskConfigurationValueFor(TaskConfigurationEntryKey.QUERY_ID)).thenReturn("1");
        
        QueryDao queryDao = mock(QueryDao.class);
        Query queryToRun = new Query();
        User author = new User();
        author.setEmail("some@some.com");
        queryToRun.setAuthor(author);
        when(queryDao.findBy(1L)).thenReturn(queryToRun);
        
        MetricMinerConfigs config = mock(MetricMinerConfigs.class);
        when(config.getQueriesResultsDir()).thenReturn("tmp/");
        Mailer mailer = mock(Mailer.class);
        when(config.getMailer()).thenReturn(mailer);
        
        QueryExecutor queryExecutor = mock(QueryExecutor.class);
        Mockito.doThrow(new RuntimeException("error message")).when(queryExecutor).execute(Mockito.any(Query.class), Mockito.any(OutputStream.class));
        
        ExecuteQueryTask queryTask = new ExecuteQueryTask(task, queryExecutor, queryDao, config);
        queryTask.run();
        
        assertEquals(1, queryToRun.getResultCount());
        String stackTrace = queryToRun.getResults().get(0).getStatus().getMessage();
        assertTrue(stackTrace.contains("error message"));
    }

}
