package io.microanut.quartz.intercept;

import io.microanut.quartz.annotation.QuartzJob;
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
import org.quartz.TriggerBuilder;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.TriggerBuilder.newTrigger;

@Singleton
public class QuartzJobIntroductionAdvice implements MethodInterceptor<Object,Object> {
    private final BeanContext beanContext;

    public QuartzJobIntroductionAdvice(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        if (context.hasAnnotation(QuartzJob.class)) {
            AnnotationValue<QuartzJob> job = context.findAnnotation(QuartzJob.class).orElseThrow(() -> new IllegalStateException("No @KafkaClient annotation present on method: " + context));

            String client = job.stringValue("value").orElse("default");
            Class<? extends Job> classz = (Class<? extends Job>) job.classValue("target").orElseThrow(() -> new IllegalStateException("No Target Job provided"));
            JobBuilder jobBuilder = JobBuilder.newJob(classz);
            TriggerBuilder triggerBuilder = newTrigger();


            Scheduler scheduler = beanContext.findBean(Scheduler.class, Qualifiers.byName(client)).orElseThrow(() -> new IllegalStateException("Unknown Scheduler by name: " + client));

            Argument[] arguments = context.getArguments();
            Object[] parameterValues = context.getParameterValues();
            Map<String, Object> jobData = new HashMap<String, Object>();
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
                } else if(!argument.isContainerType() && Trigger.class.isAssignableFrom(argument.getFirstTypeVariable().orElse(Argument.OBJECT_ARGUMENT).getType())){

                }
            }
            jobBuilder.setJobData(new JobDataMap(jobData));
            try {
                scheduler.scheduleJob(jobBuilder.build(), triggerBuilder.build());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return context.proceed();
    }
}
