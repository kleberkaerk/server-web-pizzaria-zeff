package com.webservicepizzariazeff.www.exception_handler;

public class ExistingAddressExceptionHandler {

    private final String message;

    private ExistingAddressExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static final class ExistingAddressExceptionHandlerBuilder {
        private String message;

        private ExistingAddressExceptionHandlerBuilder() {
        }

        public static ExistingAddressExceptionHandlerBuilder builder() {
            return new ExistingAddressExceptionHandlerBuilder();
        }

        public ExistingAddressExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExistingAddressExceptionHandler build() {
            return new ExistingAddressExceptionHandler(message);
        }
    }
}
