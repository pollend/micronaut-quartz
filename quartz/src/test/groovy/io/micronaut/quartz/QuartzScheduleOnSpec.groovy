package io.micronaut.quartz

import io.microanut.quartz.annotation.QuartzJob
import io.microanut.quartz.annotation.QuartzKey
import io.microanut.quartz.annotation.ScheduleOn
import io.micronaut.context.ApplicationContext
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobKey
import org.quartz.Trigger
import spock.lang.Specification

import javax.inject.Inject

class QuartzScheduleOnSpec extends Specification {

    @QuartzJob
    class Job1 implements Job {
        @Inject
        public Job1() {

        }

        @Override
        void execute(JobExecutionContext context) throws JobExecutionException {
        }
    }

    @QuartzJob
    class Job2 implements Job {
        @Inject
        public Job1() {

        }
        @Override
        void execute(JobExecutionContext context) throws JobExecutionException {

        }
    }
    interface SampleSchedule {
        @ScheduleOn(Job1.class)
        void test(JobKey key, @QuartzKey("test") String hello);

        @ScheduleOn(Job1.class)
        void test(JobKey key, Trigger trigger, @QuartzKey() String hello);
    }

    void "schedule job with key and value"(){
        given:
        ApplicationContext ctx = ApplicationContext.run()

    }

}
