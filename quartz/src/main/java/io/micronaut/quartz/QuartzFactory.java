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
package io.micronaut.quartz;

import io.micronaut.quartz.configuration.QuartzClientConfiguration;
import io.micronaut.quartz.configuration.QuartzConfiguration;
import io.micronaut.quartz.configuration.QuartzTriggerConfiguration;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import org.quartz.Calendar;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Factory class that create an {@link Scheduler} beans.
 */
@Factory
public class QuartzFactory implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzFactory.class);

    private final BeanContext beanContext;
    private final QuartzConfiguration configuration;
    private final List<Scheduler> schedulers = new ArrayList<>(2);

    /**
     * @param beanContext   bean context
     * @param configuration quartz configuration
     */
    public QuartzFactory(BeanContext beanContext, QuartzConfiguration configuration) {
        this.beanContext = beanContext;
        this.configuration = configuration;
    }

    /**
     * create quartz {@link Scheduler}.
     *
     * @param clientConfiguration client configuration
     * @param triggers            list of triggers to start up initially with scheduler
     * @param jobFactory          factory for job
     * @return scheduler
     * @throws SchedulerException file on misconfigured scheduler
     */
    @EachBean(QuartzClientConfiguration.class)
    public Scheduler scheduler(
        QuartzClientConfiguration clientConfiguration,
        Collection<QuartzTriggerConfiguration> triggers,
        JobFactory jobFactory) throws SchedulerException {

        Scheduler scheduler = clientConfiguration.getBuilder().getScheduler();
        for (QuartzTriggerConfiguration trigger : triggers) {
            if (trigger.getClient().equals(clientConfiguration.getName())) {
                if (trigger.getTarget().isPresent()) {
                    scheduler.scheduleJob(trigger.getJob().build(), trigger.getTrigger().build());
                } else {
                    scheduler.scheduleJob(trigger.getTrigger().build());
                }
            }
        }
        for (Map.Entry<String, Class<? extends Calendar>> entry : configuration.getCalenders().entrySet()) {
            Class<? extends Calendar> value = entry.getValue();
            try {
                scheduler.addCalendar(entry.getKey(), beanContext.getBean(value), true, false);
            } catch (SchedulerException e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Error adding calendar [" + entry.getKey() + "]: " + e.getMessage(), e);
                }
            }
        }

        scheduler.setJobFactory(jobFactory);
        scheduler.start();
        schedulers.add(scheduler);
        return scheduler;
    }

    @Override
    @PreDestroy
    public void close() {
        for (Scheduler scheduler : schedulers) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Error closing data source [" + scheduler + "]: " + e.getMessage(), e);
                }
            }
        }
    }
}
