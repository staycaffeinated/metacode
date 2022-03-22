<#-- @ftlroot "../../../.." -->
<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import org.springframework.stereotype.Service;

/**
 * Empty implementation of a Service
 */
@Service
@SuppressWarnings({"java:S3400"})
// S3400: we'll allow a method to return a constant
public class RootService {

    int doNothing() { return 0; }
}
