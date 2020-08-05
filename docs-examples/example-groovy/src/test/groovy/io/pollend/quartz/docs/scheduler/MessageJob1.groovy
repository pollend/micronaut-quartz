package io.pollend.quartz.docs.scheduler

import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class MessageJob1 implements Job {
    public List<String> messages = Collections.synchronizedList(new ArrayList<>())
    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.jobDetail.jobDataMap;
        messages.add(map.getString("message"));
    }
}
