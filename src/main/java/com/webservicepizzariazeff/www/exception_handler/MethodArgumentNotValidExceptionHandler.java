package com.webservicepizzariazeff.www.exception_handler;

import java.util.Objects;

public class MethodArgumentNotValidExceptionHandler {

    private final String message;

    private MethodArgumentNotValidExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodArgumentNotValidExceptionHandler that = (MethodArgumentNotValidExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
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
