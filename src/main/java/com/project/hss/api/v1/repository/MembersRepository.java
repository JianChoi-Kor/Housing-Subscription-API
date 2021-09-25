package com.project.hss.api.v1.repository;

import com.project.hss.api.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByEmail(String email);
    boolean existsByEmail(String email);
}
