package com.project.hss.api.entity;

import com.project.hss.api.enums.SmsAuthUsage;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SmsAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String code;

    @Column
    private String verifyCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SmsAuthUsage authUsage;

    // 매일 재전송 유효시간
    @Column(nullable = false)
    private LocalDateTime sendExpire;

    // 메일 인증 코드로 인증이 가능한 시간
    @Column
    private LocalDateTime verifyExpire;

    // 메일 인증 코드의 인증 유효시간
    @Column(nullable = false)
    private LocalDateTime validTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", insertable = false, updatable = false)
    private Members members;
}
