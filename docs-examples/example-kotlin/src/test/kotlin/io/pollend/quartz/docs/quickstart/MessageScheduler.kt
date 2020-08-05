package io.pollend.quartz.docs.quickstart

// tag::imports[]
import io.pollend.quartz.annotation.QuartzKey
import io.pollend.quartz.annotation.QuartzSchedule
import io.pollend.quartz.annotation.ScheduleOn
import org.quartz.JobKey
// end:imports[]

// tag::clazz[]
@QuartzSchedule()
interface MessageScheduler {
    @ScheduleOn(MessageJob::class)
    fun sendMessage(key: JobKey, @QuartzKey("message") message: String)
}
// end::clazz[]
