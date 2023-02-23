<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
public class MongoDbContainerSupport {

    private static final String IMAGE = "mongo:6.0.4";

    // @formatter:off
    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(IMAGE)
        .withReuse(true)
        .withStartupTimeout(Duration.ofMinutes(2))
        .waitingFor(Wait.forListeningPort());
        // @formatter:on

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        log.info("MongoDBContainer has started");
        log.info("Registering database properties...");
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateContainer() {
        assertThat(mongoDBContainer).isNotNull();
        assertThat(mongoDBContainer.isCreated()).isTrue();
    }

    @BeforeEach
    void startContainer() {
        mongoDBContainer.start();
    }
    void stopContainer() {
        mongoDBContainer.stop();
    }
}
