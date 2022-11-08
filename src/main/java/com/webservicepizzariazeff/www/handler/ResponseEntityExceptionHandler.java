package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.*;
import com.webservicepizzariazeff.www.exception_handler.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.ResourceBundle;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    private static final ResourceBundle message = ResourceBundle.getBundle("messages", new Locale("en", "US"));

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ExistingUserExceptionHandler> handlerExistingUserException(ExistingUserException exception) {

        return new ResponseEntity<>(ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionHandler> handlerMethodArgumentNotValidException() {

        return new ResponseEntity<>(MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder
                .builder()
                .message(message.getString("invalid.value"))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistingAddressException.class)
    public ResponseEntity<ExistingAddressExceptionHandler> handlerExistingAddressException(ExistingAddressException exception) {

        return new ResponseEntity<>(ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PurchaseFinishedException.class)
    public ResponseEntity<PurchaseFinishedExceptionHandler> handlerPurchaseFinishedException(PurchaseFinishedException exception) {

        return new ResponseEntity<>(PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistingProductException.class)
    public ResponseEntity<ExistingProductExceptionHandler> handlerExistingProductException(ExistingProductException exception) {

        return new ResponseEntity<>(ExistingProductExceptionHandler.ExistingProductExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<InvalidCardExceptionHandler> handlerInvalidCardException(InvalidCardException exception){

        return new ResponseEntity<>(InvalidCardExceptionHandler.InvalidCardExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
