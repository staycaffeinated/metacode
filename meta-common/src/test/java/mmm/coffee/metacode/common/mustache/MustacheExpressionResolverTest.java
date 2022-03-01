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
package mmm.coffee.metacode.common.mustache;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests
 */
class MustacheExpressionResolverTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();


    final Map<String,String> emptyMap = new HashMap<>();
    Map<String,String> valueMap;

    final String basePackage = "mmm.coffee.widget";
    final String basePackagePath = "mmm/coffee/widget";
    final String entityName = "Widget";
    final String entityVarName = "widget";
    final String lowerCaseEntityName = entityName.toLowerCase();
    final String basePath = "/widget";

    @BeforeEach
    public void setUpEachTime() {
        valueMap = new HashMap<>();
        valueMap.put("basePackage", basePackage);
        valueMap.put("basePackagePath", basePackagePath);
        valueMap.put("entityName", entityName);
        valueMap.put("entityVarName", entityVarName);
        valueMap.put("lowerCaseEntityName", lowerCaseEntityName);
        valueMap.put("basePath", basePath);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenExpressionIsNull() {
        assertThrows (NullPointerException.class, () ->  MustacheExpressionResolver.resolve(null, emptyMap));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenMapIsNull() {
        assertThrows (NullPointerException.class, () -> MustacheExpressionResolver.resolve("{{something}}", null));
    }

    @ParameterizedTest
    @CsvSource({
            "{{basePackagePath}}/route/{{lowerCaseEntityName}},                 mmm/coffee/widget/route/widget",
            "src/main/java/{{basePackagePath}}/{{entityName}}Controller.java,   src/main/java/mmm/coffee/widget/WidgetController.java"
    })
    void shouldConvertExpressionToString(String expression, String expectedResult) {
        String actual = MustacheExpressionResolver.resolve(expression, valueMap);
        assertThat(actual).isEqualTo(expectedResult);
    }

}
