package io.microanut.quartz.configuration;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.EachProperty;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.TriggerBuilder;

import java.util.Map;

@EachProperty(value = QuartzConfiguration.PREFIX + "." + QuartzTriggerConfiguration.PREFIX, list = true)
public class QuartzTriggerConfiguration {
    public static final String PREFIX = "triggers";

    public static final String DEFAULT_CLIENT = "default";

    @ConfigurationBuilder(prefixes = {"set", "with", ""}, excludes = {"jobDataMap","usingJobData","jobData"})
    private TriggerBuilder trigger = TriggerBuilder.newTrigger();
    @ConfigurationBuilder(value = "job", prefixes = {"set", "with", ""}, excludes = {"jobDataMap","usingJobData","jobData"})
    private JobBuilder job = JobBuilder.newJob();
    private String client = DEFAULT_CLIENT;

    public void setCron(String cron) {
        trigger.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public void setData(Map<String, Object> data){
        job.setJobData(new JobDataMap(data));
    }

    public TriggerBuilder getTrigger() {
        return trigger;
    }

    public JobBuilder getJob() {
        return job;
    }

}
