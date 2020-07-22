package io.micronaut.quartz.docs.quickstart;

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
// end::imports[]

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// tag::clazz[]
@QuartzJob // <1>
@Singleton
public class MessageJob implements Job { // <2>

    List<String> messages = Collections.synchronizedList(new ArrayList<>());
    @Override
    public void execute(JobExecutionContext context)  {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        messages.add(map.getString("message"));
    }
}
// end::clazz[]
