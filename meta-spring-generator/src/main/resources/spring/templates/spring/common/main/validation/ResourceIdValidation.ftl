
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
* Verifies a value only contains alphabetic characters
*/
public class ResourceIdValidation implements ConstraintValidator<ResourceId, String> {
   /**
    * Determines whether {@code value} is a well-formed resource identifier.
    * A well-formed resource identifier is essentially a very large, positive integer;
    * the value
    * <ul>
    *   <li>Cannot be null</li>
    *   <li>Must be either 48 to 49 characters long</li>
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
               value.length() >= 48 &&
               value.length() <= 49 &&
               value.chars().allMatch(Character::isDigit);
    }
}
