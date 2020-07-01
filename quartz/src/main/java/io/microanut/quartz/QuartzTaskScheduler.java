package io.microanut.quartz;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.TaskScheduler;
import org.quartz.CronExpression;
import org.quartz.JobBuilder;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.DateBuilder.*;

import javax.inject.Named;
import java.text.ParseException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

@Named(TaskExecutors.SCHEDULED + "-quartz")
public class QuartzTaskScheduler implements TaskScheduler {
    @Override
    public ScheduledFuture<?> schedule(String cron, Runnable command) {
        return null;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(String cron, Callable<V> command) {

//        JobBuilder.newJob().ofType()

        newTrigger()
            .withSchedule(cronSchedule(cron))
            .forJob("test")
            .build();
        try {
            CronExpression expression = new CronExpression(cron);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ScheduledFuture<?> schedule(Duration delay, Runnable command) {
        return null;
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Duration delay, Callable<V> callable) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(@Nullable Duration initialDelay, Duration period, Runnable command) {
        return null;
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(@Nullable Duration initialDelay, Duration delay, Runnable command) {
        return null;
    }
}
