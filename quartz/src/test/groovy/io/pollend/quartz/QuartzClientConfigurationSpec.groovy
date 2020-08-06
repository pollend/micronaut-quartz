package io.pollend.quartz

import io.micronaut.context.ApplicationContext
import io.pollend.quartz.configuration.QuartzClientConfiguration
import io.pollend.quartz.configuration.QuartzTriggerConfiguration
import io.pollend.quartz.jobs.Job1
import org.quartz.Scheduler
import org.quartz.Trigger
import spock.lang.Specification

class QuartzClientConfigurationSpec extends Specification{
    void "default configuration"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled"                    : true,
            "quartz.clients.default.config-file": "classpath:example.properties"
        ])


        when:
        QuartzClientConfiguration configuration = ctx.getBean(QuartzClientConfiguration.class)
        Scheduler scheduler = ctx.getBean(Scheduler.class)


        then:
        configuration.name == "default"
        configuration.configFile == "classpath:example.properties"
        scheduler.getSchedulerName() == "MyClusteredScheduler"
    }

    void "trigger and job configuration"() {
        given:
        ApplicationContext ctx = ApplicationContext.run([
            "quartz.enabled"                    : true,
            "quartz.clients.default.config-file": "classpath:example.properties",
            "quartz.clients.two.config-file"    : "classpath:example.properties",
            "quartz.triggers[0].start-now"      : true,
            "quartz.triggers[0].description"    : "description of the trigger",
            "quartz.triggers[0].target"         : "io.pollend.quartz.jobs.Job1"
        ])

        when:
        Date now = new Date()
        Collection<QuartzClientConfiguration> configuration = ctx.getBeansOfType(QuartzClientConfiguration.class)
        Collection<QuartzTriggerConfiguration> triggers = ctx.getBeansOfType(QuartzTriggerConfiguration.class)


        then:
        triggers.size() == 1

        Trigger trigger = triggers[0].getTrigger().build()
        Math.abs(trigger.startTime.getTime() - now.getTime()) < 200
        trigger.getDescription() == "description of the trigger"

        triggers[0].target.isPresent()
        triggers[0].target.get().is(Job1.class)


    }
}
