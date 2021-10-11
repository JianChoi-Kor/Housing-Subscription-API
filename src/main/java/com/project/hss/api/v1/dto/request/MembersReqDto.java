package com.project.hss.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class MembersReqDto {

    @Getter
    @Setter
    public static class SignUp {
        @Schema(name = "이메일", example = "test@gmail.com")
        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @Schema(name = "비밀번호", example = "test1234!")
        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class Login {
        @Schema(description = "이메일", example = "test@test.test")
        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @Schema(description = "비밀번호", example = "test1234!")
        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class Reissue {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QudGVzdCIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MzM4NzA5OTF9.-4Z1z75ghz3TI0KPEmqut6gl3ntRZLeCxViUvwKU9RE")
        @NotEmpty(message = "accessToken 을 입력해주세요.")
        private String accessToken;

        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQ0NzM5OTF9.NxufxbcorGE59qcGnGJbIVrHWB9hbtXu38-bBze9py0")
        @NotEmpty(message = "refreshToken 을 입력해주세요.")
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class Logout {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QudGVzdCIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2MzM4NzA5OTF9.-4Z1z75ghz3TI0KPEmqut6gl3ntRZLeCxViUvwKU9RE")
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQ0NzM5OTF9.NxufxbcorGE59qcGnGJbIVrHWB9hbtXu38-bBze9py0")
        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }
}
