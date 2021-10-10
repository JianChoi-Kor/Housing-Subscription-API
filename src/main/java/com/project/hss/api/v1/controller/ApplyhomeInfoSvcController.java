package com.project.hss.api.v1.controller;

import com.project.hss.api.v1.dto.request.api.*;
import com.project.hss.api.v1.dto.response.api.Response;
import com.project.hss.api.v1.service.ApplyhomeInfoSvcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "ApplyhomeInfoSvc", description = "전국 청약 분양정보 조회 서비스 api 데이터 요청")
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

    @Operation(summary = "APT 분양 정보 조회", description = "APT 분양 정보 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getLttotPblancList")
    public ResponseEntity<?> getLttotPblancList(LttotPblancList lttotPblancList) throws IOException {

        // TODO
        List<String> sidoList = Arrays.asList("강원", "경기", "경남", "경북", "광주", "대구", "대전", "부산", "서울",
                "세종", "울산", "인천", "전남", "전북", "제주", "충남", "충북", "기타");
        if (lttotPblancList.getSido() != null && !sidoList.contains(lttotPblancList.getSido())) {
            return response.fail("공급지역이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return applyhomeInfoSvcService.getLttotPblancList(lttotPblancList);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getNotAPTLttotPblancList")
    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancList notAPTLttotPblancList) {
        return applyhomeInfoSvcService.getNotAPTLttotPblancList(notAPTLttotPblancList);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 조회", description = "APT무순위/취소후재공급 분양정보 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancList")
    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancList remndrLttotPblancList) {
        return applyhomeInfoSvcService.getRemndrLttotPblancList(remndrLttotPblancList);
    }

    @Operation(summary = "APT 분양정보 상세 조회", description = "APT 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getAPTLttotPblancDetail")
    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetail aptLttotPblancDetail) {
        return applyhomeInfoSvcService.getAPTLttotPblancDetail(aptLttotPblancDetail);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getUrbtyOfctlLttotPblancDetail")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetail urbtyOfctlLttotPblancDetail) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancDetail(urbtyOfctlLttotPblancDetail);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 상세 조회", description = "APT무순위/취소후재공급 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancDetail")
    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetail remndrLttotPblancDetail) {
        return applyhomeInfoSvcService.getRemndrLttotPblancDetail(remndrLttotPblancDetail);
    }

    @Operation(summary = "APT 분양정보 주택형별 상세 조회", description = "APT 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getAPTLttotPblancMdl")
    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdl aptLttotPblancMdl) {
        return applyhomeInfoSvcService.getAPTLttotPblancMdl(aptLttotPblancMdl);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getUrbtyOfctlLttotPblancMdl")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdl urbtyOfctlLttotPblancMdl) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancMdl(urbtyOfctlLttotPblancMdl);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", description = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancMdl")
    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdl remndrLttotPblancMdl) {
        return applyhomeInfoSvcService.getRemndrLttotPblancMdl(remndrLttotPblancMdl);
    }
}
