<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import lombok.NonNull;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Pattern;

/**
* Verifies that search-by-text parameter only contains valid characters.
* The reg-ex used here is from:
*   https://owasp.org/www-community/OWASP_Validation_Regex_Repository
*/
public class SearchTextValidator implements ConstraintValidator<SearchText, Optional<String>> {

    // These value constraints are arbitrary, since we have to start somewhere.
    // These should be adjusted to something that makes sense to your use cases.
    private static final String REGEX = "^[a-zA-Z]+";
    private static final int MAXLENGTH = 24;
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Override
    public boolean isValid(@NonNull Optional<String> value, ConstraintValidatorContext context) {
        // when empty, then the content of the text is irrelevant to the search filter
        if (ObjectUtils.isEmpty(value.orElse("")))
            return true;
        // don't allow unlimited length; pick a limit to the length
        if (value.orElse("").length() > MAXLENGTH)
            return false;
        // check against the allowed characters
        return PATTERN.matcher(value.orElse("")).find();
    }
}
