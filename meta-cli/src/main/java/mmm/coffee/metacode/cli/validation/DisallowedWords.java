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

import java.util.Arrays;

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

    private static final String[] reserved;

    /* a private constructor to prevent instantiation */
    private DisallowedWords() {}

    /**
     * Check whether the given {@code word} is a reserved word.
     * This check also excludes the String "null",
     * @param word the candidate value to test
     * @return if {@code word} is a reserved word, or is the String "null".
     */
    public static boolean isDisallowedWord(String word) {
        for (String s: reserved) {
            if (s.equalsIgnoreCase(word)) return true;
        }
        return false;
    }

    /*
     * The disallowed words
     */
    static {
        reserved = new String[] { "test" };
    }
}
