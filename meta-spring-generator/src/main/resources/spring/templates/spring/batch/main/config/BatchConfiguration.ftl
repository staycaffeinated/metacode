<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor;
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    /**
     * Next steps:
     * 1. Create a @Bean for an ItemReader that consumes the input files
     * 2. Create a @Bean for an ItemWriter that writes the output
     * 3. Optional: create a @Bean for any ItemProcessors needed
     * 4. Create a @Bean that returns each Step of the batch Job; for example:
     *    @Bean
     *    @StepScope
     *    public Step step1() {
     *        return stepBuilderFactory.get("step1")
     *                     .<WidgetIn,WidgetOut>chunk(10)  // chunk size = size of commit block
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
     * References:
     *  https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html
     *  https://docs.spring.io/spring-batch/docs/current/reference/html/index.html
     */
}