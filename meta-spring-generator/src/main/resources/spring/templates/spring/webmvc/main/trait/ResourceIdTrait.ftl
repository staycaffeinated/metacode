<#include "/common/Copyright.ftl">
package ${project.basePackage}.trait;

/**
 * A trait for objects that are resourceId-aware
 */
public interface ResourceIdTrait<T> {
    T getResourceId();
}