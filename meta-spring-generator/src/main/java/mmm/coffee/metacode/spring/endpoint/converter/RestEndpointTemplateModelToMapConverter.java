/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.MustacheConstants;
import mmm.coffee.metacode.spring.endpoint.context.RestEndpointTemplateModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates a Map of variables to enable resolution of mustache expressions.  
 */
public class RestEndpointTemplateModelToMapConverter implements ConvertTrait<RestEndpointTemplateModel, Map<String,String>> {

    public Map<String,String> convert (RestEndpointTemplateModel model) {
        var map = new HashMap<String,String>();

        map.put(MustacheConstants.BASE_PACKAGE, model.getBasePackage());
        map.put(MustacheConstants.BASE_PACKAGE_PATH, model.getBasePackagePath());
        map.put(MustacheConstants.BASE_PATH, model.getBasePath());
        map.put(MustacheConstants.ENTITY_NAME, model.getEntityName());
        map.put(MustacheConstants.ENTITY_VAR_NAME, model.getEntityVarName());
        map.put(MustacheConstants.ENTITY_NAME_LOWERCASE, model.getLowerCaseEntityName());

        return map;
    }
}
