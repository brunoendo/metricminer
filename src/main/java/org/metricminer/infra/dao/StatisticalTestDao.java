package org.metricminer.infra.dao;

import java.util.List;

import org.hibernate.Session;
import org.metricminer.model.StatisticalTest;
import org.metricminer.model.StatisticalTestResult;
import org.metricminer.model.User;

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

	@SuppressWarnings("unchecked")
	public List<StatisticalTestResult> belongsTo(User user) {
		return session.createQuery("select t from StatisticalTestResult t where author = :user")
				.setParameter("user", user)
				.list();
	}
    
    

}
