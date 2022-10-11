package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorTest {

    @Test
    void checkThatStringIsNotAEnum_returnsFalse_whenStringPassedBiPredicateValidation() {

        Assertions.assertThat(Validator.checkThatStringIsNotAEnum("DRINK", Type.values(), (enumeration, string) -> enumeration.name().equals(string)))
                .isFalse();
    }

    @Test
    void checkThatStringIsNotAEnum_returnsTrue_whenStringNotPassedBiPredicateValidation() {

        Assertions.assertThat(Validator.checkThatStringIsNotAEnum("INVALID", Type.values(), (enumeration, string) -> enumeration.name().equals(string)))
                .isTrue();
    }
}