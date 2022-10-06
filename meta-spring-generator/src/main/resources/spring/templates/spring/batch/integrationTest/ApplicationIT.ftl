<#include "/common/Copyright.ftl">
package ${project.basePackage};

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBatchTest
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.batch.job.enabled=false",
    "spring.batch.jdbc.initialize-schema=always",
    "spring.jpa.generate-ddl=true",
    "spring.datasource.url=jdbc:tc:postgresql:13.2-alpine:///public",
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
    "spring.jpa.hibernate.ddl-auto=create-drop"})
class ApplicationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
    public void tearDown() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Disabled
    void contextLoads() {
        // The context won't load until the batch job configuration is defined.
        // NEXT STEPS:
        // Define Beans for an ItemReader and ItemWriter
        // Define a Step that uses the ItemReader and ItemWriter
        // Define a Job that runs the Step
        //
        // Once the Beans that make up the batch job are defined, you can enable this test.
        // Errors usually indicate a misconfiguration issue.
        //
        // Also, be mindful that these 2 properties are set:
        // 1) spring.batch.job.enabled=false
        // 2) spring.batch.jdbc.initialize-schema=always
        // The spring.batch.job.enabled _must_ be false or you will get errors that
        // a JobBuilderFactory cannot be instantiated.
        // The spring.batch.jbc.initialize-schema is set to 'always' to enable
        // Spring Batch's tables to be created.
    }

    /**
     * This test won't work until the Job is implemented.
     * Jobs are commonly configured in the BatchConfiguration class.
     * This is only a hypothetical test meant only to illustrate techniques.
     */
    @Disabled
    public void whenJobExecutes_expectCompleted() throws Exception {
        var jobExecution = jobLauncherTestUtils.launchJob();
        var actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertThat(actualJobInstance.getJobName()).isEqualTo("job");
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");
    }

    /**
     * This test won't work until a Step is implemented.
     * Steps are commonly implemented in the BatchConfiguration class.
     * This is only a hypothetical test meant only to illustrate techniques.
     */
    @Disabled
    public void whenStepExecution_expectCompleted() throws Exception {
        var jobExecution = jobLauncherTestUtils.launchStep("step1");
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertThat(actualStepExecutions.size()).isEqualTo(1);
        assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");

        actualStepExecutions.forEach(stepExecution -> {
            // The count should equal the number of records in the test file
            assertThat(stepExecution.getWriteCount()).isEqualTo(1);
        });
    }

    /**
     * This test won't work until a Step is implemented.
     * Steps are commonly implemented in the BatchConfiguration class.
     * This test illustrates how to test a Bean that's in StepScope.
     */
    @Disabled
    public void givenMockedStep_whenWriterCalled_thenSuccess() throws Exception {
        // given
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();

        // ItemWriter writerUnderTest = new ItemWriter();

        // when
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            // writerUnderTest.open(stepExecution.getExecutionContext());
            // writerUnderTest.write(Arrays.asList(customer));
            // writerUnderTest.close();
            return null;
        });
    }
}
