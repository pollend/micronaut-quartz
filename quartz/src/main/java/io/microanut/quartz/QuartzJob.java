package io.microanut.quartz;

import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.AliasFor;
import io.micronaut.context.annotation.Type;

import javax.inject.Singleton;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Introduction
@Singleton
public @interface QuartzJob {

    /**
     * Same as {@link #id()}.
     *
     * @return The id of the client
     */
    @AliasFor(member = "id")
    String value() default "";

    /**
     * @return The id of the client
     */
    @AliasFor(member = "value")
    String id() default "";


}
