package io.micronaut.quartz.docs.quickstart

import io.micronaut.context.ApplicationContext
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class MessageJobSpec extends Specification {

    void "test job creation"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled"                    : true,
            "quartz.clients.default.config-file": "classpath:default.properties"
        ])
        MessageJob job = ctx.getBean(MessageJob.class);
        PollingConditions conditions = new PollingConditions(timeout: 5)

        when:
// tag::create_job[]
Scheduler scheduler = ctx.getBean(Scheduler.class)
scheduler.scheduleJob(JobBuilder.newJob(MessageJob.class)
    .setJobData(new JobDataMap([
        "message": "My Message"
    ])).build(), TriggerBuilder.newTrigger().startNow().build())
// end::create_job[]

        then:
        conditions.eventually {
            job.messages.size() == 1
            job.messages.first() == "My Message"
        }
    }
}
