package com.project.hss.service;

import com.project.hss.domain.dto.Response;
import com.project.hss.domain.dto.UserRequestDto;
import com.project.hss.domain.dto.UserResponseDto;
import com.project.hss.domain.entity.User;
import com.project.hss.repository.UserRepository;
import com.project.hss.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> signUp(UserRequestDto.SignUpDto signUpDto) {
        if (userRepository.findByEmail(signUpDto.getEmail()).orElse(null) != null) {
            return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(user);

        return response.success("회원가입에 성공했습니다.");
    }

    public ResponseEntity<?> login(UserRequestDto.LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);
        if(user == null) {
            response.fail("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            response.fail("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        UserResponseDto.TokenInfo tokenInfo = new UserResponseDto.TokenInfo(accessToken, refreshToken);

        return response.success(tokenInfo);
    }

    public ResponseEntity<?> updateToken(UserRequestDto.UpdateTokenDto updateTokenDto) {
        return response.success();
    }
}
