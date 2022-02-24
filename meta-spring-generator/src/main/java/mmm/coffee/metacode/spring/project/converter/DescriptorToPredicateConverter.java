/*
 * Copyright 2022 Jon Caulfield
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
package mmm.coffee.metacode.spring.project.converter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.CatalogEntryPredicates;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts a REST ProjectDescriptor into an uber-predicate.
 * The uber-predicate can be applied to a stream of CatalogEntry's to
 * select the templates to be rendered. 
 */
public class DescriptorToPredicateConverter implements ConvertTrait<RestProjectDescriptor, Predicate<CatalogEntry>> {
    /**
     * Converts an instance of class {@code FROM} into an instance of class {@code TO}.
     * We let the implementer decide how to handle {@code nulls}
     *
     * @param fromType some instance to convert
     * @return the transformed object
     */
    @Override
    public Predicate<CatalogEntry> convert(RestProjectDescriptor fromType) {
        List<Predicate<CatalogEntry>> predicateList = collectPredicates(fromType);
        return Predicates.or(predicateList);
    }

    private List<Predicate<CatalogEntry>> collectPredicates(RestProjectDescriptor descriptor) {
        List<Predicate<CatalogEntry>> resultSet = new ArrayList<>();
        resultSet.add ( CatalogEntryPredicates.isCommonProjectArtifact() );

        if (descriptor.getIntegrations().contains(WebMvcIntegration.POSTGRES.name()))
            resultSet.add( CatalogEntryPredicates.hasPostgresTag() );

        if (descriptor.getIntegrations().contains(WebMvcIntegration.TESTCONTAINERS.name()))
            resultSet.add( CatalogEntryPredicates.hasTestContainerTag() );

        if (descriptor.getIntegrations().contains(WebMvcIntegration.LIQUIBASE.name()))
           resultSet.add(CatalogEntryPredicates.hasLiquibaseTag());


        return resultSet;
    }

}
