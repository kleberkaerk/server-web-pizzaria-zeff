package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.exception_handler.*;
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

    private static ResponseEntity<NullPointerExceptionHandler> nullPointerExceptionHandlerResponseEntity;

    private static ResponseEntity<PurchaseFinishedExceptionHandler> purchaseFinishedExceptionHandlerResponseEntity;

    private static ExistingUserException existingUserExceptionForArgument;

    private static ExistingAddressException existingAddressExceptionForArgument;

    private static PurchaseFinishedException purchaseFinishedException;

    static void setExistingUserExceptionHandlerResponseEntity() {

        existingUserExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                        .message("message ExistingUserExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setMethodArgumentNotValidExceptionHandlerResponseEntity() {

        methodArgumentNotValidExceptionHandlerResponseEntity = new ResponseEntity<>(
                MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                        .message("Invalid value.")
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    static void setExistingUserExceptionForArgument() {

        existingUserExceptionForArgument = new ExistingUserException("message ExistingUserExceptionHandler");
    }

    static void setExistingAddressExceptionHandlerResponseEntity() {

        existingAddressExceptionHandlerResponseEntity = new ResponseEntity<>(
                ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                        .message("message ExistingAddressExceptionHandler")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setExistingAddressExceptionForArgument() {

        existingAddressExceptionForArgument = new ExistingAddressException("message ExistingAddressExceptionHandler");
    }

    static void setNullPointerExceptionHandlerResponseEntity() {

        nullPointerExceptionHandlerResponseEntity = new ResponseEntity<>(
                NullPointerExceptionHandler.NullPointerExceptionHandlerBuilder.builder()
                        .message("Invalid Request.")
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    static void setPurchaseFinishedException() {

        purchaseFinishedException = new PurchaseFinishedException("message PurchaseFinishedException");
    }

    static void setPurchaseFinishedExceptionHandlerResponseEntity() {

        purchaseFinishedExceptionHandlerResponseEntity = new ResponseEntity<>(
                PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                        .message("message PurchaseFinishedException")
                        .build(), HttpStatus.CONFLICT);
    }

    @BeforeAll
    static void initializeObjects() {

        setExistingUserExceptionHandlerResponseEntity();
        setMethodArgumentNotValidExceptionHandlerResponseEntity();
        setExistingUserExceptionForArgument();
        setExistingAddressExceptionHandlerResponseEntity();
        setExistingAddressExceptionForArgument();
        setNullPointerExceptionHandlerResponseEntity();
        setPurchaseFinishedException();
        setPurchaseFinishedExceptionHandlerResponseEntity();
    }

    @BeforeEach
    void setResponseEntityExceptionHandler() {

        this.responseEntityExceptionHandler = new ResponseEntityExceptionHandler();
    }

    @Test
    void handlerExistingUserException_returnsAResponseEntityOfTypeExistingUserExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionForArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionForArgument).getStatusCode())
                .isEqualTo(existingUserExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(existingUserExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerMethodArgumentNotValidException_returnsAResponseEntityOfTypeMethodArgumentNotValidExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .doesNotThrowAnyException();

        Assertions.assertThat(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .isEqualTo(methodArgumentNotValidExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(methodArgumentNotValidExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerExistingAddressException_returnsAResponseEntityOfTypeExistingAddressExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> this.responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getStatusCode())
                .isEqualTo(existingAddressExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionForArgument).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(existingAddressExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerNullPointerException_returnsAResponseEntityOfTypeNullPointerExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> this.responseEntityExceptionHandler.handlerNullPointerException())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerNullPointerException().getStatusCode())
                .isEqualTo(nullPointerExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(this.responseEntityExceptionHandler.handlerNullPointerException().getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(nullPointerExceptionHandlerResponseEntity.getBody()).getMessage());
    }

    @Test
    void handlerPurchaseFinishedException_returnsAResponseEntityOfTypePurchaseFinishedExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> this.responseEntityExceptionHandler.handlerPurchaseFinishedException(purchaseFinishedException))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerPurchaseFinishedException(purchaseFinishedException).getStatusCode())
                .isEqualTo(purchaseFinishedExceptionHandlerResponseEntity.getStatusCode());

        Assertions.assertThat(Objects.requireNonNull(this.responseEntityExceptionHandler.handlerPurchaseFinishedException(purchaseFinishedException).getBody()).getMessage())
                .isEqualTo(Objects.requireNonNull(purchaseFinishedExceptionHandlerResponseEntity.getBody()).getMessage());
    }
}