<#include "/common/Copyright.ftl">
package ${project.basePackage}.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Provide support for MongoDB TestContainer
 */
@Slf4j
@Testcontainers
@SuppressWarnings("all")
public class MongoDbContainerTests {

    private static final String IMAGE = "mongo:6.0.4";

    // @formatter:off
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(IMAGE)
        .withReuse(true)
        .withStartupTimeout(Duration.ofMinutes(1))
        .waitingFor(Wait.forListeningPort());
    // @formatter:on

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdata"); // this property is not required
    }

    @Test
    void shouldCreateContainer() {
        assertThat(mongoDBContainer).isNotNull();
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }
}
