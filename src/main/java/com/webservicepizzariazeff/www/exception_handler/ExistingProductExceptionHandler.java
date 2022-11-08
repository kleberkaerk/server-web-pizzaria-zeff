package com.webservicepizzariazeff.www.exception_handler;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class ExistingProductExceptionHandler {

    @Schema(example = "This product has been registered before.")
    private final String message;

    private ExistingProductExceptionHandler(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistingProductExceptionHandler that = (ExistingProductExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    public static final class ExistingProductExceptionHandlerBuilder {
        private String message;

        private ExistingProductExceptionHandlerBuilder() {
        }

        public static ExistingProductExceptionHandlerBuilder builder() {
            return new ExistingProductExceptionHandlerBuilder();
        }

        public ExistingProductExceptionHandlerBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExistingProductExceptionHandler build() {
            return new ExistingProductExceptionHandler(message);
        }
    }
}
