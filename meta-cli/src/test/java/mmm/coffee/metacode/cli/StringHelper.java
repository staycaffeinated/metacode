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
package mmm.coffee.metacode.cli;

/**
 * Helper methods for string manipulation
 */
public class StringHelper {
    /**
     * Converts a String into String[] array.
     */
    public static String[] toArgV(String s) { return s.split("\\s"); }

    /**
     * Returns {@code value} is value is not null.
     * When {@code value} is null, returns an empty string
     *
     * @param value the value to convert
     * @return either {@code value} or an empty string
     */
    public static String nullSafeValue(String value) {
        if (value == null) return "";
        return value;
    }

    /**
     * Builds a base URL. When {@code suggestedPath} is null, {@code '/'} is
     * returned as the base path. Also, if {@code suggestedPath} does not
     * begin with {@code '/'} (the forward slash), the forward slash is prepended.
     *
     * @param suggestedPath some REST path candidate
     * @return a base PATH, with a forward slash added as needed
     */
    public static String safePath(String suggestedPath) {
        if (suggestedPath == null) return "/";
        suggestedPath = suggestedPath.trim();
        if (!suggestedPath.startsWith("/"))
            suggestedPath = "/" + suggestedPath;
        return suggestedPath;
    }
}
