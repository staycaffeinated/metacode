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
 *
 * A list of the reserved keywords:
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
 *
 */
public class ResourceNameValidator {

    // Creating a private constructor to ensure instances of this are not created
    private ResourceNameValidator() {}

    /**
     * Checks whether {@code value} represents a valid Java class name
     * @param value the String to check
     * @return true if {@code value} is a valid Java class name
     */
    public static boolean isValid(String value) {
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
    private static boolean check (String candidate) {
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
    private static boolean isLegalClassName(String token) {
        // Does the token does not start with a valid starting character
        if (!Character.isJavaIdentifierStart(token.charAt(0))) return false;

        // Are subsequent characters legal parts of an identifier
        for (var i = 1; i < token.length(); i++) {
            if (!Character.isJavaIdentifierPart(token.charAt(i))) return false;
        }

        return true;
    }
}
