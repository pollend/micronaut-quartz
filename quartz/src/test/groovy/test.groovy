import io.micronaut.annotation.processing.test.AbstractTypeElementSpec
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.naming.NameUtils

class test extends AbstractTypeElementSpec {
    void "test instance of job"() {
        given:
        def introspection = buildBeanIntrospection('test.Test', '''
@QuartzJob
public class SampleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}

''')
        expect:
        introspection != null

    }

    protected BeanIntrospection buildBeanIntrospection(String className, String cls) {
        def beanDefName= '$' + NameUtils.getSimpleName(className) + '$Introspection'
        def packageName = NameUtils.getPackageName(className)
        String beanFullName = "${packageName}.${beanDefName}"

        ClassLoader classLoader = buildClassLoader(className, cls)
        return (BeanIntrospection)classLoader.loadClass(beanFullName).newInstance()
    }
}
