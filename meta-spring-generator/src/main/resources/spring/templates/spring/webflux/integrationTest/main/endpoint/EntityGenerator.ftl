<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;

class ${endpoint.pojoName}Generator {
    static ${endpoint.pojoName} generate${endpoint.entityName}() {
        return ${endpoint.pojoName}.builder().text("sample text").build();
    }
}