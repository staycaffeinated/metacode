<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// See https://www.baeldung.com/spring-mvc-custom-validator

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AlphabeticValidation.class)
@Documented
public @interface Alphabetic {
    String message() default "{Alphabetic.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
