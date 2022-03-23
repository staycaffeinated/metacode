<#include "/common/Copyright.ftl">

package ${project.basePackage}.endpoint.root;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the RootService
 */
class RootServiceTest {
    RootService serviceUnderTest = new RootService();

    /**
     * Test the single method of the RootService
     */
    @Test
    void testRootService() {
        assertThat(serviceUnderTest.doNothing()).isZero();
    }
}