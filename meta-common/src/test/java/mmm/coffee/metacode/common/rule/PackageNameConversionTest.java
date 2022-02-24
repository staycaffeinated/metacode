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
package mmm.coffee.metacode.common.rule;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Unit tests
 */
class PackageNameConversionTest {
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();


    @Test
    void shouldThrowNullPointerExceptionWhenArgIsNull() {
        assertThrows (NullPointerException.class, ()-> PackageNameConversion.toPath(null));
    }

    @Test
    void shouldReturnEmptyStringWhenArgIsEmpty() {
        String actual = PackageNameConversion.toPath("");
        assertThat (actual).isEqualTo("");
    }

    @Test
    void shouldConvertWellFormedPackageNameToPath() {
        String actual = PackageNameConversion.toPath("mmm.coffee.widget");
        assertThat(actual).isEqualTo("mmm/coffee/widget");
    }
}
