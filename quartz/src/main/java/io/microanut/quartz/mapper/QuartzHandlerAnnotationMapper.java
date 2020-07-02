/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microanut.quartz.mapper;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.microanut.quartz.annotation.ScheduleOn;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.AnnotationValueBuilder;
import io.micronaut.inject.annotation.NamedAnnotationMapper;
import io.micronaut.inject.visitor.VisitorContext;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class QuartzHandlerAnnotationMapper implements NamedAnnotationMapper {
    @NonNull
    @Override
    public String getName() {
        return "io.microanut.quartz.annotation.QuartzHandler";
    }

    @Override
    public List<AnnotationValue<?>> map(AnnotationValue<Annotation> annotation, VisitorContext visitorContext) {
        final AnnotationValueBuilder<ScheduleOn> builder = AnnotationValue.builder(ScheduleOn.class);
        annotation.booleanValue("schedule").ifPresent(s -> builder.member("schedule", s));
        annotation.classValue("target").ifPresent(s -> builder.member("target", s));
        AnnotationValue<ScheduleOn> ann = builder.build();
        return Collections.singletonList(ann);
    }
}
