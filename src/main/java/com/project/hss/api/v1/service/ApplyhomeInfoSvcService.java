package com.project.hss.api.v1.service;

import com.project.hss.api.v1.dto.Response;
import com.project.hss.api.v1.dto.request.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyhomeInfoSvcService {

    private final com.project.hss.api.v1.dto.Response response;

    @Value("${openapi.decoding.key}")
    private String decodingKey;

    @Value("${openapi.encoding.key}")
    private String encodingKey;

    public ResponseEntity<?> getLttotPblancList(LttotPblancList lttotPblancList) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc/getLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("startmonth","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getStartmonth(), "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
        urlBuilder.append("&" + URLEncoder.encode("endmonth","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getEndmonth(), "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
        urlBuilder.append("&" + URLEncoder.encode("houseSecd","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getHouseSecd(), "UTF-8")); /*주택구분*/
        urlBuilder.append("&" + URLEncoder.encode("sido","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getSido(), "UTF-8")); /*공급지역*/
        urlBuilder.append("&" + URLEncoder.encode("houseName","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getHouseName(), "UTF-8")); /*주택명*/
        urlBuilder.append("&" + URLEncoder.encode("rentSecd","UTF-8") + "=" + URLEncoder.encode(lttotPblancList.getRentSecd(), "UTF-8")); /*분양/임대 구분값*/
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

        Map<String, com.project.hss.api.v1.dto.response.api.Response> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.project.hss.api.v1.dto.response.api.Response.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            com.project.hss.api.v1.dto.response.api.Response apiResponse = (com.project.hss.api.v1.dto.response.api.Response) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", apiResponse);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancList notAPTLttotPblancList) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancList remndrLttotPblancList) {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetail aptLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetail urbtyOfctlLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetail remndrLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdl aptLttotPblancMdl) {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdl urbtyOfctlLttotPblancMdl) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdl remndrLttotPblancMdl) {
        return response.success();
    }
}
