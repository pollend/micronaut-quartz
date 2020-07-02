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
package io.microanut.quartz.annotation;

import io.microanut.quartz.intercept.QuartzHandlerIntroductionAdvice;
import io.micronaut.aop.Around;
import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.AliasFor;
import io.micronaut.context.annotation.Type;
import org.quartz.Job;

import javax.inject.Named;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Introduction
@Around
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Type(QuartzHandlerIntroductionAdvice.class)
@Documented
@Named
public @interface ScheduleOn {

    /**
     * id reference for scheduler
     */
    @AliasFor(annotation = Named.class, member = "value")
    String id() default "default";

    /**
     * target job for that will be used to schedule the job.
     */
    Class<? extends Job> value();

    /**
     * job is going to be scheduled.
     */
    boolean schedule() default false;

}