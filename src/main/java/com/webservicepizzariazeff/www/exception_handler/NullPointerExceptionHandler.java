package com.webservicepizzariazeff.www.exception_handler;

public class NullPointerExceptionHandler {

    private final String message;

    private NullPointerExceptionHandler(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static final class NullPointerExceptionHandlerBuilder {
        private String message;

        private NullPointerExceptionHandlerBuilder() {
        }

        public static NullPointerExceptionHandlerBuilder builder() {
            return new NullPointerExceptionHandlerBuilder();
        }

        public NullPointerExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public NullPointerExceptionHandler build() {
            return new NullPointerExceptionHandler(message);
        }
    }
}
