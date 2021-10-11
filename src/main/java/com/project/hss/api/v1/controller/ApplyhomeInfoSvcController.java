package com.project.hss.api.v1.controller;

import com.project.hss.api.lib.Helper;
import com.project.hss.api.v1.dto.request.api.*;
import com.project.hss.api.v1.dto.response.api.LttotPblancListRes;
import com.project.hss.api.v1.service.ApplyhomeInfoSvcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
import java.util.HashMap;
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

        Map<String, LttotPblancListRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LttotPblancListRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            LttotPblancListRes apiLttotPblancListRes = (LttotPblancListRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", apiLttotPblancListRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    @Operation(summary = "APT 분양 정보 조회", description = "APT 분양 정보 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getLttotPblancList")
    public ResponseEntity<?> getLttotPblancList(@Validated LttotPblancListReq lttotPblancListReq, Errors errors) throws IOException {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getLttotPblancList(lttotPblancListReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getNotAPTLttotPblancList")
    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancListReq notAPTLttotPblancListReq) {
        return applyhomeInfoSvcService.getNotAPTLttotPblancList(notAPTLttotPblancListReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 조회", description = "APT무순위/취소후재공급 분양정보 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancList")
    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancListReq remndrLttotPblancListReq) {
        return applyhomeInfoSvcService.getRemndrLttotPblancList(remndrLttotPblancListReq);
    }

    @Operation(summary = "APT 분양정보 상세 조회", description = "APT 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getAPTLttotPblancDetail")
    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetailReq aptLttotPblancDetailReq) {
        return applyhomeInfoSvcService.getAPTLttotPblancDetail(aptLttotPblancDetailReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getUrbtyOfctlLttotPblancDetail")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetailReq urbtyOfctlLttotPblancDetailReq) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancDetail(urbtyOfctlLttotPblancDetailReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 상세 조회", description = "APT무순위/취소후재공급 분양정보 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancDetail")
    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetailReq remndrLttotPblancDetailReq) {
        return applyhomeInfoSvcService.getRemndrLttotPblancDetail(remndrLttotPblancDetailReq);
    }

    @Operation(summary = "APT 분양정보 주택형별 상세 조회", description = "APT 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getAPTLttotPblancMdl")
    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdlReq aptLttotPblancMdlReq) {
        return applyhomeInfoSvcService.getAPTLttotPblancMdl(aptLttotPblancMdlReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getUrbtyOfctlLttotPblancMdl")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdlReq urbtyOfctlLttotPblancMdlReq) {
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancMdl(urbtyOfctlLttotPblancMdlReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", description = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc")
    @PostMapping("/getRemndrLttotPblancMdl")
    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdlReq remndrLttotPblancMdlReq) {
        return applyhomeInfoSvcService.getRemndrLttotPblancMdl(remndrLttotPblancMdlReq);
    }
}
