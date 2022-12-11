<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ${project.basePackage}.math.SecureRandomSeries;

/**
 * This constraint verifies a String value is
 * consistent with the format of a resource identifier.
 * This constraint can be applied in POJOs or URL path variables
 * to help verify a field or parameter "looks like" a resource ID. 
 */
@SuppressWarnings({"java:S125"})
// S125: We don't care if a comment happens to look like code
public class ResourceIdValidator implements ConstraintValidator<ResourceId, String> {
   /**
    * Determines whether {@code value} is a well-formed resource identifier.
    * A well-formed resource identifier is essentially a very large, positive integer;
    * the value
    * <ul>
    *   <li>Cannot be null</li>
    *   <li>Must be either 48 to 49 digits long</li>
    *   <li>Must consist only of digits</li>
    * </ul>
    * <p>
    * This method can be accessed concurrently
    *
    * @param value
    *            object to validate
    * @param context
    *            context in which the constraint is evaluated
    * @return {@code false} if {@code value} does not pass the constraint
    */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValid(value);
    }

    public boolean isValid(String value) {
        // By default, this method assumes the {@code SecureRandomSeries::nextResourceId()}
        // is used.  If the {@code SecureRandomSeries::nextString()} is used, these checks
        // have to be adjusted.
        return value != null &&
               value.length() == SecureRandomSeries.ENTROPY_STRING_LENGTH &&
               value.chars().allMatch(Character::isLetterOrDigit);
    }
}
