package com.aswe.order.controller;

import com.aswe.common.CommonUtils;
import com.aswe.order.model.dto.CreateOrderRequest;
import com.aswe.order.model.entity.Order;
import com.aswe.order.service.OrderService;
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
@Api(tags = "OrderController")
@Tag(name = "OrderController", description = "주문 컨트롤러")
@RestController
@RequiredArgsConstructor
public class OrderController {
    @Autowired OrderService orderService;
    @Autowired CommonUtils commonUtils;
    @GetMapping(value = "/order")
    @Operation(summary="주문 조회 엔드포인트", description="주문 조회 API")
    public ResponseEntity searchOrder(@RequestParam String goodsCd) throws Exception {
        return commonUtils.okResponsePackaging(orderService.searchOrder(goodsCd));
    }
    @PostMapping(value = "/order")
    @Operation(summary="주문 생성 엔드포인트", description="주문 생성 API")
    public ResponseEntity createOrder(HttpServletRequest request, @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        return commonUtils.okResponsePackaging(orderService.createOrder(request, createOrderRequest));
    }
}