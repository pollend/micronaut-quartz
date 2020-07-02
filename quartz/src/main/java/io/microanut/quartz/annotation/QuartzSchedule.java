package io.microanut.quartz.annotation;

import io.microanut.quartz.intercept.QuartzScheduleIntroductionAdvice;
import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Type;

import javax.inject.Scope;
import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Scope
@Introduction
@Type(QuartzScheduleIntroductionAdvice.class)
@Singleton
public @interface QuartzSchedule {
}
