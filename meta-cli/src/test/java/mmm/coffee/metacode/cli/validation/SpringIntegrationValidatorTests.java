/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.cli.validation;

import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests for SpringIntegrationValidator.
 * <p>
 * When creating a project, the end-user can indicate additional dependencies
 * with the {@code --add} option, such as:
 * <code>
 *     create project spring-webmvc -n petstore -p acme.petstore --add postgres testcontainers
 * </code>
 * Some of the additional dependencies are mutually exclusive; the code generator does not
 * support generating code for both dependencies. Case in point is PostgreSQL and MongoDB;
 * the code generator can either create Entities for PostgreSQL, or Documents for MongoDB,
 * but not both. There's also the challenge of having multiple DataSource's and knowing when
 * to use which DataSource.  
 * </p>
 * </p>
 */
class SpringIntegrationValidatorTests {

    /**
     * The equivalent of, from the command line, --add postgres
     */
    private final SpringIntegrations[] onlyPostgres = {
            SpringIntegrations.POSTGRES
    };

    /**
     * The equivalent of, from the command line, --add mongodb
     */
    private final SpringIntegrations[] onlyMongoDb = {
            SpringIntegrations.MONGODB
    };

    /**
     * The equivalent of, from the command line, --add postgres mongodb
     */
    private final SpringIntegrations[] bothPostgresAndMongoDb = {
        SpringIntegrations.POSTGRES,
        SpringIntegrations.MONGODB
    };

    @Test
    void shouldAllowPostgresWithoutMongoDb() {
        assertThat(SpringIntegrationValidator.of(onlyPostgres).isValid()).isTrue();
    }

    @Test
    void shouldAllowMongoDbWithoutPostgres() {
        assertThat(SpringIntegrationValidator.of(onlyMongoDb).isValid()).isTrue();
    }

    @Test
    void shouldFlagPostgresAndMongoDbTogetherAsInvalid() {
        // Support for PostgreSQL and MongoDB at same time is not supported
        assertThat(SpringIntegrationValidator.of(bothPostgresAndMongoDb).isValid()).isFalse();
        // When an invalid combination is attempted, there should be an error message
        assertThat(SpringIntegrationValidator.of(bothPostgresAndMongoDb).errorMessage()).isNotEmpty();
    }

    @Nested
    class ErrorMessageTests {
        @Test
        void shouldReturnNonEmptyErrorWhenInvalidOptions() {
            var validator = SpringIntegrationValidator.of(bothPostgresAndMongoDb);
            assertThat(validator.isValid()).isFalse();
            assertThat(validator.errorMessage()).isNotEmpty();

        }

        @Test
        void shouldReturnEmptyErrorWhenValidOptions() {
            var validator = SpringIntegrationValidator.of(onlyPostgres);
            assertThat(validator.isValid()).isTrue();
            assertThat(validator.errorMessage()).isEmpty();
        }
    }
}
