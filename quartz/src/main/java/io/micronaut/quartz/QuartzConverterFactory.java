package io.micronaut.quartz;

import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.convert.TypeConverterRegistrar;
import org.quartz.JobDataMap;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;

@Singleton
public class QuartzConverterFactory implements TypeConverterRegistrar {
    @Override
    public void register(ConversionService<?> conversionService) {
        conversionService.addConverter(
            Map.class,
            JobDataMap.class,
            (map, targetType, context) -> Optional.of(new JobDataMap(map))
        );
    }
}
