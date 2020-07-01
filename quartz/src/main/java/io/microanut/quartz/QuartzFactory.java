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

import io.microanut.quartz.annotation.QuartzJob;
import io.microanut.quartz.configuration.QuartzClientConfiguration;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.inject.InjectionPoint;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.DateBuilder.*;

@Factory
public class QuartzFactory {

    @EachBean(Job.class)
    public Optional<JobBuilder> job( Job job) {
//        Optional<AnnotationValue<QuartzJob>> opt = injectionPoint.findAnnotation(QuartzJob.class);
//        if (!opt.isPresent()) {
//            return Optional.empty();
//        }


        return Optional.of(JobBuilder.newJob(job.getClass()));
    }

    @Singleton
    @EachBean(QuartzClientConfiguration.class)
    public Scheduler scheduler(QuartzClientConfiguration configuration, Collection<JobBuilder> jobs) throws SchedulerException {
        Scheduler scheduler = configuration.getBuilder().getScheduler();


        return scheduler;
    }
}
