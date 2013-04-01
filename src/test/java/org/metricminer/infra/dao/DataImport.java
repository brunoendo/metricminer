package org.metricminer.infra.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.metricminer.infra.components.SessionFactoryCreator;
import org.metricminer.model.User;
import org.metricminer.model.UserRole;



public class DataImport {
	
	public static void main(String[] args) {
		new DataImport().run();
	}

	private void run() {
		SessionFactoryCreator sessionFactoryCreator = new SessionFactoryCreator();
		sessionFactoryCreator.create();
		sessionFactoryCreator.dropAndCreate();
		SessionFactory sf = sessionFactoryCreator.getInstance();
		Session session = sf.openSession();
		
		session.beginTransaction();
		
		User user = new User();
		user.setName("Francisco Sokol");
		user.setPassword("9c2f5ce0472220c016a8a77822c22d211ab9233a7083bbe009b0db86380b6135");
		user.setRole(UserRole.ADMINISTRATOR);
		
		session.getTransaction().commit();
		session.close();
		sf.close();
	}

}
