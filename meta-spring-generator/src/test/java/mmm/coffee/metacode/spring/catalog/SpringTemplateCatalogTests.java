/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.catalog;

import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * SpringTemplateCatalogTests
 */
class SpringTemplateCatalogTests {

    @Test
    void shouldThrowException() throws Exception {
        var mockReader = Mockito.mock(ICatalogReader.class);
        when(mockReader.readCatalogFile(anyString())).thenThrow(IOException.class);

        SimpleTemplateCatalog catalog = new SimpleTemplateCatalog(mockReader);
        assertThrows(RuntimeApplicationError.class, () -> catalog.collectGeneralCatalogsAndThisOne("") );

    }



    // -----------------------------------------------------------
    // Helper methods and classes
    // -----------------------------------------------------------
    static class SimpleTemplateCatalog extends SpringTemplateCatalog {

        SimpleTemplateCatalog(ICatalogReader reader) {
            super(reader);
        }
        
        @Override
        public List<CatalogEntry> collect() {
            return Collections.emptyList();
        }
    }
}
