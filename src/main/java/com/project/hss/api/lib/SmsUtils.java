package com.project.hss.api.lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hss.api.v1.dto.request.MembersReqDto;
import com.project.hss.api.v1.dto.response.MembersResDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SmsUtils {

    private final RestTemplate restTemplate;

    @Value("${sms.serviceId}")
    private String serviceId;

    @Value("${sms.secretKey}")
    private String secretKey;

    @Value("${sms.accessKey}")
    private String accessKey;

    public String createVerifyCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    private String getSignature(String time) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + serviceId + "/messages";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }

    public MembersResDto.SmsResponse sendSmsForSmsCert() throws JsonProcessingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException {
        String time = Long.toString(System.currentTimeMillis());
        // 메세지 생성
        List<MembersReqDto.SmsRequest.SmsMessage> smsMessageList = new ArrayList<>();
        MembersReqDto.SmsRequest.SmsMessage smsMessage = new MembersReqDto.SmsRequest.SmsMessage("01036142377", "청약알림이 휴대폰 인증 번호입니다.");
        smsMessageList.add(smsMessage);

        MembersReqDto.SmsRequest smsRequest = new MembersReqDto.SmsRequest();
        smsRequest.setMessages(smsMessageList);

        // json 형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);

        // 헤더 설정값 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);

        // signature 서명
        headers.set("x-ncp-apigw-signature-v2", getSignature(time));

        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);
        MembersResDto.SmsResponse smsResponse = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), body, MembersResDto.SmsResponse.class);

        return smsResponse;
    }
}
