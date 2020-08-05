package io.pollend.quartz.docs.quickstart;

// tag::imports[]
import io.pollend.quartz.annotation.QuartzKey;
import io.pollend.quartz.annotation.QuartzSchedule;
import io.pollend.quartz.annotation.ScheduleOn;
// end:imports[]

// tag::clazz[]
@QuartzSchedule()
public interface MessageScheduler {
    @ScheduleOn(MessageJob.class)
    void sendMessage(@QuartzKey("message") String message);
}
// end::clazz[]
