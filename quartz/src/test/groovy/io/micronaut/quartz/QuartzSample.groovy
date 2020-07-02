package io.micronaut.quartz

import io.microanut.quartz.annotation.ScheduleOn
import io.microanut.quartz.annotation.QuartzJob
import io.microanut.quartz.annotation.QuartzKey
import io.micronaut.context.ApplicationContext
import org.quartz.*
import spock.lang.Specification

import javax.inject.Inject
import javax.inject.Singleton

class QuartzSample extends Specification {
    @Singleton
    class SampleService {

    }

    @QuartzJob
    class Job1 implements Job {
        @Inject
        public Job1(SampleService service) {

        }

        @Override
        void execute(JobExecutionContext context) throws JobExecutionException {
            String result = context.get("test");
        }
    }

    interface Target {
        @ScheduleOn(target = Job1.class)
        void test(JobKey key, @QuartzKey(value = "test") String hello);

        @ScheduleOn(target = Job1.class)
        void test(JobKey key, Trigger trigger, @QuartzKey() String hello);
    }

    void "test job"() {
        given:
        ApplicationContext ctx = ApplicationContext.run()

        when:
        Collection<Job> builders = ctx.getBeansOfType(Job.class);

        then:
        builders.size() == 1

    }

}
