package io.micronaut.quartz.docs.quickstart

// tag::imports[]
import io.micronaut.quartz.annotation.QuartzJob
import org.quartz.Job
import org.quartz.JobExecutionContext
import javax.inject.Singleton
// end::imports[]

import java.util.*
import kotlin.collections.ArrayList

// tag::clazz[]
@QuartzJob // <1>
@Singleton
class MessageJob : Job { // <2>
    val messages: MutableList<String> = Collections.synchronizedList(ArrayList());
    override fun execute(context: JobExecutionContext?) {
        val map = context!!.jobDetail.jobDataMap
        messages.add(map.getString("message"));
    }
}
// end::clazz[]
