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

import io.micronaut.context.BeanContext;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class MicronautJobFactory implements JobFactory {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BeanContext beanContext;

    public MicronautJobFactory(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = bundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        try {
            if (log.isDebugEnabled()) {
                log.debug(
                    "Producing instance of Job '" + jobDetail.getKey() +
                        "', class=" + jobClass.getName());
            }
            return beanContext.createBean(jobClass);
        } catch (Exception e) {
            SchedulerException se = new SchedulerException(
                "Problem instantiating class '"
                    + jobDetail.getJobClass().getName() + "'", e);
            throw se;
        }

    }
}
