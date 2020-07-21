package io.micronaut.quartz

import io.micronaut.context.ApplicationContext
import io.micronaut.quartz.jobs.Job1
import io.micronaut.quartz.jobs.Job2
import io.micronaut.quartz.jobs.Job3
import spock.lang.Specification

class QuartzJobSpec extends Specification{
    void "validate job creation"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled" : true,
            "quartz.clients.default": true
        ])

        when:
        Job2 job = ctx.getBean(Job2.class);
        Job2 job1 = ctx.getBean(Job2.class);
        Job1 job2 = ctx.getBean(Job1.class);
        Job3 job3 = ctx.getBean(Job3.class)
        Job3 job4 = ctx.getBean(Job3.class)

        then:
        job != null
        job1 != null
        job2 != null
        job != job1
        job3 == job4
        job.getPayloadContainer() == job1.getPayloadContainer()
    }
}
