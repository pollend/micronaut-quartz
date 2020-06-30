package io.microanut.quartz;

import io.micronaut.context.BeanResolutionContext;
import io.micronaut.context.scope.CustomScope;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.BeanIdentifier;

import javax.inject.Provider;
import java.util.Optional;

public class QuartzJobScope implements CustomScope<QuartzJob> {
    @Override
    public Class<QuartzJob> annotationType() {
        return QuartzJob.class;
    }

    @Override
    public <T> T get(BeanResolutionContext resolutionContext, BeanDefinition<T> beanDefinition, BeanIdentifier identifier, Provider<T> provider) {

        BeanResolutionContext.Segment segment = resolutionContext.getPath().currentSegment().orElseThrow(() ->
            new IllegalStateException("@KafkaClient used in invalid location")
        );
        return null;
    }

    @Override
    public <T> Optional<T> remove(BeanIdentifier identifier) {
        return Optional.empty();
    }
}
