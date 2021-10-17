package com.project.hss.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class MembersResDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }

    @Getter
    @Setter
    public static class CertEmailSuccess {
        private String email;
        private String verifyCode;
    }

    @Getter
    @Setter
    public static class CertSmsSuccess {
        private String phoneNumber;
        private String verifyCode;
    }

    @Getter
    @Setter
    public static class SmsResponse {
        private String statusCode;
        private String statusName;
        private String requestId;
        private Timestamp requestTime;
    }
}
