/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.cli.create.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import mmm.coffee.metacode.common.descriptor.Framework;

import static com.google.common.truth.Truth.assertThat;

/**
 * AbstractCreateSpringProjectTests
 */
public class AbstractCreateSpringProjectTests {

    AbstractCreateSpringProject commandUnderTest = new FakeCommand();

    final String givenBasePath = "/bookstore";
    final String givenPackage = "acme.bookstore";
    final String givenAppName = "bookstore";

    @BeforeEach
    public void setCommandUnderTest() {
        commandUnderTest.basePath = givenBasePath;
        commandUnderTest.applicationName = givenAppName;
        commandUnderTest.packageName = givenPackage;
    }

    /**
     * Tests of the buildProjectDescriptor method
     */
    @Nested
    class BuildProjectDescriptorTests {

        @Test
        void shouldToggleWebFluxFlag() {
            var descriptor = commandUnderTest.buildProjectDescriptor(Framework.SPRING_WEBFLUX);

            assertThat(descriptor.getFramework()).isEqualTo(Framework.SPRING_WEBFLUX.frameworkName());
            assertThat(descriptor.getApplicationName()).isEqualTo(givenAppName);
            assertThat(descriptor.getBasePath()).isEqualTo(givenBasePath);
            assertThat(descriptor.getBasePackage()).isEqualTo(givenPackage);
        }

        @Test
        void shouldToggleWebMvcFlag() {
            var descriptor = commandUnderTest.buildProjectDescriptor(Framework.SPRING_WEBMVC);

            assertThat(descriptor.getFramework()).isEqualTo(Framework.SPRING_WEBMVC.frameworkName());
            assertThat(descriptor.getApplicationName()).isEqualTo(givenAppName);
            assertThat(descriptor.getBasePath()).isEqualTo(givenBasePath);
            assertThat(descriptor.getBasePackage()).isEqualTo(givenPackage);
        }
    }

    // ----------------------------------------------------------------------
    //
    // Helper classes
    //
    // ----------------------------------------------------------------------

    // We need a concrete class
    public class FakeCommand extends AbstractCreateSpringProject {}

}
