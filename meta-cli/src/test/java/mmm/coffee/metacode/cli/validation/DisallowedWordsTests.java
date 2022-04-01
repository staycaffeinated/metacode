/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.cli.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.google.common.truth.Truth.assertThat;

/**
 * DisallowedWordsTests
 */
public class DisallowedWordsTests {

    @ParameterizedTest
    @ValueSource( strings = { "Test", "test", "TEST" })
    void shouldRecognizeDisallowedWords(String proposedResourceName) {
        assertThat(DisallowedWords.isDisallowedWord(proposedResourceName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource( strings = { "Pet", "book", "FOO" })
    void shouldRecognizeAllowedWords(String proposedResourceName) {
        assertThat(DisallowedWords.isDisallowedWord(proposedResourceName)).isFalse();
    }
}
