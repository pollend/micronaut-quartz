package io.micronaut.quartz.docs.quickstart

import io.micronaut.context.ApplicationContext
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import spock.lang.Specification

class MessageJobSpec extends Specification {

    void "test job creation"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled"                    : true,
            "quartz.clients.default.config-file": "classpath:default.properties"
        ])
        MessageJob job = ctx.getBean(MessageJob.class);
        when:
// tag::create_job[]
Scheduler scheduler = ctx.getBean(Scheduler.class)
scheduler.scheduleJob(JobBuilder.newJob(MessageJob.class)
.setJobData(new JobDataMap([
    "message": "My Message"
])).build(), TriggerBuilder.newTrigger().startNow().build())
// end::create_job[]
        then:
        job.messages.size() == 1
        job.messages.first() == "My Message"

    }
}
