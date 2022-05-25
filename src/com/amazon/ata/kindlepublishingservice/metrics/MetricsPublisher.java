package com.amazon.ata.kindlepublishingservice.metrics;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import javax.inject.Inject;

/**
 * Contains operations for publishing metrics.
 */
public class MetricsPublisher {

    private AmazonCloudWatch cloudWatch;

    /**
     * Creates a metrics publisher with the given AmazonCloudWatch.
     * @param cloudWatch AmazonCloudWatch
     */
    @Inject
    public MetricsPublisher (final AmazonCloudWatch cloudWatch) {
        this.cloudWatch = cloudWatch;
    }

}
