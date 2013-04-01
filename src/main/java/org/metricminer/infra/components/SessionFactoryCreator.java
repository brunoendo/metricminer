package org.metricminer.infra.components;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.caelum.vraptor.ioc.ComponentFactory;

public class SessionFactoryCreator implements ComponentFactory<SessionFactory> {

	private Configuration cfg;
	private SessionFactory factory;

	@Override
	public SessionFactory getInstance() {
		return factory;
	}

	public void create() {
		cfg = new Configuration().configure(this.getClass().getResource(
				"/hibernate.cfg.xml"));
		init();
	}

	public void dropAndCreate() {
		destroy();
		new SchemaExport(cfg).drop(true, true);
		new SchemaExport(cfg).create(true, true);
		init();
	}

	private void init() {
		factory = cfg.buildSessionFactory();
	}

	void destroy() {
		if (!factory.isClosed()) {
			factory.close();
		}
		factory = null;
	}

}
