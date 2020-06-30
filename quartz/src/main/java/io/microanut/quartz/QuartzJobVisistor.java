package io.microanut.quartz;

import io.micronaut.inject.ast.ClassElement;
import io.micronaut.inject.visitor.TypeElementVisitor;
import io.micronaut.inject.visitor.VisitorContext;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class QuartzJobVisistor implements TypeElementVisitor<QuartzJob, Object> {
    /**
     * The position of the visitor.
     */
    public static final int POSITION = 100;

    protected static Map<String, JobDetail> jobs = new HashMap<>();

    @Inject
    public QuartzJobVisistor(){

    }

    @Override
    public int getOrder() {
        return POSITION;
    }

    @Override
    public void visitClass(ClassElement element, VisitorContext context) {
        if(element.hasDeclaredStereotype(QuartzJob.class)){

            JobBuilder.newJob(element.getType().getClass().asSubclass(Job.class));

        }
    }
}
