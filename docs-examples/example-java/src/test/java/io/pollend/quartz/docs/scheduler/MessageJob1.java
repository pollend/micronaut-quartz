package io.pollend.quartz.docs.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageJob1 implements Job {
    public List<String> messages = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        messages.add(map.getString("message"));
    }
}
