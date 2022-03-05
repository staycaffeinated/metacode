/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.CatalogEntryPredicates;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;

import java.util.ArrayList;
import java.util.List;

/**
 * RestEndpointDescriptorToPredicateConverter
 */
public class RestEndpointDescriptorToPredicateConverter implements ConvertTrait<RestEndpointDescriptor, Predicate<CatalogEntry>> {
    /**
     * Converts an instance of class {@code FROM} into an instance of class {@code TO}.
     * We let the implementer decide how to handle {@code nulls}
     *
     * @param fromType some instance to convert
     * @return the transformed object
     */
    @Override
    public Predicate<CatalogEntry> convert(RestEndpointDescriptor fromType) {
        List<Predicate<CatalogEntry>> predicateList = collectPredicates(fromType);
        return Predicates.or(predicateList);
    }

    private List<Predicate<CatalogEntry>> collectPredicates(RestEndpointDescriptor descriptor) {
        List<Predicate<CatalogEntry>> resultSet = new ArrayList<>();
        resultSet.add ( CatalogEntryPredicates.isEndpointArtifact() );
        return resultSet;
    }
}
