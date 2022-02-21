<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import ${endpoint.basePackage}.stereotype.*;
import ${endpoint.basePackage}.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * This is the POJO of ${endpoint.entityName} data exposed to client applications
 */
@lombok.Data
// The next 2 lines allow jackson-databind and lombok to play nice together.
// These 2 lines specifically resolve this exception:
//    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance...
// See https://www.thecuriousdev.org/lombok-builder-with-jackson/
@JsonDeserialize(builder = ${endpoint.pojoName}.DefaultBuilder.class)
@Builder(builderClassName = "DefaultBuilder", toBuilder = true)
public class ${endpoint.pojoName} implements RestfulResource<Long> {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long resourceId;

    /*
     * An @Pattern can also be used here.
     * The @Alphabet is used to demonstrate a custom validator
     */
    @NotEmpty @Alphabetic
    private String text;

    /**
     * Added to enable Lombok and jackson-databind to play nice together
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class DefaultBuilder {
        // empty
    }
}