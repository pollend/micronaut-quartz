package io.micronaut.quartz

import io.microanut.quartz.annotation.QuartzJob
import io.microanut.quartz.annotation.QuartzKey
import io.micronaut.context.ApplicationContext
import org.quartz.*
import spock.lang.Specification

import javax.inject.Singleton

class QuartzSample extends Specification{
   @Singleton
   class Job1 implements Job {

        @Override
        void execute(JobExecutionContext context) throws JobExecutionException {
            String result = context.get("test");
        }
    }

    interface Target {
        @QuartzJob(target = Job1.class)
        void test(JobKey key, @QuartzKey(value = "test") String hello);

        @QuartzJob(target = Job1.class)
        void test(JobKey key, Trigger trigger, @QuartzKey(value = "test") String hello);
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
