package com.webservicepizzariazeff.www.exception_handler;

public class ExistingUserExceptionHandler {
    private final String message;

    private ExistingUserExceptionHandler(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static final class ExistingUserExceptionHandlerBuilder {
        private String message;

        private ExistingUserExceptionHandlerBuilder() {
        }

        public static ExistingUserExceptionHandlerBuilder builder() {
            return new ExistingUserExceptionHandlerBuilder();
        }

        public ExistingUserExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExistingUserExceptionHandler build() {
            return new ExistingUserExceptionHandler(message);
        }
    }
}
