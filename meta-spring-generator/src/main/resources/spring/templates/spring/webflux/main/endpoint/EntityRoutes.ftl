<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

/**
 * Routes to ${endpoint.entityName} resources
 */
@SuppressWarnings({"java:S1075"})
public final class ${endpoint.entityName}Routes {

    private ${endpoint.entityName}Routes() {}

    public static final String ${endpoint.routeConstants.basePath} = "${endpoint.route}";
    public static final String ${endpoint.routeConstants.idParameter} = "/{id}";

    public static final String ${endpoint.routeConstants.findOne} = ${endpoint.routeConstants.basePath} + ${endpoint.routeConstants.idParameter};
    public static final String ${endpoint.routeConstants.findAll} = ${endpoint.routeConstants.basePath} + "/findAll";
    public static final String ${endpoint.routeConstants.stream} = ${endpoint.routeConstants.basePath} + "/stream";

    public static final String ${endpoint.routeConstants.create} = ${endpoint.routeConstants.basePath};
    public static final String ${endpoint.routeConstants.update} = ${endpoint.routeConstants.basePath} + ${endpoint.routeConstants.idParameter};
    public static final String ${endpoint.routeConstants.delete} = ${endpoint.routeConstants.basePath} + ${endpoint.routeConstants.idParameter};
    public static final String ${endpoint.routeConstants.search} = ${endpoint.routeConstants.basePath} + "/search";

    // Get websocket events
    public static final String ${endpoint.routeConstants.events} = ${endpoint.routeConstants.basePath} + "/ws/events";
}