package com.project.hss.api.lib;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SmsUtils {

    public String createVerifyCode() {
        return RandomStringUtils.randomNumeric(6);
    }
}
