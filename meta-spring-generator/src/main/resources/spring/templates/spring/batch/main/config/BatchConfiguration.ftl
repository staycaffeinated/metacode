<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.stereotype.Component;

/**
 * References:
 *  https://github.com/spring-projects/spring-batch/tree/main/spring-batch-samples
 *  https://docs.spring.io/spring-batch/docs/current/reference/html/index.html
 *  https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html
 */
@Component
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    /**
     * Next steps:
     * 1. Create a @Bean for an ItemReader that consumes the input source
     * 2. Create a @Bean for an ItemWriter that writes the output
     * 3. Optional: create a @Bean for any ItemProcessors needed
     * 4. Create a @Bean that returns each Step of the batch Job; for example:
     *    @Bean
     *    @StepScope
     *    public Step step1() {
     *        return stepBuilderFactory.get("step1")
     *                     .<WidgetIn,WidgetOut>chunk(10)
     *                     .reader(yourItemReaderBean())
     *                     .writer(yourItemWriterBean())
     *                     .processor(yourItemProcessorBean())
     *                     .build();
     *    }
     *
     * 5. Create a @Bean that returns the Job; for example:
     *    @Bean
     *    public Job job() {
     *        return jobBuilderFactory.get("job")
     *                     .incrementer(new RunIdIncrementer())
     *                     .flow(step1()).end();
     *    }
     *
     */
}