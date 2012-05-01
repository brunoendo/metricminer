package sandbox;

import java.util.Calendar;

import model.SourceCode;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import br.com.caelum.revolution.domain.Commit;

public class Sandbox {
    @Test
    public void testStatelessSession() throws Exception {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        StatelessSession statelessSession = sf.openStatelessSession();
        Query query = statelessSession.createQuery("select source from SourceCode source "
                + "join source.artifact as artifact where artifact.project.id = :project_id");
        query.setParameter("project_id", 1L);
        ScrollableResults results = query.scroll();
        while (results.next()) {
            SourceCode source = (SourceCode) results.get(0);
            System.out.println(source.getName());
        }
    }
    
    @Test
    public void testCommitersCount() throws Exception {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("select count(id) from Author where id in (select author.id from Commit commit " +
                "join commit.author author where commit.project.id = :id " +
                "group by author.id)");
        
        query.setParameter("id", 4l);
        
        ScrollableResults results = query.scroll();
        while (results.next()) {
            Long count = (Long) results.get(0);
            System.out.println(count);
        }
    }
    
    @Test
    public void testFirstCommit() throws Exception {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Query query = session.createQuery("select commit From Commit as commit where " +
        		"commit.project.id=:id order by date asc ");
        
        query.setMaxResults(1);
        
        query.setParameter("id", 4l);
        
        Commit commit = (Commit) query.uniqueResult();
        
        System.out.println(commit.getDate().get(Calendar.YEAR));
    }
    
    
}
