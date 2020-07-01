package io.microanut.quartz.bind;

import io.microanut.quartz.annotation.QuartzKey;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.convert.ArgumentConversionContext;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public class QuartzKeyBinder<T> implements AnnotationRecordBinder<QuartzKey, T> {
    @Override
    public Class<QuartzKey> annotationType() {
        return QuartzKey.class;
    }

    @Override
    public BindingResult<T> bind(ArgumentConversionContext<T> context, JobBuilder source) {

        AnnotationMetadata annotationMetadata = context.getAnnotationMetadata();
        String name = annotationMetadata.getValue(QuartzKey.class, "name", String.class).get();



        return null;
    }
}
