<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * Initialize the ${endpoint.entityName} table within the testcontainer database.
 *
 * This class is named as-is to avoid a conflict with the ${endpoint.entityName}TableInitializer
 * class found under the 'src/main' source directory.
 */
@Component
public class ${endpoint.entityName}TestTableInitializer {

    public ${endpoint.entityName}TestTableInitializer(ConnectionFactoryInitializer initializer) {
        // This is where the default sql script is found
        final String sqlScript = "database/${endpoint.lowerCaseEntityName}-schema.sql";

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(sqlScript)));
        initializer.setDatabasePopulator(populator);
    }
}
