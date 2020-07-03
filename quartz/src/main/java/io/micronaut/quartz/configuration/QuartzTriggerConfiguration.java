/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.quartz.configuration;

import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.EachProperty;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.TriggerBuilder;

import java.util.Map;

/**
 * trigger configuration used when quartz scheduler is created.
 */
@EachProperty(value = QuartzConfiguration.PREFIX + "." + QuartzTriggerConfiguration.PREFIX, list = true)
public class QuartzTriggerConfiguration {
    public static final String PREFIX = "triggers";

    public static final String DEFAULT_CLIENT = "default";

    @ConfigurationBuilder(prefixes = {"set", "with", ""}, excludes = {"jobDataMap", "usingJobData", "jobData"})
    private TriggerBuilder trigger = TriggerBuilder.newTrigger();
    @ConfigurationBuilder(value = "job", prefixes = {"set", "with", ""}, excludes = {"jobDataMap", "usingJobData", "jobData"})
    private JobBuilder job = JobBuilder.newJob();
    private String client = DEFAULT_CLIENT;

    /**
     * @param cron set a cron tab that triggers for {@link org.quartz.Job}
     */
    public void setCron(String cron) {
        trigger.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    }

    /**
     * @param client id for scheduler
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return client id for scheduler
     */
    public String getClient() {
        return client;
    }

    /**
     * @param data set data for job detail
     */
    public void setData(Map<String, Object> data) {
        job.setJobData(new JobDataMap(data));
    }

    /**
     * @return trigger used with job
     */
    public TriggerBuilder getTrigger() {
        return trigger;
    }

    /**
     * @return job tied to trigger
     */
    public JobBuilder getJob() {
        return job;
    }
}
