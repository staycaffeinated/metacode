/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.MustacheConstants;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;

import java.util.HashMap;
import java.util.Map;

/**
 * ModelToMapConverter is used to convert the template model into
 * a Map suitable for Mustache expression resolution.
 *
 * The {@code MustacheExpressionResolver} takes a Map[String,String],
 * where the key corresponds to a mustache expression variable
 * and the value corresponds to the final value.  For example, given
 * the mustache expression "{{basePackageName}}", the key is "basePackageName"
 * and the value is, say, "org.acme.petstore".
 */
public class RestTemplateModelToMapConverter implements ConvertTrait<RestProjectTemplateModel, Map<String,String>> {


    public Map<String,String> convert (RestProjectTemplateModel model) {
        var map = new HashMap<String,String>();

        map.put(MustacheConstants.BASE_PACKAGE, model.getBasePackage());
        map.put(MustacheConstants.BASE_PACKAGE_PATH, model.getBasePackage());
        map.put(MustacheConstants.BASE_PATH, model.getBasePath());
        return map;
    }
}
