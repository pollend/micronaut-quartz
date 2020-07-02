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

import io.microanut.quartz.configuration.QuartzClientConfiguration;
import io.microanut.quartz.configuration.QuartzConfiguration;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.quartz.Calendar;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Factory class that create an {@link Scheduler} beans.
 *
 */
@Factory
public class QuartzFactory {

    private final BeanContext beanContext;
    private final QuartzConfiguration configuration;

    private QuartzFactory(BeanContext beanContext, QuartzConfiguration configuration) {
        this.beanContext = beanContext;
        this.configuration = configuration;
    }

    @Singleton
    @EachBean(QuartzClientConfiguration.class)
    public Scheduler scheduler(QuartzClientConfiguration configuration, MicronautJobFactory jobFactory) throws SchedulerException {
        Scheduler scheduler = configuration.getBuilder().getScheduler();
        for (Map.Entry<String, Class<? extends Calendar>> entry : configuration.getCalenders().entrySet()) {
            String key = entry.getKey();
            Class<? extends Calendar> value = entry.getValue();
            try {
                scheduler.addCalendar(key, beanContext.getBean(value, Qualifiers.byName(configuration.getName())), true, false);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        for (QuartzClientConfiguration.TriggerConfiguration trigger : configuration.getTriggers()) {
            scheduler.scheduleJob(trigger.getJob().build(), trigger.getTrigger().build());
        }
        scheduler.setJobFactory(jobFactory);
        return scheduler;
    }
}
