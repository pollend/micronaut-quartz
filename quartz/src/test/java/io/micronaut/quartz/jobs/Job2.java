package io.micronaut.quartz.jobs;

import io.micronaut.quartz.annotation.QuartzJob;
import io.micronaut.quartz.PayloadContainer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

@QuartzJob
public class Job2 implements Job {
    private PayloadContainer payloadContainer;

    @Inject
    public void Job1(PayloadContainer result) {
        this.payloadContainer = result;
    }

    public PayloadContainer getPayloadContainer() {
        return payloadContainer;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        payloadContainer.add((String) context.get("value"));
    }
}
