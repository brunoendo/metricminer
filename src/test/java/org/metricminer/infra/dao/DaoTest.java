package org.metricminer.infra.dao;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class DaoTest {
    
    protected Session session;
    private static SessionFactory sessionFactory;
    protected StatelessSession statelessSession;
    
    @BeforeClass
    public static void setupClass() {
    	sessionFactory = new Configuration().configure(
    			"/hibernate.test.cfg.xml").buildSessionFactory();
    }
    
    @AfterClass
    public static void tearDownClass() {
    	sessionFactory.close();
    }
    
    
    @Before
    public void setUpSuper() {
        session = sessionFactory.openSession();
        session.getTransaction().begin();
        statelessSession = sessionFactory.openStatelessSession();
        statelessSession.getTransaction().begin();
    }
    
    @After
    public void tearDown() {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        statelessSession.getTransaction().rollback();
        session.close();
    }
    
}
