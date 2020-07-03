package io.micronaut.quartz;

import io.micronaut.quartz.annotation.QuartzKey;
import io.micronaut.quartz.annotation.QuartzSchedule;
import io.micronaut.quartz.annotation.ScheduleOn;
import io.micronaut.quartz.jobs.Job1;
import org.quartz.JobKey;
import org.quartz.Trigger;

@QuartzSchedule()
public interface SampleSchedule {
    @ScheduleOn(Job1.class)
    void basicJob(JobKey key);

    @ScheduleOn(Job1.class)
    void withJobKey(JobKey key, @QuartzKey("value") String val);

    @ScheduleOn(Job1.class)
    void withTrigger(JobKey key, Trigger trigger, @QuartzKey("value") String val);
}
