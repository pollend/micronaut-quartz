package io.micronaut.quartz.docs.quickstart

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzKey
import io.micronaut.quartz.annotation.QuartzSchedule
import io.micronaut.quartz.annotation.ScheduleOn
import org.quartz.JobKey
// end:imports[]

// tag::clazz[]
@QuartzSchedule()
interface MessageScheduler {
    @ScheduleOn(MessageJob::class)
    fun sendMessage(key: JobKey, @QuartzKey("message") message: String)
}
// end::clazz[]
