package io.pollend.quartz;

import io.pollend.quartz.annotation.QuartzKey;
import io.pollend.quartz.annotation.QuartzSchedule;
import io.pollend.quartz.annotation.ScheduleOn;
import io.pollend.quartz.jobs.Job1;
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
