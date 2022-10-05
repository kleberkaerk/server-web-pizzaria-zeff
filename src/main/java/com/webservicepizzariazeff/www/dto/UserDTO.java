package com.webservicepizzariazeff.www.dto;

import javax.validation.constraints.NotEmpty;

public class UserDTO {

    @NotEmpty()
    private final String username;
    @NotEmpty()
    private final String password;

    private UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static final class UserDTOBuilder {
        private String username;
        private String password;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder builder() {
            return new UserDTOBuilder();
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
            return new UserDTO(username, password);
        }
    }
}
