<#include "/common/Copyright.ftl">
package ${project.basePackage};

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Application {
    public static void main(String[] args) {
        // Enable graceful shutdowns so Spring Batch can correctly
        // set the job's execution status to FAILED and sets its END_TIME.
        // Otherwise, if the job restarts (e.g., the container or pod is restarted),
        // the job status will still be STARTED and END_TIME will be null, and
        // Batch will prevent the job from re-starting (since Batch prevents
        // concurrent runs of the same job). The technique used here enables
        // SIGTERMs to be handled gracefully.
        // See
        // https://spring.io/blog/2021/01/27/spring-batch-on-kubernetes-efficient-batch-processing-at-scale,
        // in particular, `Tips and Tricks #4, Graceful/Abrupt Shutdown Implication.`
        System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
    }
}