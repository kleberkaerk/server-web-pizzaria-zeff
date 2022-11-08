package com.webservicepizzariazeff.www.exception_handler;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class InvalidCardExceptionHandler {

    @Schema(example = "Invalid card.")
    private final String message;

    private InvalidCardExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidCardExceptionHandler that = (InvalidCardExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public static final class InvalidCardExceptionHandlerBuilder {
        private String message;

        private InvalidCardExceptionHandlerBuilder() {
        }

        public static InvalidCardExceptionHandlerBuilder builder() {
            return new InvalidCardExceptionHandlerBuilder();
        }

        public InvalidCardExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public InvalidCardExceptionHandler build() {
            return new InvalidCardExceptionHandler(message);
        }
    }
}
