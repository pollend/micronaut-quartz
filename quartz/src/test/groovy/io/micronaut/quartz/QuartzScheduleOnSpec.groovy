package io.micronaut.quartz

import io.micronaut.context.ApplicationContext
import org.quartz.JobKey
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class QuartzScheduleOnSpec extends Specification {

    void "schedule job"() {
        given:
        PollingConditions conditions = new PollingConditions(timeout: 30, delay: 1)
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled"                    : true,
            "quartz.clients.default.config-file": ""
        ])

        when:
        SampleSchedule sampleSchedule = ctx.getBean(SampleSchedule.class)
        PayloadContainer payloadContainer = ctx.getBean(PayloadContainer.class)

        then:
        sampleSchedule.withJobKey(new JobKey("one", "group1"), "one")
        sampleSchedule.withJobKey(new JobKey("two", "group1"), "two")
        sampleSchedule.withJobKey(new JobKey("three", "group1"), "three")
        conditions.eventually {
            payloadContainer.arrQueue.size() == 3
            payloadContainer.arrQueue.contains("one")
            payloadContainer.arrQueue.contains("two")
            payloadContainer.arrQueue.contains("three")
        }
    }

}
