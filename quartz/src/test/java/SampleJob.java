import io.microanut.quartz.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@QuartzJob
public class SampleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
