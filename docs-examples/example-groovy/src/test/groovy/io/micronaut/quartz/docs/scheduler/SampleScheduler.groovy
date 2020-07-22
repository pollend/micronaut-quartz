package io.micronaut.quartz.docs.scheduler

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzKey
import io.micronaut.quartz.annotation.QuartzSchedule
import io.micronaut.quartz.annotation.ScheduleOn
import org.quartz.JobKey
import org.quartz.Trigger
// end::imports[]

// tag::clazz[]
@QuartzSchedule
interface SampleScheduler {
    @ScheduleOn(MessageJob1.class)
    void messageWithoutKey(@QuartzKey("message") String value);

    @ScheduleOn(MessageJob1.class)
    void messageJobWithKey(JobKey key, @QuartzKey("message") String value)

    @ScheduleOn(MessageJob1.class)
    void messageJobWithTrigger(JobKey key, Trigger trigger, @QuartzKey("message") String value)
}
// end::clazz[]
