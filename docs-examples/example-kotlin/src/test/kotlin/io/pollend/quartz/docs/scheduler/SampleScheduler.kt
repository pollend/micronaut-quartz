package io.pollend.quartz.docs.scheduler

// tag::imports[]
import io.pollend.quartz.annotation.QuartzKey
import io.pollend.quartz.annotation.ScheduleOn
import org.quartz.JobKey
import org.quartz.Trigger
// end::imports[]

// tag::clazz[]
interface SampleScheduler {

    @ScheduleOn(MessageJob1::class)
    fun messageWithoutKey(@QuartzKey("message") value: String?)

    @ScheduleOn(MessageJob1::class)
    fun messageJobWithKey(key: JobKey?, @QuartzKey("message") value: String?)

    @ScheduleOn(MessageJob1::class)
    fun messageJobWithTrigger(key: JobKey?, trigger: Trigger?, @QuartzKey("message") value: String?)
}
// end::clazz[]
