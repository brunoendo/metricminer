package org.metricminer.worker;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.tasks.metric.CalculateAllMetricsTaskFactory;
import org.metricminer.tasks.scm.ParseSCMLogTaskFactory;
import org.metricminer.tasks.scm.RemoveSourceDirectoryTaskFactory;
import org.metricminer.tasks.scm.SCMCloneTaskFactory;
import org.metricminer.worker.representations.TaskRepresentation;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class InitialTasks {
	public List<TaskRepresentation> tasksFor(Long projectId) {
		List<TaskRepresentation> tasks = new ArrayList<>();
		
		tasks.add(new TaskRepresentation("Clone SCM", projectId, SCMCloneTaskFactory.class));
		tasks.add(new TaskRepresentation("Parse SCM logs", projectId, ParseSCMLogTaskFactory.class));
		tasks.add(new TaskRepresentation("Remove source code directory", projectId, RemoveSourceDirectoryTaskFactory.class));
		tasks.add(new TaskRepresentation("Calculate all metrics", projectId, CalculateAllMetricsTaskFactory.class));
		
		//TODO: pass configuration entries too
		//tasks.add(new TaskRepresentation("Caculate truck factor metric", projectId, CalculateProjectMetricTaskFactory.class));
		//calculateTruckFactor.addTaskConfigurationEntry(TaskConfigurationEntryKey.PROJECT_METRIC_FACTORY_CLASS,TruckFactorFactory.class.getCanonicalName());
		
		return tasks;

	}

}
