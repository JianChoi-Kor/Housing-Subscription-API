package com.project.hss.api.v1.dto.request.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LttotPblancList {

    @Schema(name = "인증키")
    @NotBlank(message = "인증키는 필수값입니다.")
    private String serviceKey;

    // 공고 월(검색 시작 월), 6, 필수 ㄱ
    // 202101
    @Schema(name = "공고 월(검색 시작 월)", example = "202101")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String startmonth;

    // 공고 월(검색 종료 월), 6, 필수, 최대 12개월
    // 202103
    @Schema(name = "공고 월(검색 종료 월)", description = "검색 기간은 최대 12개월", example = "202103")
    @Pattern(regexp = "^[0-9]{6}$", message = "공고 월이 올바르지 않습니다.")
    @NotBlank(message = "공고 월은 필수값입니다.")
    private String endmonth;

    // 주택 구분, 2, 옵션
    // 01: 민영, 03: 국민
    @Schema(name = "주택 구분", description = "01: 민영, 03: 국민", example = "01")
    @Pattern(regexp = "[01|03]", message = "주택 구분이 올바르지 않습니다.")
    private String houseSecd;

    // 공급지역, 2, 옵션
    // 강원, 경기, 경남, 경북, 광주, 대구, 대전, 부산, 서울, 세종, 울산, 인천, 전남, 전북, 제주, 충남, 충북, 기타
    @Schema(name = "공급지역", description = "강원, 경기, 경남, 경북, 광주, 대구, 대전, 부산, 서울, 세종, 울산, 인천, 전남, 전북, 제주, 충남, 충북, 기타", example = "대구")
    @Pattern(regexp = "^[가-힣]$", message = "공급지역이 올바르지 않습니다.")
    private String sido;

    // 주택명, 30, 옵션
    @Schema(name = "주택명", example = "횡성 벨라시티")
    private String houseName;

    // 분양/임대, 2, 옵션
    // 0: 분양주택, 1: 분양전환 가능임대, 2: 분양전환 불가임대
    @Schema(name = "분양/임대", description = "0: 분양주택, 1: 분양전환 가능임대, 2: 분양전환 불가임대", example = "0")
    @Pattern(regexp = "[0|1|2]", message = "분양/임대 값이 올바르지 않습니다.")
    private String rentSecd;
}
