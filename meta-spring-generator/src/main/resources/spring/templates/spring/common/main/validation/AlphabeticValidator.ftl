<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Verifies a value only contains alphabetic characters
 */
public class AlphabeticValidator implements ConstraintValidator<Alphabetic, String> {
    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.chars().allMatch(Character::isAlphabetic);
    }
}
