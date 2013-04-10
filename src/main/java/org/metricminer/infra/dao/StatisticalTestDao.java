package org.metricminer.infra.dao;

import java.util.List;

import org.hibernate.Session;
import org.metricminer.model.StatisticalTest;
import org.metricminer.model.StatisticalTestResult;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class StatisticalTestDao {
    
    private final Session session;

    public StatisticalTestDao(Session session) {
        this.session = session;
    }

    public StatisticalTest findById(long id) {
        return (StatisticalTest) session.load(StatisticalTest.class, id);
    }

    public void save(StatisticalTest statisticalTest) {
        session.save(statisticalTest);
    }

    @SuppressWarnings("unchecked")
    public List<StatisticalTest> list() {
        return session.createCriteria(StatisticalTest.class).list();
    }

	@SuppressWarnings("unchecked")
	public List<StatisticalTestResult> results() {
		return session.createCriteria(StatisticalTestResult.class).list();
	}

	public StatisticalTestResult findResult(Long resultId) {
		return (StatisticalTestResult) session.load(StatisticalTestResult.class, resultId);
	}
    
    

}
