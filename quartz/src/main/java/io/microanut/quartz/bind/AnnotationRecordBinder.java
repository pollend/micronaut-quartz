package io.microanut.quartz.bind;

import java.lang.annotation.Annotation;

public interface AnnotationRecordBinder<A extends Annotation, T> extends JobRecordBinder<T> {
    /**
     * @return The annotation type
     */
    Class<A> annotationType();
}
