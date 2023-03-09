<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.CustomRepository;

public interface ${endpoint.entityName}Repository extends CustomRepository<${endpoint.ejbName}, Long> {
}

