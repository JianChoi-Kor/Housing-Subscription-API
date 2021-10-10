package com.project.hss.api.v1.controller;

import com.project.hss.api.lib.Helper;
import com.project.hss.api.v1.dto.Response;
import com.project.hss.api.v1.dto.request.MembersReqDto;
import com.project.hss.api.v1.service.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "청약알림이 회원가입, 로그인, 로그아웃, 토큰 재발급")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MembersController {

    private final MembersService membersService;
    private final Response response;

    @Operation(summary = "회원가입", description = "청약알림이 사용자 회원가입", tags = "Member")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated MembersReqDto.SignUp signUp, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return membersService.signUp(signUp);
    }

    @Operation(summary = "로그인", description = "청약알림이 사용자 로그인", tags = "Member")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated MembersReqDto.Login login, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return membersService.login(login);
    }

    @Operation(summary = "토큰 갱신", description = "청약알림이 사용자 토큰 갱신", tags = "Member")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated MembersReqDto.Reissue reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return membersService.reissue(reissue);
    }

    @Operation(summary = "로그아웃", description = "청약알림이 사용자 로그아웃", tags = "Member")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated MembersReqDto.Logout logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return membersService.logout(logout);
    }
}
