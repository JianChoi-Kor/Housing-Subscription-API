package com.project.hss.api.v1.dto.request.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LttotPblancListReq {

    @Schema(description = "공고 월(검색 시작 월)", example = "202101")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String startmonth;

    @Schema(description = "공고 월(검색 종료 월) * 검색 기간은 최대 12개월", example = "202103")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String endmonth;

    @Schema(description = "주택 구분 (01: 민영, 03: 국민)", example = "01")
    @Pattern(regexp = "^0([1|3])", message = "주택 구분이 올바르지 않습니다.")
    private String houseSecd;

    @Schema(description = "공급지역 (강원, 경기, 경남, 경북, 광주, 대구, 대전, 부산, 서울, 세종, 울산, 인천, 전남, 전북, 제주, 충남, 충북, 기타)", example = "대구")
    @Pattern(regexp = "^[가-힣]*$", message = "공급지역이 올바르지 않습니다.")
    private String sido;

    @Schema(description = "주택명", example = "횡성 벨라시티")
    private String houseName;

    @Schema(description = "분양/임대 (0: 분양주택, 1: 분양전환 가능임대, 2: 분양전환 불가임대)", example = "0")
    @Pattern(regexp = "[0|1|2]", message = "분양/임대 값이 올바르지 않습니다.")
    private String rentSecd;
}
