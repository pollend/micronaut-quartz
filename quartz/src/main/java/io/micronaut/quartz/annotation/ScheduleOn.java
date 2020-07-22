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
package io.micronaut.quartz.annotation;

import io.micronaut.core.bind.annotation.Bindable;
import org.quartz.Job;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for scheduling a quartz job.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Bindable
public @interface ScheduleOn {

    /**
     * @return id reference for scheduler.
     */
    String id() default "default";

    /**
     * @return target job for that will be used to schedule the job.
     */
    Class<? extends Job> value();

    /**
     * @return Set job status for {@link org.quartz.JobDetail#isDurable()}.
     */
    boolean durability() default false;

    /**
     * @return Set job status for {@link org.quartz.JobDetail#requestsRecovery()}.
     */
    boolean recoverable() default false;

    /**
     * @return Set job status for {@link org.quartz.JobDetail#getDescription()}.
     */
    String description() default "";
}
