<#include "/common/Copyright.ftl">
package ${project.basePackage}.stereotype;

/**
 * A stereotype for RESTful resources
 */
public interface RestfulResource<T> {
    T getResourceId();
}
