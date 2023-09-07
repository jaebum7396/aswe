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
@Tag(name = "CouponController", description = "상품 컨트롤러")
@RestController
@RequiredArgsConstructor
public class CouponController {
    @Autowired CouponService couponService;
    @Autowired CommonUtils commonUtils;
    /*
    @GetMapping(value = "/coupon")
    @Operation(summary="상품 조회", description="상품 조회 API")
    public ResponseEntity getCoupon(@RequestParam String CouponCd) throws Exception {
        return commonUtils.okResponsePackaging(couponService.getCoupon(couponCd));
    }
    */
    @PostMapping(value = "/coupon")
    @Operation(summary="쿠폰 생성", description="쿠폰 생성 API")
    public ResponseEntity createCoupon(HttpServletRequest request, @RequestBody CreateCouponRequest createCouponRequest) throws Exception {
        return commonUtils.okResponsePackaging(couponService.createCoupon(request, createCouponRequest));
    }
    /*
    @PutMapping(value = "/coupon")
    @Operation(summary="상품 수정", description="상품 수정 API")
    public ResponseEntity updateCoupon(HttpServletRequest request, @RequestBody UpdateCouponRequest updateCouponRequest) throws Exception {
        return commonUtils.okResponsePackaging(couponService.updateCoupon(request, updateCouponRequest));
    }
    @DeleteMapping(value = "/coupon")
    @Operation(summary="상품 삭제", description="상품 삭제 API")
    public ResponseEntity deleteCoupon(HttpServletRequest request, @RequestParam String couponCd) throws Exception {
        return commonUtils.okResponsePackaging(couponService.deleteCoupon(request, couponCd));
    }
    */
}