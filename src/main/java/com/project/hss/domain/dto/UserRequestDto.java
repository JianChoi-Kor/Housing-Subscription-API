package com.project.hss.domain.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUpDto {

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
//        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$\n", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class LoginDto {

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        private String email;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateTokenDto {

        @NotEmpty(message = "Access Token 은 필수 입력값입니다.")
        private String accessToken;

        @NotEmpty(message = "Refresh Token 은 필수 입력값읍니다.")
        private String refreshToken;
    }
}
