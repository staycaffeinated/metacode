<#include "/common/Copyright.ftl">
package ${project.basePackage}.common;

/**
 * Spring profiles
 */
public final class Constants {
    private Constants(){}

    public static final String PROFILE_PROD = "prod";
    public static final String PROFILE_NOT_PROD = "!"+PROFILE_PROD;
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_IT = "integration-test";
}