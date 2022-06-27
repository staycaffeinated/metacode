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
 * Checks whether a proposed class name is disallowed.
 * A class name can be disallowed if source code for the
 * class name can be produced, but compile errors come up.
 * Case in point, we disallow naming a resource 'Test' because
 * the generator produces a class Test.java, which turns out
 * to conflict with JUnit's Test class, causing compile errors
 * because of the naming conflict.
 */
public class DisallowedWords {

    private static final String[] DISALLOWED;

    /* a private constructor to prevent instantiation */
    private DisallowedWords() {}

    /**
     * Check whether the given {@code word} is a disallowed word.
     * This check also excludes the String "null",
     * @param word the candidate value to test
     * @return if {@code word} is a reserved word, or is the String "null".
     */
    public static boolean isDisallowedWord(String word) {
        for (String s: DISALLOWED) {
            // Future task: log an error to the console, maybe with a specific reason.
            if (s.equalsIgnoreCase(word)) return true;
        }
       return false;
    }

    /*
     * These are resource names that have been found to cause errors
     * in the generated code. The errors may be compile-time errors
     * or runtime errors. The general idea is, if the end-user wants
     * to create a resource or entity with a name that'll prevent the
     * generated code from working out-of-the-box, let's warn the end-user
     * about those rather than generate broken code.
     */
    static {
        // When the database table name is 'User' (or equivalent),
        // Hibernate generates invalid SQL, in the form 'Invalid syntax: identifier expected: [*].User'.
        // I don't know why a table named 'User' is a problem.
        // The work-around: use something like `UserInfo` or `UserDetail`
        //
        // Using `Test` causes collisions with the JUnit `Test` class.
        //
        DISALLOWED = new String[] { "test", "user" };
    }
}
