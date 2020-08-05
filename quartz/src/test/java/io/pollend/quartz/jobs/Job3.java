package io.pollend.quartz.jobs;

import io.pollend.quartz.annotation.QuartzJob;
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
