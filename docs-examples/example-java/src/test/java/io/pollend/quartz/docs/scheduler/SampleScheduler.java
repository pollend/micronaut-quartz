package io.pollend.quartz.docs.scheduler;

// tag::imports[]
import io.pollend.quartz.annotation.QuartzKey;
import io.pollend.quartz.annotation.ScheduleOn;
import org.quartz.JobKey;
import org.quartz.Trigger;
// end::imports[]

// tag::clazz[]
public interface SampleScheduler {
    @ScheduleOn(MessageJob1.class)
    void messageWithoutKey(@QuartzKey("message") String value);

    @ScheduleOn(MessageJob1.class)
    void messageJobWithKey(JobKey key, @QuartzKey("message") String value);

    @ScheduleOn(MessageJob1.class)
    void messageJobWithTrigger(JobKey key, Trigger trigger, @QuartzKey("message") String value);
}
// end::clazz[]
