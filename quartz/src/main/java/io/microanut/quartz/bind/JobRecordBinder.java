package io.microanut.quartz.bind;

import io.micronaut.core.bind.ArgumentBinder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public interface JobRecordBinder<T> extends ArgumentBinder<T, JobBuilder> {
}
