<#include "/common/Copyright.ftl">
package ${project.basePackage}.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A formalized wrapper for a resource identifier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceIdentity {
    @NonNull
    private String resourceId;
}