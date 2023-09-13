package com.aswe.coupon.controller;

import com.aswe.common.CommonUtils;
import com.aswe.coupon.model.dto.CreateCouponRequest;
import com.aswe.coupon.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "CouponController")
@Tag(name = "CouponController", description = "쿠폰 컨트롤러")
@RestController
@RequiredArgsConstructor
public class CouponController {
    @Autowired CouponService couponService;
    @Autowired CommonUtils commonUtils;
    @PostMapping(value = "/coupon")
    @Operation(summary="쿠폰 생성 요청을 처리하는 엔드포인트", description="쿠폰 생성 API - MART 권한이 필요합니다.")
    public ResponseEntity createCoupon(HttpServletRequest request, @RequestBody CreateCouponRequest createCouponRequest) throws Exception {
        return commonUtils.okResponsePackaging(couponService.createCoupon(request, createCouponRequest));
    }
}