package org.metricminer.infra.dao;

import org.hibernate.Session;
import org.metricminer.model.StatisticalTest;

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

}
