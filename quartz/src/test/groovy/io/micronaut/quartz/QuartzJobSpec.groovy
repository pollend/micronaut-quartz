package io.micronaut.quartz

import io.microanut.quartz.MicronautJobFactory
import io.microanut.quartz.QuartzFactory
import io.micronaut.context.ApplicationContext
import io.micronaut.quartz.jobs.Job1
import io.micronaut.quartz.jobs.Job2
import org.quartz.spi.TriggerFiredBundle
import spock.lang.Specification

class QuartzJobSpec extends Specification{
    void "validate job creation"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled" : true,
            "quartz.clients.default": true
        ])

        when:
        Job2 job = ctx.createBean(Job2.class);
        Job2 job1 = ctx.createBean(Job2.class);
        Job1 job2 = ctx.createBean(Job1.class);

        then:
        job != null
        job1 != null
        job2 != null
        job != job1
        job.getPayloadContainer() == job1.getPayloadContainer()
    }
}
