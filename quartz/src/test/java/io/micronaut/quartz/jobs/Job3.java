package io.micronaut.quartz.jobs;

import io.micronaut.quartz.annotation.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Singleton;

@QuartzJob
@Singleton
public class Job3 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
