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

    private static ResponseEntityExceptionHandler responseEntityExceptionHandler;

    private ResponseEntity<ExistingUserExceptionHandler> existingUserExceptionHandlerResponseEntity;

    private ResponseEntity<MethodArgumentNotValidExceptionHandler> methodArgumentNotValidExceptionHandlerResponseEntity;

    private ResponseEntity<ExistingAddressExceptionHandler> existingAddressExceptionHandlerResponseEntity;

    private ExistingUserException existingUserExceptionForArgument;

    private ExistingAddressException existingAddressExceptionForArgument;


    @BeforeAll
    static void setResponseEntityExceptionHandler() {

        responseEntityExceptionHandler = new ResponseEntityExceptionHandler();
    }

    @BeforeEach
    void setObjects() {

        this.existingUserExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                        .message("message ExistingUserExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );

        this.methodArgumentNotValidExceptionHandlerResponseEntity = new ResponseEntity<>(
                MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                        .message("Invalid value.")
                        .build(), HttpStatus.BAD_REQUEST
        );

        this.existingUserExceptionForArgument = new ExistingUserException("message ExistingUserExceptionHandler");

        this.existingAddressExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                        .message("message ExistingAddressExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );

        this.existingAddressExceptionForArgument = new ExistingAddressException("message ExistingAddressExceptionHandler");
    }

    @Test
    void handlerExistingUserException_returnsAResponseEntityOfTypeExistingUserExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingUserException(this.existingUserExceptionForArgument).getStatusCode())
                .isEqualTo(this.existingUserExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingUserException(this.existingUserExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(this.existingUserExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerMethodArgumentNotValidException_returnsAResponseEntityOfTypeMethodArgumentNotValidExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .isEqualTo(this.methodArgumentNotValidExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(this.methodArgumentNotValidExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerExistingAddressException_returnsAResponseEntityOfTypeExistingAddressExceptionHandler_wheneverCalled() {

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getStatusCode())
                .isEqualTo(this.existingAddressExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(this.existingAddressExceptionHandlerResponseEntity.getBody()).getMessage());
    }
}