package io.micronaut.quartz.docs.quickstart

import io.kotlintest.eventually
import io.kotlintest.seconds
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.micronaut.context.ApplicationContext
import org.opentest4j.AssertionFailedError
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.Scheduler
import org.quartz.TriggerBuilder

class MessageJobSpec: BehaviorSpec() {
    init {
        given("test job creation") {
            val ctx = ApplicationContext.run(
                mutableMapOf(
                    "quartz.enabled" to true,
                    "quartz.clients.default.config-file" to "classpath:default.properties"
                )
            )
            `when`("the job is created") {
//                val job = ctx.getBean(MessageJob::class.java)

// tag::create_job[]
val scheduler = ctx.getBean(Scheduler::class.java)
scheduler.scheduleJob(JobBuilder.newJob(MessageJob::class.java).setJobData(
    JobDataMap(mutableMapOf(
        "message" to "My Message"
    ))
).build(), TriggerBuilder.newTrigger().startNow().build())
// end::create_job[]

                then("the message is retrieved") {
                    val job = ctx.getBean(MessageJob::class.java)
                    eventually(10.seconds, AssertionFailedError::class.java) {
                        job.messages.size shouldBe  1
                        job.messages.first() shouldBe "My Message"
                    }
                }
            }
        }
    }
}
