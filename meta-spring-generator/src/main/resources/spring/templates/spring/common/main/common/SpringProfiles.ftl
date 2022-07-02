<#include "/common/Copyright.ftl">
package ${project.basePackage}.common;

/**
 * Spring profiles
 */
public final class SpringProfiles {
    private SpringProfiles(){}

    public static final String PRODUCTION = "prod";
    public static final String TEST = "test";
    public static final String INTEGRATION_TEST = "integration-test";
}