package com.project.hss.api.v1.dto.request.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LttotPblancList {

    // 인증키, 100, 필수
    private String serviceKey;

    // 공고 월(검색 시작 월), 6, 필수 ㄱ
    // 202101
    private String startmonth;

    // 공고 월(검색 종료 월), 6, 필수, 최대 12개월
    // 202103
    private String endmonth;

    // 주택 구분, 2, 옵션
    // 01: 민영, 03: 국민
    private String houseSecd;

    // 공급지역, 2, 옵션
    // 강원, 경기, 경남, 경북, 광주, 대구, 대전, 부산, 서울, 세종, 울산, 인천, 전남, 전북, 제주, 충남, 충분, 기타
    private String sido;

    // 주택명, 30, 옵션
    private String houseName;

    // 분양/임대, 2, 옵션
    // 0: 분양주택, 1: 분양전환 가능임대, 2: 분양전환 불가임대
    private String rentSecd;
}
