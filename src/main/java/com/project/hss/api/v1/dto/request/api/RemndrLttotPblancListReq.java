package com.project.hss.api.v1.dto.request.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RemndrLttotPblancListReq {

    @Schema(description = "공고 월(검색 시작 월)", example = "202101")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String startmonth;

    @Schema(description = "공고 월(검색 종료 월) * 검색 기간은 최대 12개월", example = "202103")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String endmonth;

    @Schema(description = "주택명(건설업체명)", example = "금호어울림")
    private String searchName;
}
