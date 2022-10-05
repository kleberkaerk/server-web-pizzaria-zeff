package com.webservicepizzariazeff.www.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ExistingUserExceptionTest {

    @Test
    void constructorOfExistingUserException_createNewInstanceOfExistingUserException_whenTheConstructorIsCalled() {

        Assertions.assertThat(new ExistingUserException(""))
                .isNotNull()
                .isExactlyInstanceOf(ExistingUserException.class);
    }
}