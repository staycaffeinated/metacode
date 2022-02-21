<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@ExtendWith(MockitoExtension.class)
class RootServiceTests {

    @InjectMocks
	private RootService serviceUnderTest;

	@Test
	void verifyServiceIsLoaded() {
		int result = serviceUnderTest.doNothing();
		assertThat(result).isZero();
	}
}