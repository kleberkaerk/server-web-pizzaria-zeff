package com.webservicepizzariazeff.www.exception_handler;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class ExistingAddressExceptionHandler {

    @Schema(example = "This address has already been registered before.")
    private final String message;

    private ExistingAddressExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistingAddressExceptionHandler that = (ExistingAddressExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
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
