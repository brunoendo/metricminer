package org.metricminer.infra.dao;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;

public class DaoTest {
    
    protected Session session;
    private SessionFactory sessionFactory;
    protected StatelessSession statelessSession;
    
    @Before
    public void setUpSuper() {
        sessionFactory = new Configuration().configure(
                "/hibernate.test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        session.getTransaction().begin();
        statelessSession = sessionFactory.openStatelessSession();
        statelessSession.getTransaction().begin();
    }
    
    @After
    public void tearDown() {
        session.getTransaction().rollback();
        statelessSession.getTransaction().rollback();
        session.close();
        sessionFactory.close();
    }
    
}
