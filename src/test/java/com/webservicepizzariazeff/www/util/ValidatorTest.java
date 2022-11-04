package com.webservicepizzariazeff.www.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class ValidatorTest {

    @Test
    void validateAcceptLanguage_doesNotThrowException_whenTheValueOfAcceptLanguageIsValid() {

        Assertions.assertThatCode(() -> Validator.validateAcceptLanguage("en-US"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateAcceptLanguage_throwsResponseStatusException_whenTheValueOfAcceptLanguageIsInvalid(){

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> Validator.validateAcceptLanguage("en"));
    }
}