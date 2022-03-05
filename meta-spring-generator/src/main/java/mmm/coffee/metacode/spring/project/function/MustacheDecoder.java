/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.function;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mmm.coffee.metacode.common.mustache.MustacheExpressionResolver;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import mmm.coffee.metacode.spring.project.converter.RestTemplateModelToMapConverter;

import java.util.Map;

/**
 * This function decodes any mustache expression used in
 * the {@code CatalogEntry.destination} field. Typically,
 * this means translating "{{basePackagePath}}" to a file-system
 * style path such as, say, "org/acme/petstore".
 */
@Builder
public class MustacheDecoder {
    /*
     * This map does not need to be visible to anything.
     * This map is used to cache results
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Map<String,String> map;

    private RestTemplateModelToMapConverter converter;

    /**
     * Initialize a map to be used by the Mustache parser to resolve values
     * @param model the source data that's read to create the map
     */
    public void configure(RestProjectTemplateModel model) {
        map = converter.convert(model);
    }
    
    public String decode(String incoming) {
        return MustacheExpressionResolver.resolve(incoming, map);
    }
}
