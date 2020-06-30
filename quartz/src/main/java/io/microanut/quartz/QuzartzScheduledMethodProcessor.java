package io.microanut.quartz;

import io.micronaut.context.processor.ExecutableMethodProcessor;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;

import java.util.function.Consumer;

public class QuzartzScheduledMethodProcessor implements ExecutableMethodProcessor<QuartzJob> {
    @Override
    public void process(BeanDefinition<?> beanDefinition, ExecutableMethod<?, ?> method) {
        Job job = new Job() {
            @Override
            public void execute(JobExecutionContext context) throws JobExecutionException {

            }
        };

        JobBuilder.newJob(job.getClass()).build();
    }
}
