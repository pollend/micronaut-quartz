package io.microanut.quartz.bind;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.microanut.quartz.annotation.QuartzJob;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.AnnotationValueBuilder;
import io.micronaut.inject.annotation.NamedAnnotationMapper;
import io.micronaut.inject.visitor.VisitorContext;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class QuartzJobAnnotationMapper implements NamedAnnotationMapper {
    @NonNull
    @Override
    public String getName() {
        return "io.microanut.quartz.annotation.QuartzJob";
    }

    @Override
    public List<AnnotationValue<?>> map(AnnotationValue<Annotation> annotation, VisitorContext visitorContext) {
        final AnnotationValueBuilder<QuartzJob> builder = AnnotationValue.builder(QuartzJob.class);
       builder.member("clients", String.valueOf(annotation.stringValue("clients")));
        AnnotationValue<QuartzJob> ann = builder.build();
        return Collections.singletonList(ann);
    }
}
