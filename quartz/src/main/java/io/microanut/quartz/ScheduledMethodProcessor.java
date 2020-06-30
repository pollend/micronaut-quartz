package io.microanut.quartz;

import io.micronaut.context.processor.ExecutableMethodProcessor;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import io.micronaut.scheduling.annotation.Scheduled;

public class ScheduledMethodProcessor implements ExecutableMethodProcessor<Scheduled> {
    @Override
    public void process(BeanDefinition<?> beanDefinition, ExecutableMethod<?, ?> method) {

    }
}
