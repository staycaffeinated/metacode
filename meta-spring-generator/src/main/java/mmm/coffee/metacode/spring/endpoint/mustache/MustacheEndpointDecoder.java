/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.mustache;

import lombok.*;
import mmm.coffee.metacode.common.mustache.MustacheExpressionResolver;
import mmm.coffee.metacode.spring.endpoint.model.RestEndpointTemplateModel;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointTemplateModelToMapConverter;

import java.util.Map;

/**
 * MustacheEndpointDecoder
 */
@Builder
@AllArgsConstructor
public class MustacheEndpointDecoder {
    /*
     * This map does not need to be visible to anything.
     * This map is used to cache results
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Map<String,String> map;

    private RestEndpointTemplateModelToMapConverter converter;

    /**
     * Initialize a map to be used by the Mustache parser to resolve values
     * @param model the source data that's read to create the map
     */
    public void configure(RestEndpointTemplateModel model) {
        map = converter.convert(model);
    }

    public String decode(String incoming) {
        return MustacheExpressionResolver.resolve(incoming, map);
    }
}
