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

import java.util.StringTokenizer;

/**
 * Validation of packageName to ensure its a legal Java package name
 *
 * A review of the rules for package names
 * (from  https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html )
 *
 * 1. Package names are written in all lower case
 * 2. Cannot contain a hyphen or other special character (underscore is allowed)
 * 3. Cannot contain a reserved word
 */
public class PackageNameValidator {

    // Creating a private constructor to ensure instances of this are not created
    private PackageNameValidator() {}

    /**
     * Checks whether {@code value} represents a valid Java package name
     * @param value the String to check
     * @return true if {@code value} is a valid Java package name
     */
    public static boolean isValid(String value) {
        if (value == null || value.isBlank()) return false;
        return check(value);
    }

    /**
     * For times when this is easier to read
     */
    public static boolean isNotValid(String value) {
        return !isValid(value);
    }

    /**
     * Checks whether the given candidate is a valid Java package name.
     * We don't guarantee that pathological cases will be detected.
     *
     * @param candidate  the candidate value
     * @return if it can be used as a package name
     */
    private static boolean check (String candidate) {
        var tokenizer = new StringTokenizer(candidate, ".");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (ReservedWords.isReservedWord(token))
                return false;
            if (!isLegalIdentifier((token))) return false;
        }
        return true;
    }

    /**
     * Not exactly the rules of a Java identifier, but sufficient for package names
     * @param token the token to check
     * @return true if the token is a legal part of a package name
     */
    private static boolean isLegalIdentifier(String token) {
        // first character must be a-z or underscore
        if (! (isLowerCaseLetter(token.charAt(0)) || token.charAt(0) == '_') ) {
            return false;
        }

        // a valid subsequent letters can be: a-z, A-Z, 0-9, or underscore
        for (var i = 1; i < token.length(); i++) {
            if (!isLowerCaseLetter(token.charAt(i)) &&
                    !isUpperCaseLetter(token.charAt(i)) &&
                    !isDigit(token.charAt(i)) &&
                    token.charAt(i) != '_')
                return false;
        }
        return true;
    }

    protected static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    protected static boolean isLowerCaseLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }
    protected static boolean isUpperCaseLetter(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }
}
