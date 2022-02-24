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
 * Checks whether a word is a reserved Java keyword
 */
public class ReservedWords {

    private static final String[] reserved;

    /* a private constructor to prevent instantiation */
    private ReservedWords() {}

    /**
     * Check whether the given {@code word} is a reserved word.
     * This check also excludes the String "null",
     * @param word the candidate value to test
     * @return if {@code word} is a reserved word, or is the String "null".
     */
    public static boolean isReservedWord(String word) {
        return Arrays.binarySearch(reserved, word) >= 0;
    }

    /*
     * The reserved words, plus 'null'
     */
    static {
        reserved = new String[] { "abstract", "assert", "boolean", "break", "byte",
                "case", "catch", "char", "class", "const", "continue", "default", "do",
                "double", "else", "enum", "extends", "false", "final", "finally",
                "float", "for", "goto", "if", "implements", "import", "instanceof",
                "int", "interface", "long", "native", "new", "null", "package",
                "private", "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this", "throw",
                "throws", "transient", "true", "try", "void", "volatile", "while" };
    }
}
