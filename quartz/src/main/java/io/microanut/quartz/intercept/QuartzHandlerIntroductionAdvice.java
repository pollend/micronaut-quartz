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
package io.microanut.quartz.intercept;

import io.microanut.quartz.annotation.ScheduleOn;
import io.microanut.quartz.annotation.QuartzKey;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.type.Argument;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.quartz.TriggerBuilder.newTrigger;

@Singleton
public class QuartzHandlerIntroductionAdvice implements MethodInterceptor<Object,Object> {
    private final BeanContext beanContext;

    public QuartzHandlerIntroductionAdvice(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        if (context.hasAnnotation(ScheduleOn.class)) {
            AnnotationValue<ScheduleOn> jobAnnotation = context.findAnnotation(ScheduleOn.class).orElseThrow(() -> new IllegalStateException("No @KafkaClient annotation present on method: " + context));

            String client = jobAnnotation.stringValue("value").orElse("default");
            boolean isScheduled = jobAnnotation.booleanValue("schedule").orElse(false);
            Class<? extends Job> classz = (Class<? extends Job>) jobAnnotation.classValue("target").orElseThrow(() -> new IllegalStateException("No Target Job provided"));
            JobBuilder jobBuilder = JobBuilder.newJob(classz);
            Optional<Trigger> trigger = Optional.empty();
            Scheduler scheduler = beanContext.findBean(Scheduler.class, Qualifiers.byName(client)).orElseThrow(() -> new IllegalStateException("Unknown Scheduler by name: " + client));

            Argument[] arguments = context.getArguments();
            Object[] parameterValues = context.getParameterValues();
            Map<String, Object> jobData = new HashMap<>();
            for (int i = 0; i < arguments.length; i++) {
                Argument argument = arguments[i];
                if (argument.isAnnotationPresent(QuartzKey.class)) {
                    final AnnotationMetadata annotationMetadata = argument.getAnnotationMetadata();
                    String argumentName = argument.getName();
                    String name = annotationMetadata.stringValue(QuartzKey.class, "name")
                        .orElse(argumentName);
                    jobData.put(name, parameterValues[i]);
                } else if (!argument.isContainerType() && JobKey.class.isAssignableFrom(argument.getFirstTypeVariable().orElse(Argument.OBJECT_ARGUMENT).getType())) {
                    jobBuilder.withIdentity((JobKey) parameterValues[i]);
                } else if (!argument.isContainerType() && Trigger.class.isAssignableFrom(argument.getFirstTypeVariable().orElse(Argument.OBJECT_ARGUMENT).getType())) {
                    trigger = Optional.of((Trigger) parameterValues[i]);
                }
            }
            if (jobData.size() == 0) {
                jobBuilder.setJobData(new JobDataMap(jobData));
            }

            try {
                if (isScheduled) {
                    scheduler.addJob(jobBuilder.build(), true);
                } else {
                    if (trigger.isPresent()) {
                        scheduler.scheduleJob(jobBuilder.build(), trigger.get());
                    } else {
                        scheduler.scheduleJob(jobBuilder.build(), newTrigger().startNow().build());
                    }
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return context.proceed();
    }
}
