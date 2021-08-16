package com.project.hss.controller;

import com.project.hss.domain.dto.Response;
import com.project.hss.domain.dto.UserRequestDto;
import com.project.hss.lib.Common;
import com.project.hss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final Response response;
    private final Common common;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated UserRequestDto.Signup userSignup, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return response.invalidFields(common.refineErrors(errors));
        }
        return userService.signUp(userSignup);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(UserRequestDto.Login userLogin) {
        return userService.login(userLogin);
    }
}
