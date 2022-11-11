package com.webservicepizzariazeff.www.exception_handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class ExistingUserExceptionHandler {

    @Schema(example = "This email is already being used, please choose another one.")
    private final String message;

    @JsonCreator()
    private ExistingUserExceptionHandler(@JsonProperty("message") String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExistingUserExceptionHandler that = (ExistingUserExceptionHandler) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
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
