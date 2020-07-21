package io.micronaut.quartz.docs.quickstart

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzJob
import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import javax.inject.Singleton
// end::imports[]

// tag::clazz[]
@QuartzJob // <1>
@Singleton
class MessageJob implements Job { // <2>
    public List<String> messages = Collections.synchronizedList(new ArrayList<>())
    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.jobDetail.jobDataMap;
        messages.add(map.getString("message"));
    }
}
// end::clazz[]
