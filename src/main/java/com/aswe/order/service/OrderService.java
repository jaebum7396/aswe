package com.aswe.order.service;

import com.aswe.common.CommonUtils;
import com.aswe.common.exception.CalculateConsistencyException;
import com.aswe.common.exception.NotFoundException;
import com.aswe.coupon.model.dto.CouponType;
import com.aswe.coupon.model.entity.Coupon;
import com.aswe.coupon.repository.CouponRepository;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    @Autowired OrderRepository orderRepository;
    @Autowired GoodsRepository goodsRepository;
    @Autowired CouponRepository couponRepository;
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
        BigDecimal totalGoodsPrice = new BigDecimal(0);

        Optional<Goods> applicableGoodsOpt = Optional.empty();
        boolean specificGoodsCoupon = false;
        if(createOrderRequest.getCouponCd()!= null){
            Coupon applicateCoupon = couponRepository.findById(createOrderRequest.getCouponCd()).orElseThrow(() -> new NotFoundException("적용하려는 쿠폰이 존재하지 않습니다."));
            applicableGoodsOpt = Optional.ofNullable(applicateCoupon.getApplicableGoods());
            if(applicableGoodsOpt.isPresent()){// 특정 상품 한정 (특정 상품의 모든 개수에 적용) 쿠폰
                specificGoodsCoupon = true;
            }
        }

        for (OrderDetailDTO orderDetailDTO : createOrderRequest.getOrderDetailList()) {
            if (orderDetailDTO.getQuantity() < 0) {
                continue;
            }
            Goods goods = goodsRepository.findById(orderDetailDTO.getGoodsCd()).orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다."));

            BigDecimal goodsPrice = goods.getGoodsPrices().get(0).getGoodsPrice().multiply(new BigDecimal(orderDetailDTO.getQuantity()));

            OrderDetail.OrderDetailBuilder orderDetailBuilder = OrderDetail.builder()
                    .goods(Goods.builder().goodsCd(orderDetailDTO.getGoodsCd()).build())
                    .quantity(orderDetailDTO.getQuantity())
                    .goodsPrice(goods.getGoodsPrices().get(0).getGoodsPrice().multiply(new BigDecimal(orderDetailDTO.getQuantity()))) //해당 상품 가격의 합(상품x수량)
                    .insertUserCd(userCd);
            if(specificGoodsCoupon){ //특정 상품 한정 쿠폰이 있다면
                if(applicableGoodsOpt.get().getGoodsCd().equals(orderDetailDTO.getGoodsCd())){

                }
            }
            orderDetailList.add(orderDetailBuilder.build());

            totalGoodsPrice = totalGoodsPrice.add(goods.getGoodsPrices().get(0).getGoodsPrice().multiply(new BigDecimal(orderDetailDTO.getQuantity())));
        }
        Order.OrderBuilder orderBuilder = Order.builder()
                .orderDetails(orderDetailList)
                .deliveryPrice(createOrderRequest.getDeliveryPrice())
                .insertUserCd(userCd);

        if(createOrderRequest.getCouponCd()!= null){
            Coupon applicateCoupon = couponRepository.findById(createOrderRequest.getCouponCd()).orElseThrow(() -> new NotFoundException("적용하려는 쿠폰이 존재하지 않습니다."));
            Optional<Goods> applicableGoodsOpt = Optional.ofNullable(applicateCoupon.getApplicableGoods());
            if(applicableGoodsOpt.isPresent())

            if(applicateCoupon.getCouponType().equals(String.valueOf(CouponType.FIX))){
                if(totalGoodsPrice.compareTo(applicateCoupon.getDiscount())<0){
                    throw new CalculateConsistencyException("할인 금액이 주문 가격보다 큽니다.");
                }
            }
            if(coupon.getCouponType().equals(String.valueOf(CouponType.RATE))){
                if(coupon.getDiscount().compareTo(new BigDecimal(100))>0){
                    throw new CalculateConsistencyException("할인율이 100%를 초과합니다.");
                }else if(coupon.getDiscount().compareTo(new BigDecimal(0))<0){
                    throw new CalculateConsistencyException("할인율이 0% 미만입니다.");
                }
            }
            orderBuilder.coupon(applicateCoupon);
        }

        Order order = orderBuilder.build();
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