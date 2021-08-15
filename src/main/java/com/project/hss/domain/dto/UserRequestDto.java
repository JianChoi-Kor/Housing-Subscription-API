package com.project.hss.domain.dto;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

    @Getter
    @Setter
    public static class UserSignUp {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class UserLogin {
        private String email;
        private String password;
    }
}
