package com.webservicepizzariazeff.www.exception_handler;

public class PurchaseFinishedExceptionHandler {

    private final String message;

    private PurchaseFinishedExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static final class PurchaseFinishedExceptionHandlerBuilder {
        private String message;

        private PurchaseFinishedExceptionHandlerBuilder() {
        }

        public static PurchaseFinishedExceptionHandlerBuilder builder() {
            return new PurchaseFinishedExceptionHandlerBuilder();
        }

        public PurchaseFinishedExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public PurchaseFinishedExceptionHandler build() {
            return new PurchaseFinishedExceptionHandler(message);
        }
    }
}
