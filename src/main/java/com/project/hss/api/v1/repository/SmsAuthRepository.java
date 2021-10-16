package com.project.hss.api.v1.repository;

import com.project.hss.api.entity.SmsAuth;
import com.project.hss.api.enums.SmsAuthUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsAuthRepository extends JpaRepository<SmsAuth, Long> {
    Optional<SmsAuth> findFirstByPhoneNumberAndAuthUsageOrderByIdxDesc(String phoneNumber, SmsAuthUsage smsAuthUsage);

    Optional<SmsAuth> findFirstByPhoneNumberAndCodeAndAuthUsage(String phoneNumber, String code, SmsAuthUsage smsAuthUsage);

    Optional<SmsAuth> findFirstByPhoneNumberAndVerifyCode(String phoneNumber, String verifyCode);
}
