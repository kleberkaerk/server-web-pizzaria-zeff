package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.*;
import com.webservicepizzariazeff.www.exception_handler.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ResponseEntityExceptionHandlerTest {

    private ResponseEntityExceptionHandler responseEntityExceptionHandler;

    private static ExistingUserException existingUserExceptionToArgument;

    private static ResponseEntity<ExistingUserExceptionHandler> existingUserExceptionHandlerToComparison;

    private static ResponseEntity<MethodArgumentNotValidExceptionHandler> methodArgumentNotValidExceptionHandlerToComparison;

    private static ExistingAddressException existingAddressExceptionToArgument;

    private static ResponseEntity<ExistingAddressExceptionHandler> existingAddressExceptionHandlerToComparison;

    private static PurchaseFinishedException purchaseFinishedExceptionToArgument;

    private static ResponseEntity<PurchaseFinishedExceptionHandler> purchaseFinishedExceptionHandlerToComparison;

    private static ExistingProductException existingProductExceptionToArgument;

    private static ResponseEntity<ExistingProductExceptionHandler> existingProductExceptionHandlerToComparison;

    private static InvalidCardException invalidCardExceptionToArgument;

    private static ResponseEntity<InvalidCardExceptionHandler> invalidCardExceptionHandlerToComparison;

    static void setExistingUserExceptionToArgument() {

        existingUserExceptionToArgument = new ExistingUserException("message ExistingUserException");
    }

    static void setExistingUserExceptionHandlerToComparison() {

        existingUserExceptionHandlerToComparison = new ResponseEntity<>(
                ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                        .message("message ExistingUserException")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setMethodArgumentNotValidExceptionHandlerToComparison() {

        methodArgumentNotValidExceptionHandlerToComparison = new ResponseEntity<>(
                MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                        .message("Invalid value.")
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    static void setExistingAddressExceptionToArgument() {

        existingAddressExceptionToArgument = new ExistingAddressException("message ExistingAddressException");
    }

    static void setExistingAddressExceptionHandlerToComparison() {

        existingAddressExceptionHandlerToComparison = new ResponseEntity<>(
                ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                        .message("message ExistingAddressException")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setPurchaseFinishedExceptionToArgument() {

        purchaseFinishedExceptionToArgument = new PurchaseFinishedException("message PurchaseFinishedException");
    }

    static void setPurchaseFinishedExceptionHandlerToComparison() {

        purchaseFinishedExceptionHandlerToComparison = new ResponseEntity<>(
                PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                        .message("message PurchaseFinishedException")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setExistingProductExceptionToArgument() {

        existingProductExceptionToArgument = new ExistingProductException("message ExistingProductException");
    }

    static void setExistingProductExceptionHandlerToComparison() {

        existingProductExceptionHandlerToComparison = new ResponseEntity<>(
                ExistingProductExceptionHandler.ExistingProductExceptionHandlerBuilder.builder()
                        .message("message ExistingProductException")
                        .build(), HttpStatus.CONFLICT
        );
    }

    static void setInvalidCardExceptionToArgument(){

        invalidCardExceptionToArgument = new InvalidCardException("message InvalidCardException");
    }

    static void setInvalidCardExceptionHandlerToComparison(){

        invalidCardExceptionHandlerToComparison = new ResponseEntity<>(
                InvalidCardExceptionHandler.InvalidCardExceptionHandlerBuilder.builder()
                        .message("message InvalidCardException")
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @BeforeAll
    static void initializeObjects() {

        setExistingUserExceptionToArgument();
        setExistingUserExceptionHandlerToComparison();
        setMethodArgumentNotValidExceptionHandlerToComparison();
        setExistingAddressExceptionToArgument();
        setExistingAddressExceptionHandlerToComparison();
        setPurchaseFinishedExceptionToArgument();
        setPurchaseFinishedExceptionHandlerToComparison();
        setExistingProductExceptionToArgument();
        setExistingProductExceptionHandlerToComparison();
        setInvalidCardExceptionToArgument();
        setInvalidCardExceptionHandlerToComparison();
    }

    @BeforeEach
    void setResponseEntityExceptionHandler() {

        this.responseEntityExceptionHandler = new ResponseEntityExceptionHandler();
    }

    @Test
    void handlerExistingUserException_returnsAResponseEntityOfTypeExistingUserExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionToArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(responseEntityExceptionHandler.handlerExistingUserException(existingUserExceptionToArgument))
                .isNotNull()
                .isEqualTo(existingUserExceptionHandlerToComparison);
    }

    @Test
    void handlerMethodArgumentNotValidException_returnsAResponseEntityOfTypeMethodArgumentNotValidExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> responseEntityExceptionHandler.handlerMethodArgumentNotValidException().getStatusCode())
                .doesNotThrowAnyException();

        Assertions.assertThat(responseEntityExceptionHandler.handlerMethodArgumentNotValidException())
                .isNotNull()
                .isEqualTo(methodArgumentNotValidExceptionHandlerToComparison);
    }

    @Test
    void handlerExistingAddressException_returnsAResponseEntityOfTypeExistingAddressExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> this.responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionToArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerExistingAddressException(existingAddressExceptionToArgument))
                .isNotNull()
                .isEqualTo(existingAddressExceptionHandlerToComparison);
    }

    @Test
    void handlerPurchaseFinishedException_returnsAResponseEntityOfTypePurchaseFinishedExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(() -> this.responseEntityExceptionHandler.handlerPurchaseFinishedException(purchaseFinishedExceptionToArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerPurchaseFinishedException(purchaseFinishedExceptionToArgument))
                .isNotNull()
                .isEqualTo(purchaseFinishedExceptionHandlerToComparison);
    }

    @Test
    void handlerExistingProductException_returnsAResponseEntityOfTypeExistingProductExceptionHandler_wheneverCalled() {

        Assertions.assertThatCode(()-> this.responseEntityExceptionHandler.handlerExistingProductException(existingProductExceptionToArgument))
                .doesNotThrowAnyException();
        Assertions.assertThat(this.responseEntityExceptionHandler.handlerExistingProductException(existingProductExceptionToArgument))
                .isNotNull()
                .isEqualTo(existingProductExceptionHandlerToComparison);
    }

    @Test
    void handlerInvalidCardException_returnsAResponseEntityOfTypeInvalidCardExceptionHandler_wheneverCalled(){

        Assertions.assertThatCode(()-> this.responseEntityExceptionHandler.handlerInvalidCardException(invalidCardExceptionToArgument))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.responseEntityExceptionHandler.handlerInvalidCardException(invalidCardExceptionToArgument))
                .isNotNull()
                .isEqualTo(invalidCardExceptionHandlerToComparison);
    }
}