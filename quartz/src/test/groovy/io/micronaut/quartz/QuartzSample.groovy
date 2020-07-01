package io.micronaut.quartz

import io.microanut.quartz.annotation.QuartzJob
import io.micronaut.context.ApplicationContext
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import spock.lang.Specification

import javax.inject.Singleton

class QuartzSample extends Specification{
   @Singleton
   class Job1 implements Job {

        @Override
        void execute(JobExecutionContext context) throws JobExecutionException {

        }
    }

    interface Jbb {
        @QuartzJob(target = Job1.class)
        void test();
    }

    void  "test job"(){
        given:
        ApplicationContext ctx = ApplicationContext.run()

        when:
        Collection<Job> builders = ctx.getBeansOfType(Job.class);


        then:
        builders.size() == 1


    }

}
