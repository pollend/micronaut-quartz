package io.micronaut.quartz.docs.quickstart;

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzKey;
import io.micronaut.quartz.annotation.QuartzSchedule;
import io.micronaut.quartz.annotation.ScheduleOn;
import org.quartz.JobKey;
// end:imports[]

// tag::clazz[]
@QuartzSchedule()
public interface MessageScheduler {
    @ScheduleOn(MessageJob.class)
    void sendMessage(@QuartzKey("message") String message);
}
// end::clazz[]
