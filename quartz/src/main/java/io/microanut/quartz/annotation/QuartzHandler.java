package io.microanut.quartz.annotation;

import io.microanut.quartz.intercept.QuartzHandlerIntroductionAdvice;
import io.micronaut.aop.Around;
import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Type;
import org.quartz.Job;

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
public @interface QuartzHandler {

    String value() default "default";

    /**
     * job is going to be scheduled
     *
     * @return
     */
    boolean schedule() default false;

    /**
     * target job for that will be used to schedule the job
     * @return
     */
    Class<? extends Job> target();
}
