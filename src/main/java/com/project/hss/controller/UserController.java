package com.project.hss.controller;

import com.project.hss.domain.dto.Response;
import com.project.hss.domain.dto.UserRequestDto;
import com.project.hss.lib.Helper;
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

    /**
     * <p> 회원가입 </p>
     *
     * @param signUpDto
     * @param errors
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated UserRequestDto.SignUpDto signUpDto, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.signUp(signUpDto);
    }

    /**
     * <p> 로그인 </p>
     *
     * @param loginDto
     * @param errors
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated UserRequestDto.LoginDto loginDto, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.login(loginDto);
    }

    /**
     * <p> 토큰 갱신 </p>
     *
     * @param updateTokenDto
     * @param errors
     * @return
     */
    @PostMapping("/update-token")
    public ResponseEntity<?> updateToken(@Validated UserRequestDto.UpdateTokenDto updateTokenDto, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.updateToken(updateTokenDto);
    }
}
