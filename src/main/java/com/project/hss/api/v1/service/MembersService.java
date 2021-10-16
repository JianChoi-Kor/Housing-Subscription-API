package com.project.hss.api.v1.service;

import com.project.hss.api.entity.MailAuth;
import com.project.hss.api.entity.Members;
import com.project.hss.api.entity.SmsAuth;
import com.project.hss.api.enums.Authority;
import com.project.hss.api.enums.MailAuthUsage;
import com.project.hss.api.enums.SmsAuthUsage;
import com.project.hss.api.jwt.JwtTokenProvider;
import com.project.hss.api.lib.Encrypt;
import com.project.hss.api.lib.MailUtils;
import com.project.hss.api.lib.SmsUtils;
import com.project.hss.api.v1.dto.Response;
import com.project.hss.api.v1.dto.request.MembersReqDto;
import com.project.hss.api.v1.dto.response.MembersResDto;
import com.project.hss.api.v1.repository.MailAuthRepository;
import com.project.hss.api.v1.repository.MembersRepository;
import com.project.hss.api.v1.repository.SmsAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private final MailAuthRepository mailAuthRepository;
    private final SmsAuthRepository smsAuthRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;
    private final MailUtils mailUtils;
    private final SmsUtils smsUtils;

    public ResponseEntity<?> signUp(MembersReqDto.SignUp signUp) {
        if (membersRepository.existsByEmail(signUp.getEmail())) {
            return response.fail("이미 회원가입된 이메일입니다.");
        }

        // 메일 인증 체크
        Optional<MailAuth> mailAuthOptional = mailAuthRepository.findFirstByEmailAndVerifyCode(signUp.getEmail(), signUp.getEmailVerifyCode());
        if (mailAuthOptional.isPresent()) {
            MailAuth mailAuth = mailAuthOptional.get();
            LocalDateTime emailValidTime = mailAuth.getValidTime();
            LocalDateTime emailNow = LocalDateTime.now();

            if (emailNow.isAfter(emailValidTime)) {
                return response.fail("해당하는 이메일 인증코드는 사용할 수 없습니다.");
            }

            Optional<SmsAuth> smsAuthOptional = smsAuthRepository.findFirstByPhoneNumberAndVerifyCode(signUp.getPhoneNumber(), signUp.getPhoneVerifyCode());
            if (smsAuthOptional.isPresent()) {
                SmsAuth smsAuth = smsAuthOptional.get();
                LocalDateTime smsValidTime = smsAuth.getValidTime();
                LocalDateTime smsNow = LocalDateTime.now();
                if (smsNow.isAfter(smsValidTime)) {
                    return response.fail("해당하는 휴대폰 인증코드는 사용할 수 없습니다.");
                }

                Members member = Members.builder()
                        .email(signUp.getEmail())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .phoneNumber(signUp.getPhoneNumber())
                        .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                        .build();
                membersRepository.save(member);

                return response.success("회원가입에 성공했습니다.");
            }
            return response.fail("회원가입에 실패했습니다. 휴대폰 인증 확인에 실패했습니다.");
        }
        return response.fail("회원가입에 실패했습니다. 이메일 인증 확인에 실패했습니다.");
    }

    public ResponseEntity<?> sendEmail(MembersReqDto.SendEmail sendEmail) throws MessagingException {
        if (membersRepository.existsByEmail(sendEmail.getEmail())) {
            return response.fail("이미 가입된 이메일입니다.");
        }
        Optional<MailAuth> mailAuthOptional =
                mailAuthRepository.findFirstByEmailAndAuthUsageOrderByIdxDesc(sendEmail.getEmail(), MailAuthUsage.SIGN_UP);
        if (mailAuthOptional.isPresent()) {
            MailAuth mailAuth = mailAuthOptional.get();
            LocalDateTime sendExpire = mailAuth.getSendExpire();
            if (LocalDateTime.now().isBefore(sendExpire)) {
                return response.fail("이메일 재발송은 30초 이후 가능합니다.");
            }
        }

        // 인증 코드 생성
        String code = mailUtils.createVerifyCode();
        // 이메일 전송
        mailUtils.sendMailForEmailCert(sendEmail.getEmail(), code);

        LocalDateTime now = LocalDateTime.now();
        mailAuthRepository.save(MailAuth.builder()
                .email(sendEmail.getEmail())
                .code(code)
                .authUsage(MailAuthUsage.SIGN_UP)
                .sendExpire(now.plusSeconds(30))
                .verifyExpire(now.plusMinutes(3))
                .validTime(now.plusMinutes(10))
                .build());
        return response.success("인증 메일이 발송되었습니다.");
    }

    public ResponseEntity<?> certEmail(MembersReqDto.CertEmail certEmail) {
        // 회원가입용 이메일과 인증번호를 통한 데이터 조회
        Optional<MailAuth> mailAuthOptional = mailAuthRepository.findFirstByEmailAndCodeAndAuthUsage(certEmail.getEmail(), certEmail.getCode(), MailAuthUsage.SIGN_UP);
        if (mailAuthOptional.isPresent()) {
            MailAuth mailAuth = mailAuthOptional.get();
            LocalDateTime verifyExpire = mailAuth.getVerifyExpire();
            LocalDateTime validTime = mailAuth.getValidTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(validTime)) {
                return response.fail("해당하는 인증코드는 사용할 수 없습니다.");
            }

            if (now.isAfter(verifyExpire)) {
                return response.fail("인증 가능한 시간이 만료되었습니다.");
            }
            mailAuth.setVerifyCode(Encrypt.getSha2bit256(certEmail.getEmail() + certEmail.getCode() + System.currentTimeMillis()));
            mailAuthRepository.save(mailAuth);

            MembersResDto.CertEmailSuccess certEmailSuccess = new MembersResDto.CertEmailSuccess();
            certEmailSuccess.setEmail(certEmail.getEmail());
            certEmailSuccess.setVerifyCode(mailAuth.getVerifyCode());
            return response.success(certEmailSuccess);
        }
        return response.fail("이메일 인증에 실패했습니다.");
    }

    public ResponseEntity<?> sendSms(MembersReqDto.SendSms sendSms) {
        if (membersRepository.existsByPhoneNumber(sendSms.getPhoneNumber())) {
            return response.fail("이미 가입된 휴대폰입니다.");
        }

        Optional<SmsAuth> smsAuthOptional = smsAuthRepository.findFirstByPhoneNumberAndAuthUsageOrderByIdxDesc(sendSms.getPhoneNumber(), SmsAuthUsage.SIGN_UP);
        if (smsAuthOptional.isPresent()) {
            SmsAuth smsAuth = smsAuthOptional.get();
            LocalDateTime sendExpire = smsAuth.getSendExpire();
            if (LocalDateTime.now().isBefore(sendExpire)) {
                return response.fail("휴대폰 인증 재발송은 30초 이후 가능합니다.");
            }
        }

        String code = smsUtils.createVerifyCode();
        // TODO:: sms 발송
        LocalDateTime now = LocalDateTime.now();
        smsAuthRepository.save(SmsAuth.builder()
                .phoneNumber(sendSms.getPhoneNumber())
                .code(code)
                .authUsage(SmsAuthUsage.SIGN_UP)
                .sendExpire(now.plusSeconds(30))
                .verifyExpire(now.plusMinutes(3))
                .validTime(now.plusMinutes(10))
                .build());
        return response.success("인증 번호가 발송되었습니다.");
    }

    public ResponseEntity<?> certSms(MembersReqDto.CertSms certSms) {
        Optional<SmsAuth> smsAuthOptional = smsAuthRepository.findFirstByPhoneNumberAndCodeAndAuthUsage(certSms.getPhoneNumber(), certSms.getCode(), SmsAuthUsage.SIGN_UP);
        if (smsAuthOptional.isPresent()) {
            SmsAuth smsAuth = smsAuthOptional.get();
            LocalDateTime verifyExpire = smsAuth.getVerifyExpire();
            LocalDateTime validTime = smsAuth.getValidTime();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(validTime)) {
                return response.fail("해당하는 인증코드는 사용할 수 없습니다.");
            }

            if (now.isAfter(verifyExpire)) {
                return response.fail("인증 가능한 시간이 만료되었습니다.");
            }
            smsAuth.setVerifyCode(Encrypt.getSha2bit256(certSms.getPhoneNumber() + certSms.getCode() + System.currentTimeMillis()));
            smsAuthRepository.save(smsAuth);

            MembersResDto.CertSmsSuccess certSmsSuccess = new MembersResDto.CertSmsSuccess();
            certSmsSuccess.setPhoneNumber(certSms.getPhoneNumber());
            certSmsSuccess.setVerifyCode(smsAuth.getVerifyCode());
            return response.success(certSmsSuccess);
        }
        return response.fail("휴대폰 인증에 실패했습니다.");
    }

    public ResponseEntity<?> login(MembersReqDto.Login login) {

        if (membersRepository.findByEmail(login.getEmail()).orElse(null) == null) {
            return response.fail("해당하는 유저가 존재하지 않습니다.");
        }

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        MembersResDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> reissue(MembersReqDto.Reissue reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User email 를 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("잘못된 요청입니다.");
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.");
        }

        // 4. 새로운 토큰 생성
        MembersResDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(MembersReqDto.Logout logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다.");
    }
}
