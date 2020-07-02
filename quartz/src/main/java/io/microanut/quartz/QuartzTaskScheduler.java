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
