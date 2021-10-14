package com.project.hss.api.v1.repository;

import com.project.hss.api.entity.MailAuth;
import com.project.hss.api.enums.MailAuthUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailAuthRepository extends JpaRepository<MailAuth, Long> {
    Optional<MailAuth> findFirstByEmailAndAuthUsageOrderByIdxDesc(String email, MailAuthUsage mailAuthUsage);
}
