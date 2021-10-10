package com.project.hss.api.v1.dto.request.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RemndrLttotPblancList {

    @Schema(name = "인증키")
    @NotBlank(message = "인증키는 필수값입니다.")
    private String serviceKey;

    // 공고 월(검색 시작 월), 6, 필수 ㄱ
    // 202101
    private String startmonth;

    // 공고 월(검색 종료 월), 6, 필수, 최대 12개월
    // 202103
    private String endmonth;

    // 주택명(건설업체명), 50, 옵션
    // 금호어울림
    private String searchName;
}
