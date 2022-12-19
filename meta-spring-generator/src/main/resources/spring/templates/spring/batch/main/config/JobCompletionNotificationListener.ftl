<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * This is an example of a JobCompletionNotificationListener.
 * Wire this class into the Job bean (in the BatchConfiguration.class) to use it;
 * something like:
 *      jobBuilderFactory.get("job")
 *          .listener(new JobCompletionNotificationListener())
 *          .start(...).end(...).build();
 *
 */
@Component
@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished. Maybe print some summary information, like the number of records processed.");
        }
    }

}


