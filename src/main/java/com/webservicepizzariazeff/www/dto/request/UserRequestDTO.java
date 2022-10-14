package com.webservicepizzariazeff.www.dto.request;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty()
    private final String name;

    @NotEmpty()
    private final String username;

    @NotEmpty()
    private final String password;

    private UserDTO(String name, String username, String password) {

        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static final class UserDTOBuilder {
        private @NotEmpty() String name;
        private @NotEmpty() String username;
        private @NotEmpty() String password;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder builder() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(name, username, password);
        }
    }
}
