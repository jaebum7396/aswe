package com.aswe.goods.controller;

import com.aswe.common.CommonUtils;
import com.aswe.goods.model.Goods;
import com.aswe.goods.service.GoodsService;
import com.aswe.user.model.SignupRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity searchGoods(@RequestParam String goodsCd) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.searchGoods(goodsCd));
    }
    @GetMapping(value = "/goods/price")
    @Operation(summary="상품 조회", description="상품 조회 API")
    public ResponseEntity searchGoods(@RequestParam String goodsCd, ) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.searchGoods(goods));
    }
    @PostMapping(value = "/goods")
    @Operation(summary="상품 생성", description="상품 생성 API")
    public ResponseEntity createGoods(@RequestBody Goods goods) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.createGoods(goods));
    }
    @PutMapping(value = "/goods")
    @Operation(summary="상품 생성", description="상품 생성 API")
    public ResponseEntity updateGoods(@RequestBody Goods goods) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.updateGoods(goods));
    }
    @DeleteMapping(value = "/goods")
    @Operation(summary="상품 생성", description="상품 생성 API")
    public ResponseEntity deleteGoods(@RequestBody Goods goods) throws Exception {
        return commonUtils.okResponsePackaging(goodsService.deleteGoods(goods));
    }
}