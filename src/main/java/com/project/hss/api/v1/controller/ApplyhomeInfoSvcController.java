package com.project.hss.api.v1.controller;

import com.project.hss.api.v1.dto.request.api.*;
import com.project.hss.api.v1.dto.response.api.Response;
import com.project.hss.api.v1.service.ApplyhomeInfoSvcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/hhs")
@RestController
public class ApplyhomeInfoSvcController {

    private final ApplyhomeInfoSvcService applyhomeInfoSvcService;
    private final com.project.hss.api.v1.dto.Response response;

    @Value("${openapi.decoding.key}")
    private String decodingKey;

    @Value("${openapi.encoding.key}")
    private String encodingKey;

    @GetMapping("/test")
    public ResponseEntity<?> apiTest() throws IOException, JAXBException {

        StringBuilder urlBuilder = new StringBuilder("http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("startmonth","UTF-8") + "=" + URLEncoder.encode("202101", "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
        urlBuilder.append("&" + URLEncoder.encode("endmonth","UTF-8") + "=" + URLEncoder.encode("202103", "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
        urlBuilder.append("&" + URLEncoder.encode("houseSecd","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*주택구분*/
        urlBuilder.append("&" + URLEncoder.encode("sido","UTF-8") + "=" + URLEncoder.encode("부산", "UTF-8")); /*공급지역*/
        urlBuilder.append("&" + URLEncoder.encode("houseName","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*주택명*/
        urlBuilder.append("&" + URLEncoder.encode("rentSecd","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*분양/임대 구분값*/
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

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
        String xml = sb.toString();

        Map<String, Response> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Response.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            Response apiResponse = (Response) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", apiResponse);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    // APT 분양 정보 조회
    @GetMapping("/getLttotPblancList")
    public ResponseEntity<?> getLttotPblancList(LttotPblancList lttotPblancList) throws IOException {
        return applyhomeInfoSvcService.getLttotPblancList(lttotPblancList);
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 조회
    @GetMapping("/getNotAPTLttotPblancList")
    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancList notAPTLttotPblancList) {
        return applyhomeInfoSvcService.getNotAPTLttotPblancList(notAPTLttotPblancList);
    }

    // APT무순위/취소후재공급 분양정보 조회
    @GetMapping("/getRemndrLttotPblancList")
    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancList remndrLttotPblancList) {
        return applyhomeInfoSvcService.getRemndrLttotPblancList(remndrLttotPblancList);
    }

    // APT 분양정보 상세 조회
    @GetMapping("/getAPTLttotPblancDetail")
    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetail aptLttotPblancDetail) {
        return applyhomeInfoSvcService.getAPTLttotPblancDetail(aptLttotPblancDetail);
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회
    @GetMapping("/getUrbtyOfctlLttotPblancDetail")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetail urbtyOfctlLttotPblancDetail) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancDetail(urbtyOfctlLttotPblancDetail);
    }

    // APT무순위/취소후재공급 분양정보 상세 조회
    @GetMapping("/getRemndrLttotPblancDetail")
    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetail remndrLttotPblancDetail) {
        return applyhomeInfoSvcService.getRemndrLttotPblancDetail(remndrLttotPblancDetail);
    }

    // APT 분양정보 주택형별 상세 조회
    @GetMapping("/getAPTLttotPblancMdl")
    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdl aptLttotPblancMdl) {
        return applyhomeInfoSvcService.getAPTLttotPblancMdl(aptLttotPblancMdl);
    }

    // 오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회
    @GetMapping("/getUrbtyOfctlLttotPblancMdl")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdl urbtyOfctlLttotPblancMdl) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancMdl(urbtyOfctlLttotPblancMdl);
    }

    // APT무순위/취소후재공급 분양정보 주택형별 상세 조회
    @GetMapping("/getRemndrLttotPblancMdl")
    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdl remndrLttotPblancMdl) {
        return applyhomeInfoSvcService.getRemndrLttotPblancMdl(remndrLttotPblancMdl);
    }
}
