package tasks;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.List;

import model.Project;
import model.SourceCode;
import model.Task;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import tasks.metric.common.Metric;
import tasks.metric.common.MetricResult;
import tasks.runner.RunnableTask;
import br.com.caelum.revolution.domain.Artifact;

public class CalculateMetricTask implements RunnableTask {

    private Task task;
    private Metric metric;
    private Session session;
    private static Logger log = Logger.getLogger(CalculateMetricTask.class);

    public CalculateMetricTask(Task task, Metric metric, Session session) {
        this.task = task;
        this.metric = metric;
        this.session = session;
    }

    @Override
    public void run() {
        Project project = task.getProject();
        List<Artifact> artifacts = project.getArtifacts();
        for (Artifact artifact : artifacts) {
            calculateMetricForAllVersionsOf(artifact);
        }

    }

    private void calculateMetricForAllVersionsOf(Artifact artifact) {
        for (SourceCode sourceCode : artifact.getSources()) {
            if (metric.shouldCalculateMetricOf(sourceCode.getName())) {
                calculateAndSaveResultsOf(sourceCode);
            }
        }
    }

    private void calculateAndSaveResultsOf(SourceCode sourceCode) {
        log.info("Calculating " + metric.getClass() + " for: " + sourceCode.getName() + " - "
                + sourceCode.getCommit().getCommitId());
        try {
            metric.calculate(new ByteArrayInputStream(sourceCode.getSourceBytesArray()));
            Collection<MetricResult> results = metric.resultsToPersistOf(sourceCode);
            for (MetricResult result : results) {
                session.save(result);
            }
        } catch (Throwable t) {
            log.error("Unable to calculate metric: ", t);
        }
    }

}
