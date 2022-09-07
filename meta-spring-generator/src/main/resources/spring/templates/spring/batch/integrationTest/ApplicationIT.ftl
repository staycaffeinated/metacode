<#include "/common/Copyright.ftl">
package ${project.basePackage};

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBatchTest
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
        // The context won't load until the batch/job configuration is defined:
        // the Job has to be created, which requires Steps to be defined, which
        // requires ItemReaders, ItemWriters and, possibly, ItemProcessors to be
        // defined. The latter can't be defined until its known what those `Items`
        // are, which are unknown when this basic batch project is generated.
    }
}
