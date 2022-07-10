<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * Creates a ConversionService instance suitable for testing
 */
public class FakeConversionService {

    static ConversionService build() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ${endpoint.entityName}EntityToPojoConverter());
        service.addConverter(new ${endpoint.entityName}PojoToEntityConverter());
        return service;
    }
}
