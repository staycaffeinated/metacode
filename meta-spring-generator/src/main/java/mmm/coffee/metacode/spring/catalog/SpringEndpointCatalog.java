/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.stereotype.Collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringEndpointCatalog
 */
@Builder
@AllArgsConstructor
public class SpringEndpointCatalog implements Collector {

    private static final String[] ENDPOINT_CATALOGS = {
            SpringWebFluxTemplateCatalog.WEBFUX_CATALOG,
            SpringWebMvcTemplateCatalog.WEBMVC_CATALOG
    };

    final ICatalogReader reader;

    /**
     * This collects both SpringWebMvc and SpringWebFlux
     * endpoint templates, so a Predicate is needed to
     * filter the templates applicable to the framework being used.
     *
     * @return a collection containing "endpoint" templates
     * (and others, so be mindful to apply a Predicate to filter
     * for the desired templates).
     */
    @Override
    public List<CatalogEntry> collect() {
        List<CatalogEntry> resultSet = new ArrayList<>();
        for (String catalog: ENDPOINT_CATALOGS) {
            try {
                resultSet.addAll(reader.readCatalogFile(catalog));
            }
            catch (IOException e) {
                throw new RuntimeApplicationError(String.format("Error reading endpoint catalog file: %s", e.getMessage()), e);
            }
        }
        return resultSet;
    }
}
