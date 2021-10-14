package com.project.hss.api.lib;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Configuration
public class MailUtils {

    private final JavaMailSender javaMailSender;

    @Value("${mail.sender}")
    private String FROM_ADDRESS;

    public String createVerifyCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    public void sendMailForEmailCert(String email, String code) throws MessagingException {
        String html =
                "<!DOCTYPE html><html lang=\"ko\"><head><meta charset=\"utf-8\"></head><body><div style=\"background: #f9f9f9;padding: 100px 0;\">\n"
                        +
                        "            <div style=\"background:#fff;width:600px;padding:25px;margin:0 auto;border-radius:10px;font-family:noto sans korean, noto sans korean regular, noto sans cjk kr, noto sans cjk, nanum gothic, malgun gothic, dotum, arial, helvetica, MS Gothic, sans-serif!important;\">\n"
                        +
                        "                <h3 style=\"font-size:20px;color:#4a4a4a;border-top:1px solid #e2e6ed;padding-top:30px;margin-bottom:10px;\">청약알림이 회원가입을 환영합니다.</h3>\n"
                        +
                        "                <p style=\"margin:0;font-size:14px;color:#4a4a4a;\">이메일 인증을 위하여 아래의 코드를 입력하시면 가입이 정상적으로 진행됩니다.</p>\n"
                        +
                        "                <p style=\"margin:30px 0;font-size:14px;padding:15px;background:#ffeff5;border:1px solid #ffb7d1;font-weight:bold;border-radius:5px;color:#4a4a4a;\">이메일 인증코드 :  <span style=\"color:#ff347d;\">"
                        + code + "</span></p>\n" +
                        "                <p style=\"margin:0;font-size:14px;color:#4a4a4a;\">본 메일은 발신전용이며, 문의에 대한 회신은 처리되지 않습니다.</p>\n"
                        +
                        "            </div>\n" +
                        "            <p style=\"margin:30px 0 0 0;text-align:center;font-family:noto sans korean, noto sans korean regular, noto sans cjk kr, noto sans cjk, nanum gothic, malgun gothic, dotum, arial, helvetica, MS Gothic, sans-serif!important;\"><span style=\"display:inline-block;font-size:14px;padding-right:15px;color:#4a4a4a;\">주)닝슈컴퍼니</span><span style=\"display:inline-block;font-size:14px;padding-right:15px;color:#4a4a4a;\">대표이사 : 김수인</span><span style=\"display:inline-block;font-size:14px;color:#4a4a4a;\">주소 : 경북 경산시 대학로 9길 8 정평현대타운</span></p>\n"
                        +
                        "            <p style=\"margin:0;text-align:center;font-family:noto sans korean, noto sans korean regular, noto sans cjk kr, noto sans cjk, nanum gothic, malgun gothic, dotum, arial, helvetica, MS Gothic, sans-serif!important;\"><span style=\"display:inline-block;font-size:14px;color:#4a4a4a;\">Copyright&#169;shu._.nin Corp. All rights reserved.</span></p>\n"
                        +
                        "        </div></body></html>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(html, true);
        helper.setTo(email);
        helper.setSubject("청약알림이 회원가입 이메일 인증번호 입니다.");
        helper.setFrom(FROM_ADDRESS);
        javaMailSender.send(mimeMessage);
    }
}
