<#-- @ftlroot "../../../.." -->
<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import org.springframework.stereotype.Service;

/**
 * Empty implementation of a Service
 */
// Because sonarqube complains about doNothing returning a constant value
@SuppressWarnings("java:S3400")
@Service
public class RootService {

    int doNothing() { return 0; }
}
