package com.webservicepizzariazeff.www.exception_handler;

import java.util.Objects;

public class PurchaseFinishedExceptionHandler {

    private final String message;

    private PurchaseFinishedExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseFinishedExceptionHandler that = (PurchaseFinishedExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
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
