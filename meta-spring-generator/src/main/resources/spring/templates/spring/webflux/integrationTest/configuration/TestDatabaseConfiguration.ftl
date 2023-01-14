<#include "/common/Copyright.ftl">

package ${project.basePackage}.configuration;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

/**
 * Set up a ConnectionFactoryInitializer, which gets used downstream
 * to initialize tables within the testcontainer database.
 */
@Configuration
public class TestDatabaseConfiguration {
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }
}
