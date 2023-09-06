package com.aswe.goods.controller;

import com.aswe.common.CommonUtils;
import com.aswe.goods.model.dto.CreateGoodsRequest;
import com.aswe.goods.model.dto.UpdateGoodsRequest;
import com.aswe.goods.service.GoodsService;
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
@Api(tags = "GoodsController")
@Tag(name = "GoodsController", description = "상품 컨트롤러")
@RestController
@RequiredArgsConstructor
public class GoodsController {
    @Autowired GoodsService goodsService;
    @Autowired CommonUtils commonUtils;
    @GetMapping(value = "/goods")
    @Operation(summary="상품 조회", description="상품 조회 API")
    public ResponseEntity getGoods(@RequestParam String goodsCd) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.getGoods(goodsCd));
    }
    @GetMapping(value = "/goods/price")
    @Operation(summary="상품 금액 조회", description="상품 금액 조회 API")
    public ResponseEntity getGoodsPrice(@RequestParam String goodsCd, @RequestParam String insertDT) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.getGoodsPrice(goodsCd, insertDT));
    }
    @PostMapping(value = "/goods")
    @Operation(summary="상품 생성", description="상품 생성 API")
    public ResponseEntity createGoods(HttpServletRequest request, @RequestBody CreateGoodsRequest createGoodsRequest) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.createGoods(request, createGoodsRequest));
    }
    @PutMapping(value = "/goods")
    @Operation(summary="상품 수정", description="상품 수정 API")
    public ResponseEntity updateGoods(HttpServletRequest request, @RequestBody UpdateGoodsRequest updateGoodsRequest) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.updateGoods(request, updateGoodsRequest));
    }
    @DeleteMapping(value = "/goods")
    @Operation(summary="상품 삭제", description="상품 삭제 API")
    public ResponseEntity deleteGoods(HttpServletRequest request, @RequestParam String goodsCd) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.deleteGoods(request, goodsCd));
    }
}