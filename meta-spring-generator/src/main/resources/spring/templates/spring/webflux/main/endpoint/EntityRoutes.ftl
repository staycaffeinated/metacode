<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

/**
 * Routes to ${endpoint.entityName} resources
 */
@SuppressWarnings({"java:S1075"})
public class ${endpoint.entityName}Routes {

    private ${endpoint.entityName}Routes() {}

    public static final String BASE_PATH = "${endpoint.route}";
    public static final String ID_PARAM = "/{id}";

    public static final String GET_ONE = BASE_PATH + ID_PARAM;
    public static final String GET_ALL = BASE_PATH + "/findAll";
    public static final String GET_STREAM = BASE_PATH + "/stream";

    public static final String CREATE = BASE_PATH;
    public static final String UPDATE = BASE_PATH + ID_PARAM;
    public static final String DELETE = BASE_PATH + ID_PARAM;
    public static final String SEARCH = BASE_PATH + "/search";

    // Get websocket events
    public static final String GET_WS_EVENTS = BASE_PATH + "/ws/events";
}