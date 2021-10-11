package com.project.hss.api.v1.service;

import com.project.hss.api.v1.dto.request.api.*;
import com.project.hss.api.v1.dto.response.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyhomeInfoSvcService {

    private final com.project.hss.api.v1.dto.Response response;

    private final static String openApiBaseUrl = "http://openapi.reb.or.kr/OpenAPI_ToolInstallPackage/service/rest/ApplyhomeInfoSvc";

    @Value("${openapi.decoding.key}")
    private String decodingKey;

    public ResponseEntity<?> getLttotPblancList(LttotPblancListReq lttotPblancListReq) throws IOException {
        // sido check
        List<String> sidoList = Arrays.asList("강원", "경기", "경남", "경북", "광주", "대구", "대전", "부산", "서울",
                "세종", "울산", "인천", "전남", "전북", "제주", "충남", "충북", "기타");
        if (lttotPblancListReq.getSido() != null && !sidoList.contains(lttotPblancListReq.getSido())) {
            return response.fail("공급지역이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("startmonth","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getStartmonth(), "UTF-8")); /*월 단위 모집공고일 (검색시작월)*/
        urlBuilder.append("&" + URLEncoder.encode("endmonth","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getEndmonth(), "UTF-8")); /*월 단위 모집공고일 (검색종료월, 최대 12개월)*/
        if (lttotPblancListReq.getHouseSecd() != null) {
            urlBuilder.append("&" + URLEncoder.encode("houseSecd","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getHouseSecd(), "UTF-8")); /*주택구분*/
        }
        if (lttotPblancListReq.getSido() != null) {
            urlBuilder.append("&" + URLEncoder.encode("sido","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getSido(), "UTF-8")); /*공급지역*/
        }
        if (lttotPblancListReq.getHouseName() != null) {
            urlBuilder.append("&" + URLEncoder.encode("houseName","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getHouseName(), "UTF-8")); /*주택명*/
        }
        if (lttotPblancListReq.getRentSecd() != null) {
            urlBuilder.append("&" + URLEncoder.encode("rentSecd","UTF-8") + "=" + URLEncoder.encode(lttotPblancListReq.getRentSecd(), "UTF-8")); /*분양/임대 구분값*/
        }
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
            LttotPblancListRes lttotPblancListRes = (LttotPblancListRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", lttotPblancListRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancListReq notAPTLttotPblancListReq) throws IOException {
        // searchHouseSecd check
        List<String> searchHouseSecdList = Arrays.asList("0201", "0202", "0203", "0303");
        if (notAPTLttotPblancListReq.getSearchHouseSecd() != null && !searchHouseSecdList.contains(notAPTLttotPblancListReq.getSearchHouseSecd())) {
            return response.fail("주택구분값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getNotAPTLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("startmonth", "UTF-8") + "=" + URLEncoder.encode(notAPTLttotPblancListReq.getStartmonth(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("endmonth", "UTF-8") + "=" + URLEncoder.encode(notAPTLttotPblancListReq.getEndmonth(), "UTF-8"));
        if (notAPTLttotPblancListReq.getSearchHouseSecd() != null) {
            urlBuilder.append("&" + URLEncoder.encode("searchHouseSecd", "UTF-8") + "=" + URLEncoder.encode(notAPTLttotPblancListReq.getSearchHouseSecd(), "UTF-8"));
        }
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        Map<String, NotAPTLttotPblancListRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NotAPTLttotPblancListRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            NotAPTLttotPblancListRes notAPTLttotPblancListRes = (NotAPTLttotPblancListRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", notAPTLttotPblancListRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancListReq remndrLttotPblancListReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getRemndrLttotPblancList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("startmonth", "UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancListReq.getStartmonth(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("endmonth", "UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancListReq.getEndmonth(), "UTF-8"));
        if (remndrLttotPblancListReq.getSearchName() != null) {
            urlBuilder.append("&" + URLEncoder.encode("searchName", "UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancListReq.getSearchName(), "UTF-8"));
        }
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        Map<String, RemndrLttotPblancListRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RemndrLttotPblancListRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            RemndrLttotPblancListRes remndrLttotPblancListRes = (RemndrLttotPblancListRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", remndrLttotPblancListRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetailReq aptLttotPblancDetailReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getAPTLttotPblancDetail");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo", "UTF-8") + "=" + URLEncoder.encode(aptLttotPblancDetailReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo", "UTF-8") + "=" + URLEncoder.encode(aptLttotPblancDetailReq.getPblancNo(), "UTF-8"));

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

        Map<String, APTLttotPblancDetailRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(APTLttotPblancDetailRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            APTLttotPblancDetailRes aptLttotPblancDetailRes = (APTLttotPblancDetailRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", aptLttotPblancDetailRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetailReq urbtyOfctlLttotPblancDetailReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getUrbtyOfctlLttotPblancDetail");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo","UTF-8") + "=" + URLEncoder.encode(urbtyOfctlLttotPblancDetailReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo","UTF-8") + "=" + URLEncoder.encode(urbtyOfctlLttotPblancDetailReq.getPblancNo(), "UTF-8"));

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

        Map<String, UrbtyOfctlLttotPblancDetailRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UrbtyOfctlLttotPblancDetailRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            UrbtyOfctlLttotPblancDetailRes urbtyOfctlLttotPblancDetailRes = (UrbtyOfctlLttotPblancDetailRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", urbtyOfctlLttotPblancDetailRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetailReq remndrLttotPblancDetailReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getRemndrLttotPblancDetail");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo","UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancDetailReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo","UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancDetailReq.getPblancNo(), "UTF-8"));

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

        Map<String, RemndrLttotPblancDetailRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RemndrLttotPblancDetailRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            RemndrLttotPblancDetailRes remndrLttotPblancDetailRes = (RemndrLttotPblancDetailRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", remndrLttotPblancDetailRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdlReq aptLttotPblancMdlReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getAPTLttotPblancMdl");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo","UTF-8") + "=" + URLEncoder.encode(aptLttotPblancMdlReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo","UTF-8") + "=" + URLEncoder.encode(aptLttotPblancMdlReq.getPblancNo(), "UTF-8"));

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

        Map<String, APTLttotPblancMdlRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(APTLttotPblancMdlRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            APTLttotPblancMdlRes aptLttotPblancMdlRes = (APTLttotPblancMdlRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", aptLttotPblancMdlRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdlReq urbtyOfctlLttotPblancMdlReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getUrbtyOfctlLttotPblancMdl");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo", "UTF-8") + "=" + URLEncoder.encode(urbtyOfctlLttotPblancMdlReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo", "UTF-8") + "=" + URLEncoder.encode(urbtyOfctlLttotPblancMdlReq.getPblancNo(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        Map<String, UrbtyOfctlLttotPblancMdlRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UrbtyOfctlLttotPblancMdlRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            UrbtyOfctlLttotPblancMdlRes urbtyOfctlLttotPblancMdlRes = (UrbtyOfctlLttotPblancMdlRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", urbtyOfctlLttotPblancMdlRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }

    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdlReq remndrLttotPblancMdlReq) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(openApiBaseUrl + "/getRemndrLttotPblancMdl");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(decodingKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("houseManageNo", "UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancMdlReq.getHouseManageNo(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pblancNo", "UTF-8") + "=" + URLEncoder.encode(remndrLttotPblancMdlReq.getPblancNo(), "UTF-8"));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

        Map<String, RemndrLttotPblancMdlRes> result = new HashMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RemndrLttotPblancMdlRes.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            RemndrLttotPblancMdlRes remndrLttotPblancMdlRes = (RemndrLttotPblancMdlRes) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
            result.put("response", remndrLttotPblancMdlRes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response.success(result);
    }
}
