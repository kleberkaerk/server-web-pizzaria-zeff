package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.exception_handler.ExistingAddressExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.MethodArgumentNotValidExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

class ResponseEntityExceptionHandlerTest {

    private ResponseEntityExceptionHandler responseEntityExceptionHandler;

    private static ResponseEntity<ExistingUserExceptionHandler> existingUserExceptionHandlerResponseEntity;

    private static ResponseEntity<MethodArgumentNotValidExceptionHandler> methodArgumentNotValidExceptionHandlerResponseEntity;

    private static ResponseEntity<ExistingAddressExceptionHandler> existingAddressExceptionHandlerResponseEntity;

    private static ExistingUserException existingUserExceptionForArgument;

    private static ExistingAddressException existingAddressExceptionForArgument;

    @BeforeAll
    static void setObjects() {

        existingUserExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                        .message("message ExistingUserExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );

        methodArgumentNotValidExceptionHandlerResponseEntity = new ResponseEntity<>(
                MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                        .message("Invalid value.")
                        .build(), HttpStatus.BAD_REQUEST
        );

        existingUserExceptionForArgument = new ExistingUserException("message ExistingUserExceptionHandler");

        existingAddressExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                        .message("message ExistingAddressExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );

        existingAddressExceptionForArgument = new ExistingAddressException("message ExistingAddressExceptionHandler");
    }

    @BeforeEach
    void setResponseEntityExceptionHandler(){

        this.responseEntityExceptionHandler = new ResponseEntityExceptionHandler();
    }

    @Test
    void handlerExistingUserException_returnsAResponseEntityOfTypeExistingUserExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionForArgument).getStatusCode())
                .isEqualTo(existingUserExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(existingUserExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerMethodArgumentNotValidException_returnsAResponseEntityOfTypeMethodArgumentNotValidExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .isEqualTo(methodArgumentNotValidExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(methodArgumentNotValidExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerExistingAddressException_returnsAResponseEntityOfTypeExistingAddressExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getStatusCode())
                .isEqualTo(existingAddressExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(existingAddressExceptionHandlerResponseEntity.getBody()).getMessage());
    }
}