package org.metricminer.infra.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class StatisticsCreator implements ComponentFactory<Statistics> {

	private final SessionFactory sf;

	public StatisticsCreator(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public Statistics getInstance() {
		return sf.getStatistics();
	}

}
