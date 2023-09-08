package com.aswe.user.controller;

import com.aswe.common.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aswe.user.model.dto.LoginRequest;
import com.aswe.user.model.dto.SignupRequest;
import com.aswe.user.service.AuthService;
import com.aswe.user.service.UserService;

@Slf4j
@Api(tags = "UserController")
@Tag(name = "UserController", description = "회원가입, 유저정보")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired UserService userService;
    @Autowired AuthService authService;
    @Autowired CommonUtils commonUtils;

    @PostMapping(value = "/signup")
    @Operation(summary="회원가입 엔드포인트", description="회원 가입 API")
    public ResponseEntity signup(@RequestBody SignupRequest signupRequest) throws Exception {
        return commonUtils.okResponsePackaging(userService.signup(signupRequest));
    }
    @PostMapping(value = "/login")
    @Operation(summary="로그인 엔드포인트", description="가입한 회원을 로그인 하는 API")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {
        return commonUtils.okResponsePackaging(authService.generateToken(loginRequest));
    }
}