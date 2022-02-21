<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

class ${endpoint.pojoName}Generator {
    static ${endpoint.pojoName} generate${endpoint.entityName}() {
        return ${endpoint.pojoName}.builder().text("sample text").build();
    }
}