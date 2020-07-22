package io.micronaut.quartz.docs.scheduler

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import java.util.*

class MessageJob1 : Job {
    var messages = Collections.synchronizedList(ArrayList<String>())

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        val map = context.jobDetail.jobDataMap
        messages.add(map.getString("message"))
    }
}
