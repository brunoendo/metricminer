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
	private final CSVWriter writer;
	private QueryProcessor processor;
    
    public QueryExecutor(StatelessSession session, CSVWriter writer, QueryProcessor processor) {
        this.session = session;
		this.writer = writer;
		this.processor = processor;
    }
    
    @SuppressWarnings("unchecked")
    public void execute(Query query, OutputStream... outputs) {

    	int currentPage = 1;
    	do {
    		Query paginatedQuery = processor.paginate(query, currentPage);
			SQLQuery sqlQuery = session.createSQLQuery(paginatedQuery.getSqlQuery());
	        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	        List<Map<String, Object>> results = sqlQuery.list();
	
	        if (results.isEmpty() && currentPage == 1) {
	        	writeEmptyIn(outputs);
	        }
	        else if(results.isEmpty()) break;
	        else {
	        	writeResultIn(results, outputs);
	        }
	        
	        currentPage++;
    	} while(true);
    }

	private void writeResultIn(List<Map<String, Object>> results,
			OutputStream... outputs) {
		for (OutputStream out : outputs) {
			writer.write(out, results);
		}
	}

	private void writeEmptyIn(OutputStream... outputs) {
		for (OutputStream out : outputs) {
			writer.emptyResult(out);
		}
	}

}
