package com.project.hss.api.v1.controller;

import com.project.hss.api.v1.dto.Response;
import com.project.hss.api.v1.service.ApplyhomeInfoSvcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/hhs")
@RestController
public class ApplyhomeInfoSvcController {

    private final ApplyhomeInfoSvcService applyhomeInfoSvcService;
    private final Response response;

    @Value("${openapi.decoding.key}")
    private String decodingKey;

    @Value("${openapi.encoding.key}")
    private String encodingKey;

    @GetMapping("/test")
    public void apiTest() throws IOException {

        System.out.println("origin decodingKey : " + decodingKey);
        System.out.println("decodingKey decode : " + URLDecoder.decode(decodingKey, "UTF-8"));
        System.out.println("decodingKey encode : " + URLEncoder.encode(decodingKey, "UTF-8"));

        System.out.println("origin encodingKey : " + encodingKey);
        System.out.println("encodingKey decode : " + URLDecoder.decode(encodingKey, "UTF-8"));
        System.out.println("encodingKey encode : " + URLEncoder.encode(encodingKey, "UTF-8"));

        StringBuilder urlBuilder = new StringBuilder("http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("startmonth","UTF-8") + "=" + URLEncoder.encode("202101", "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
        urlBuilder.append("&" + URLEncoder.encode("endmonth","UTF-8") + "=" + URLEncoder.encode("202103", "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
        urlBuilder.append("&" + URLEncoder.encode("houseSecd","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*주택구분*/
        urlBuilder.append("&" + URLEncoder.encode("sido","UTF-8") + "=" + URLEncoder.encode("강원", "UTF-8")); /*공급지역*/
        urlBuilder.append("&" + URLEncoder.encode("houseName","UTF-8") + "=" + URLEncoder.encode("횡성 벨라시티", "UTF-8")); /*주택명*/
        urlBuilder.append("&" + URLEncoder.encode("rentSecd","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*분양/임대 구분값*/
        URL url = new URL(urlBuilder.toString());

        System.out.println("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

    // APT 분양 정보 조회
    @GetMapping("/getLttotPblancList")
    public ResponseEntity<?> getLttotPblancList() {
        return applyhomeInfoSvcService.getLttotPblancList();
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 조회
    @GetMapping("/getNotAPTLttotPblancList")
    public ResponseEntity<?> getNotAPTLttotPblancList() {
        return applyhomeInfoSvcService.getNotAPTLttotPblancList();
    }

    // APT무순위/취소후재공급 분양정보 조회
    @GetMapping("/getRemndrLttotPblancList")
    public ResponseEntity<?> getRemndrLttotPblancList() {
        return applyhomeInfoSvcService.getNotAPTLttotPblancList();
    }

    // APT 분양정보 상세 조회
    @GetMapping("/getAPTLttotPblancDetail")
    public ResponseEntity<?> getAPTLttotPblancDetail() {
        return applyhomeInfoSvcService.getAPTLttotPblancDetail();
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회
    @GetMapping("/getUrbtyOfctlLttotPblancDetail")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail() {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancDetail();
    }

    // APT무순위/취소후재공급 분양정보 상세 조회
    @GetMapping("/getRemndrLttotPblancDetail")
    public ResponseEntity<?> getRemndrLttotPblancDetail() {
        return applyhomeInfoSvcService.getRemndrLttotPblancDetail();
    }

    // APT 분양정보 주택형별 상세 조회
    @GetMapping("/getAPTLttotPblancMdl")
    public ResponseEntity<?> getAPTLttotPblancMdl() {
        return applyhomeInfoSvcService.getAPTLttotPblancMdl();
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회
    @GetMapping("/getUrbtyOfctlLttotPblancMdl")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl() {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancMdl();
    }

    // APT무순위/취소후재공급 분양정보 주택형별 상세 조회
    @GetMapping("/getRemndrLttotPblancMdl")
    public ResponseEntity<?> getRemndrLttotPblancMdl() {
        return applyhomeInfoSvcService.getRemndrLttotPblancMdl();
    }
}
