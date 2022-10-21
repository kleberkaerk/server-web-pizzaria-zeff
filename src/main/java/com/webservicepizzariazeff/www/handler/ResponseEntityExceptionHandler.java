package com.webservicepizzariazeff.www.handler;

import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.exception_handler.ExistingAddressExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.MethodArgumentNotValidExceptionHandler;
import com.webservicepizzariazeff.www.exception_handler.NullPointerExceptionHandler;
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<NullPointerExceptionHandler> handlerNullPointerException(){

        return new ResponseEntity<>(NullPointerExceptionHandler.NullPointerExceptionHandlerBuilder.builder()
                .message(message.getString("invalid.request"))
                .build(), HttpStatus.BAD_REQUEST);
    }
}
