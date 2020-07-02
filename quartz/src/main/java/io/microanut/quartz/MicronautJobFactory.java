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
            if(log.isDebugEnabled()) {
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
