/*
 * Copyright 2022 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.cli.validation;

/**
 * Validation of resourceName to ensure its not a reserved Java keyword
 * <p>
 * A list of the reserved keywords:
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html">Reserved Words in Java</a>
 * </p>
 */
public class ResourceNameValidator implements ValidationTrait {

    private final String value;

    // Creating a private constructor to ensure instances of this are not created
    private ResourceNameValidator(String value) {
        this.value = value;
    }

    public static ResourceNameValidator of (String value) {
        return new ResourceNameValidator(value);
    }

    @Override
    public boolean isValid() {
        return evaluate(this.value);
    }

    @Override
    public boolean isInvalid() {
        return !evaluate(this.value);
    }

    @Override
    public String errorMessage() {
        if (isValid())
            return "";
        else {
            return String.format("%nERROR: %n\tThe resource name '%s' cannot be used. " +
                    "Resource names that lead to compile-time errors or " +
                    "obscure runtime errors are not supported.\n" +
                    "\tSuggestion: try something like '%sInfo' or '%sDetail', for example.", value, value, value);
        }
    }

    /**
     * Checks whether {@code value} represents a valid Java class name
     * @param value the String to check
     * @return true if {@code value} is a valid Java class name
     */
    private  boolean evaluate(String value) {
        // Null and blank names are not allowed as class names
        if (value == null || value.isBlank()) return false;
        return check(value);
    }

    /**
     * Checks whether the given candidate is a valid Java package name.
     * We don't guarantee that pathological cases will be detected.
     *
     * @param candidate  the candidate value
     * @return if it can be used as a package name
     */
    private boolean check (String candidate) {
        if (ReservedWords.isReservedWord(candidate)) return false;
        if (DisallowedWords.isDisallowedWord(candidate)) return false;
        return isLegalClassName(candidate);
    }

    /**
     * Checks whether {@code token} is a valid Java identifier.
     * The assumption is {@code token} will be used as a classname.
     * @param token the value to check
     * @return true if the token is suitable as a classname
     */
    private boolean isLegalClassName(String token) {
        // Does the token does not start with a valid starting character
        if (!Character.isJavaIdentifierStart(token.charAt(0))) return false;

        // Are subsequent characters legal parts of an identifier
        for (var i = 1; i < token.length(); i++) {
            if (!Character.isJavaIdentifierPart(token.charAt(i))) return false;
        }
        return true;
    }
}
