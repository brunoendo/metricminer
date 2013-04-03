package org.metricminer.tasks.query;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.StatelessSession;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.metricminer.infra.csv.CSVWriter;
import org.metricminer.model.Query;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class QueryExecutor {
    private StatelessSession session;
	private final QueryProcessor queryProcessor;
	private final CSVWriter writer;
    
    public QueryExecutor(StatelessSession session, QueryProcessor queryProcessor, CSVWriter writer) {
        this.session = session;
		this.queryProcessor = queryProcessor;
		this.writer = writer;
    }
    
    @SuppressWarnings("unchecked")
    public void execute(Query query, OutputStream csvOutputStream) {

    	int currentPage = 1;
    	do {
	        Query processedQuery = queryProcessor.process(query, currentPage);
			SQLQuery sqlQuery = session.createSQLQuery(processedQuery.getSqlQuery());
	        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	        List<Map<String, Object>> results = sqlQuery.list();
	
	        if (results.isEmpty() && currentPage == 1) {
	        	writer.emptyResult(csvOutputStream);
	        }
	        else if(results.isEmpty()) break;
	        else {
	        	writer.write(csvOutputStream, results);
	        }
	        
	        currentPage++;
    	} while(true);
    }

}
