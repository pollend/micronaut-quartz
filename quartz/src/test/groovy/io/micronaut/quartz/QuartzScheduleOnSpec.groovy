package io.micronaut.quartz

import io.micronaut.context.ApplicationContext
import org.quartz.JobKey
import spock.lang.Specification

class QuartzScheduleOnSpec extends Specification {

    void "schedule job with key and value"(){
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled" : true,
            "quartz.clients.default.config-file": "io.microanut.quartz.properties"
        ])

        when:
        SampleSchedule schedule = ctx.createBean(SampleSchedule.class)

        then:
        schedule.withJobKey(new JobKey("hello_world"), "hello world")
    }

}
