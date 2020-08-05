package io.pollend.quartz.jobs;

import io.pollend.quartz.annotation.QuartzJob;
import io.pollend.quartz.PayloadContainer;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

@QuartzJob
public class Job1 implements Job {
    private PayloadContainer payloadContainer;
    @Inject
    Job1(PayloadContainer result) {
        this.payloadContainer = result;
    }

    public PayloadContainer getPayloadContainer() {
        return payloadContainer;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap map = context.getJobDetail().getJobDataMap();
        payloadContainer.add(map.getString("value"));

    }
}
