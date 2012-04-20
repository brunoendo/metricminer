package tasks.metric.fanout;

import tasks.metric.common.Metric;
import tasks.metric.common.MetricFactory;

public class FanOutMetricFactory implements MetricFactory {

    @Override
    public Metric build() {
        return new FanOutMetric();
    }

}
