package com.aswe.order.service;

import com.aswe.common.CommonUtils;
import com.aswe.common.exception.GoodsNotFoundException;
import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.repository.GoodsRepository;
import com.aswe.order.model.dto.CreateOrderRequest;
import com.aswe.order.model.dto.OrderDetailDTO;
import com.aswe.order.model.entity.Order;
import com.aswe.order.model.entity.OrderDetail;
import com.aswe.order.repository.OrderRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    @Autowired OrderRepository orderRepository;
    @Autowired GoodsRepository goodsRepository;
    @Autowired CommonUtils commonUtils;

    public Map<String, Object> searchOrder(String orderCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> searchOrderPrice(String orderCd, LocalDateTime insertDT) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> createOrder(HttpServletRequest request, CreateOrderRequest createOrderRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        String userCd = claim.getSubject();

        ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        for (OrderDetailDTO orderDetailDTO : createOrderRequest.getOrderDetailList()) {
            if (orderDetailDTO.getQuantity() < 0) {
                continue;
            }
            goodsRepository.findById(orderDetailDTO.getGoodsCd()).orElseThrow(() -> new GoodsNotFoundException("상품이 존재하지 않습니다."));
            OrderDetail orderDetail = OrderDetail.builder()
                    .goods(Goods.builder().goodsCd(orderDetailDTO.getGoodsCd()).build())
                    .quantity(orderDetailDTO.getQuantity())
                    .insertUserCd(userCd)
                    .build();
            orderDetailList.add(orderDetail);
        }
        Order order = Order.builder()
                .orderDetails(orderDetailList)
                .deliveryPrice(createOrderRequest.getDeliveryPrice())
                .insertUserCd(userCd)
                .build();

        order = orderRepository.save(order);
        resultMap.put("order", order);
        return resultMap;
    }

    public Map<String, Object> updateOrder(Order order) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> deleteOrder(Order order) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }
}