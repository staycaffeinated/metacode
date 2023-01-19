<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * Creates a fake ConversionService suitable for testing
 */
public class FakeConversionService {

    static ConversionService build()   {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ${endpoint.entityName}EntityToPojoConverter());
        service.addConverter(new ${endpoint.entityName}PojoToEntityConverter());
        return service;
    }
}
