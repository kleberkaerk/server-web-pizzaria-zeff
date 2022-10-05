package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.MethodArgumentNotValidExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.ResourceBundle;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ExistingUserExceptionHandler> handlerExistingUserException(ExistingUserException exception) {

        return new ResponseEntity<>(ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionHandler> handlerMethodArgumentNotValidException() {

        ResourceBundle message = ResourceBundle.getBundle("messages", new Locale("en", "US"));

        return new ResponseEntity<>(MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder
                .builder()
                .message(message.getString("invalid.value"))
                .build(), HttpStatus.BAD_REQUEST);
    }
}
