package com.project.hss.api.v1.dto.request.api;

public class NotAPTLttotPblancList {

    // 인증키, 100, 필수
    private String serviceKey;

    // 공고 월(검색 시작 월), 6, 필수 ㄱ
    // 202101
    private String startmonth;

    // 공고 월(검색 종료 월), 6, 필수, 최대 12개월
    // 202103
    private String endmonth;

    // 주택구분, 4, 옵션
    // 0201: 도시형생활주택, 0202: 오피스텔, 0203: 민간임대, 0303: 공공지원민간임대
    private String searchHouseSecd;
}
