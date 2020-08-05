package io.pollend.quartz.docs.quickstart;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;

import java.util.HashMap;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class MessageJobSpec {
    @Test
    void testJobCreation() throws SchedulerException {
        ApplicationContext ctx = ApplicationContext.run(new HashMap<String, Object>() {
            {
                put("quartz.enabled", true);
                put("quartz.clients.default.config-file", "classpath:default.properties");
            }
        });

// tag::create_job[]
Scheduler scheduler = ctx.getBean(Scheduler.class);
scheduler.scheduleJob(JobBuilder.newJob(MessageJob.class)
    .setJobData(new JobDataMap(new HashMap<String, Object>() {{
        put("message", "My Message");
    }})).build(), TriggerBuilder.newTrigger().startNow().build());
// end::create_job[]

        MessageJob job = ctx.getBean(MessageJob.class);
        try {
            await().atMost(5, SECONDS).until(() ->
                job.messages.size() == 1 &&
                    job.messages.get(0).equals("My Message"));
        } finally {
            ctx.close();
        }

    }
}
