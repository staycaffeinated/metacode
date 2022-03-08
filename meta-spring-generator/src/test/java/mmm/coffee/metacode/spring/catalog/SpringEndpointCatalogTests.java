/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.catalog;

import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * SpringEndpointCatalogTests
 */
class SpringEndpointCatalogTests {

    SpringEndpointCatalog catalogUnderTest;

    @BeforeEach
    public void setUp() {
        catalogUnderTest = SpringEndpointCatalog.builder()
                .reader(new CatalogFileReader())
                .build();
    }

    @Test
    void shouldReadTemplates() {
        var resultSet = catalogUnderTest.collect();
        assertThat(resultSet).isNotNull();
        assertThat(resultSet.size()).isGreaterThan(0);
    }

    @Test
    void shouldWorkWithAllArgsConstructor() {
        var catalog = new SpringEndpointCatalog(new CatalogFileReader());
        assertThat(catalog.reader).isNotNull();
        assertThat(catalog.collect().size()).isGreaterThan(0);
    }

    @Test
    void shouldWrapAnyExceptionAsRuntimeApplicationError() throws Exception {
        // given: a CatalogFileReader that eagerly throws IOExceptions
        var mockReader = Mockito.mock(CatalogFileReader.class);
        when(mockReader.readCatalogFile(anyString())).thenThrow(IOException.class);

        // given: a catalog that uses this iffy reader...
        var catalog = new SpringEndpointCatalog(mockReader);

        // When an IOException occurs when collecting templates,
        // then expect a RuntimeApplicationError is thrown instead
        assertThrows(RuntimeApplicationError.class, () -> catalog.collect());
    }
}
