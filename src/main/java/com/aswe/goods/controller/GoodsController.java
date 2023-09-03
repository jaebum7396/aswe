package com.aswe.goods.controller;

import com.aswe.common.CommonUtils;
import com.aswe.goods.service.GoodsService;
import com.aswe.user.model.SignupRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "GoodsController")
@Tag(name = "GoodsController", description = "상품 컨트롤러")
@RestController
@RequiredArgsConstructor
public class GoodsController {
    @Autowired GoodsService goodsService;
    @Autowired CommonUtils commonUtils;
    @GetMapping(value = "/goods")
    @Operation(summary="회원가입", description="회원 가입 API")
    public ResponseEntity goods(@RequestBody SignupRequest signupRequest) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.signup(signupRequest));
    }
}