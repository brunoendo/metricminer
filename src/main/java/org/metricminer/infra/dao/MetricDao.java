package org.metricminer.infra.dao;

import java.util.List;






import org.hibernate.Session;
import org.metricminer.tasks.metric.AvaiableMetricResults;
import org.metricminer.tasks.metric.MetricInfo;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class MetricDao {

	private Session session;
	private AvaiableMetricResults avaiableMetrics;

	public MetricDao(Session session, AvaiableMetricResults avaiableMetrics) {
		this.session = session;
		this.avaiableMetrics = avaiableMetrics;
	}
	
	public List<ColumnMetadata> getColumns(Class<?> metric) {
		String sql = "SELECT COLUMN_NAME, DATA_TYPE FROM "
				+ "information_schema.COLUMNS "
				+ "WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=:class";
		return ColumnMetadata.build(session.createSQLQuery(sql)
			.setString("class", metric.getSimpleName())
			.list());
	}

	public List<MetricInfo> listAvaiableResults() {
		return avaiableMetrics.getMetrics();
	}
}
