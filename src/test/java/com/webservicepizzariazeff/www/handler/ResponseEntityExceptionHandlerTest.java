package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.MethodArgumentNotValidExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ResponseEntityExceptionHandlerTest {

    ResponseEntityExceptionHandler responseEntityExceptionHandler;

    ResponseEntity<ExistingUserExceptionHandler> existingUserExceptionHandlerResponseEntity;

    ResponseEntity<MethodArgumentNotValidExceptionHandler> methodArgumentNotValidExceptionHandler;

    ExistingUserException exception;

    @BeforeEach
    void setResponseEntityExceptionHandler() {

        this.responseEntityExceptionHandler = new ResponseEntityExceptionHandler();

        this.existingUserExceptionHandlerResponseEntity =
                new ResponseEntity<>(
                        ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                                .message("message ExistingUserExceptionHandler")
                                .build(), HttpStatus.CONFLICT);

        this.methodArgumentNotValidExceptionHandler =
                new ResponseEntity<>(
                        MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                                .message("Invalid value.")
                                .build(), HttpStatus.BAD_REQUEST);

        this.exception = new ExistingUserException("message ExistingUserExceptionHandler");
    }

    @Test
    void handlerExistingUserException_returnsAResponseEntityOfTypeExistingUserExceptionHandler_wheneverCalled() {

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerExistingUserException(this.exception).getStatusCode())
                .isEqualTo(this.existingUserExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(this.responseEntityExceptionHandler.handlerExistingUserException(this.exception).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(this.existingUserExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerMethodArgumentNotValidException() {

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .isEqualTo(this.methodArgumentNotValidExceptionHandler.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(this.responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(this.methodArgumentNotValidExceptionHandler.getBody()).getMessage());
    }
}