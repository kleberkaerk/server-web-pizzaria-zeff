package com.webservicepizzariazeff.www.exception_handler;

public class MethodArgumentNotValidExceptionHandler {

    private final String message;

    private MethodArgumentNotValidExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }


    public static final class MethodArgumentNotValidExceptionHandlerBuilder {
        private String message;

        private MethodArgumentNotValidExceptionHandlerBuilder() {
        }

        public static MethodArgumentNotValidExceptionHandlerBuilder builder() {
            return new MethodArgumentNotValidExceptionHandlerBuilder();
        }

        public MethodArgumentNotValidExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MethodArgumentNotValidExceptionHandler build() {
            return new MethodArgumentNotValidExceptionHandler(message);
        }
    }
}
