<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

/**
 * Routes to ${endpoint.entityName} resources
 */
public class ${endpoint.entityName}Routes {

    private ${endpoint.entityName}Routes() {}

    public static final String INDEX = "${endpoint.route}";
    public static final String CREATE_ONE = INDEX;
    public static final String EXACTLY_ONE = INDEX + "/{id}";
    public static final String GET_ALL = INDEX + "/findAll";
    public static final String SEARCH = INDEX + "/search";

}