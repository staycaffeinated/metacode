<#include "/common/Copyright.ftl">

package ${endpoint.packageName};


import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * Configures of the converters, since WebFlux does not automatically do this.
 */
@Configuration
public class ${endpoint.entityName}Config {

	@Bean("${endpoint.entityVarName}Converter")
    ConversionService conversionService() {
		    var factory = new ConversionServiceFactoryBean();
		    Set<Converter<?, ?>> convSet = new HashSet<>();
		    convSet.add(new ${endpoint.entityName}EntityToPojoConverter());
		    convSet.add(new ${endpoint.entityName}PojoToEntityConverter());
		    factory.setConverters(convSet);
		    factory.afterPropertiesSet();
		    return factory.getObject();
	  }
}