package io.micronaut.quartz

import io.micronaut.context.ApplicationContext
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import spock.lang.Specification

class QuartzScheduleOnSpec extends Specification {

    void "schedule job with key and value"(){
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled" : true,
            "quartz.clients.default.config-file": "io.microanut.quartz.properties"
        ])

        when:
        SampleSchedule sampleSchedule = ctx.createBean(SampleSchedule.class)
        Scheduler scheduler = ctx.getBean(Scheduler.class)
        JobKey key = new JobKey("hello_world")

        then:
        sampleSchedule.withJobKey(key, "hello world")
        JobDetail detail = scheduler.getJobDetail()
        !detail.durable
        !detail.requestsRecovery()


    }

}
