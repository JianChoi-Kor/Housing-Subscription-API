package com.project.hss.api.lib;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailUtils {
    public String createVerifyCode() {
        return RandomStringUtils.randomNumeric(6);
    }
}
