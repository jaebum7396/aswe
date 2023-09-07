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
import java.util.*;
import java.util.stream.Collectors;

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
        Order order = orderRepository.findById(orderCd).orElseThrow(() -> new NotFoundException("주문이 존재하지 않습니다."));
        resultMap.put("order", order);
        return resultMap;
    }

    public Map<String, Object> createOrder(HttpServletRequest request, CreateOrderRequest createOrderRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        String userCd = claim.getSubject();

        ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        Optional<Coupon> applicateCouponOpt = Optional.empty();
        Coupon applicateCoupon = null;
        boolean goodsCouponFlag = false;
        Goods applicableGoods = null;
        if (createOrderRequest.getCouponCd() != null) { //요청에 쿠폰코드가 있을때
            applicateCouponOpt = couponRepository.findById(createOrderRequest.getCouponCd());
            applicateCoupon = applicateCouponOpt.orElseThrow(() -> new NotFoundException("적용하려는 쿠폰이 존재하지 않습니다."));
            Optional<Goods> applicableGoodsOpt = Optional.ofNullable(applicateCoupon.getApplicableGoods());
            if (applicableGoodsOpt.isPresent()) { //특정 상품 한정 (특정 상품의 모든 개수에 적용) 쿠폰
                goodsCouponFlag = true;
                applicableGoods = applicableGoodsOpt.get();
            }
        }

        List<OrderDetailDTO> orderDetailDTOList = createOrderRequest.getOrderDetailList().stream()
            .filter(orderDetailDTO -> orderDetailDTO.getQuantity() > 0) //유효한 orderDetail(금액이 0원 이상인 것)
            .collect(Collectors.toList());

        BigDecimal totalGoodsPrice = new BigDecimal(0);
        BigDecimal totalDiscountPrice = new BigDecimal(0);
        BigDecimal totalPayPrice = new BigDecimal(0);
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            Goods goods = goodsRepository.getGoods(orderDetailDTO.getGoodsCd())
                    .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다."));
            BigDecimal goodsPrice = goods.getGoodsPrices().get(0).getGoodsPrice().multiply(new BigDecimal(orderDetailDTO.getQuantity()));
            BigDecimal discountPrice = new BigDecimal(0);
            BigDecimal payPrice = new BigDecimal(0);

            OrderDetail.OrderDetailBuilder orderDetailBuilder = OrderDetail.builder()
                    .goods(goods)
                    .quantity(orderDetailDTO.getQuantity());

            if(goodsCouponFlag){ //특정 상품 한정 쿠폰이 존재하는 경우
                if(applicableGoods.getGoodsCd().equals(orderDetailDTO.getGoodsCd())){//쿠폰이 적용되는 상품인 경우
                    if(applicateCoupon.getCouponType().equals(String.valueOf(CouponType.RATE))){ //퍼센트 쿠폰
                        discountPrice = goodsPrice.multiply(applicateCoupon.getDiscount()).divide(new BigDecimal(100));
                    }else if(applicateCoupon.getCouponType().equals(String.valueOf(CouponType.FIX))){ //고정 쿠폰
                        discountPrice = applicateCoupon.getDiscount();
                    }
                    orderDetailBuilder.coupon(applicateCoupon); //orderDetail에 쿠폰 추가
                }
            }

            payPrice = goodsPrice.subtract(discountPrice);
            orderDetailBuilder
                    .goodsPrice(goodsPrice)
                    .discountPrice(discountPrice)
                    .payPrice(payPrice)
                    .insertUserCd(userCd)
                    .deleteYn("N");
            orderDetailList.add(orderDetailBuilder.build()); //orderDetailList에 추가

            totalGoodsPrice = totalGoodsPrice.add(goodsPrice);
            totalDiscountPrice = totalDiscountPrice.add(discountPrice);
            totalPayPrice = totalPayPrice.add(payPrice);
            //System.out.println("totalGoodsPrice : " + totalGoodsPrice);
            //System.out.println("totalDiscountPrice : " + totalDiscountPrice);
            //System.out.println("totalPayPrice : " + totalPayPrice);
        }

        Order.OrderBuilder orderBuilder = Order.builder()
                .orderDetails(orderDetailList)
                .deliveryPrice(createOrderRequest.getDeliveryPrice())
                .insertUserCd(userCd)
                .deleteYn("N");

        if(applicateCouponOpt.isPresent()&&!goodsCouponFlag){ //쿠폰이 존재하고, 특정 상품 한정 쿠폰이 아닌 경우
            if(applicateCoupon.getCouponType().equals(String.valueOf(CouponType.RATE))){ //퍼센트 쿠폰
                totalDiscountPrice = totalGoodsPrice.multiply(applicateCoupon.getDiscount()).divide(new BigDecimal(100));
            }else if(applicateCoupon.getCouponType().equals(String.valueOf(CouponType.FIX))){ //고정 쿠폰
                totalDiscountPrice = applicateCoupon.getDiscount();
            }
            totalPayPrice = totalGoodsPrice.subtract(totalDiscountPrice);
            orderBuilder.coupon(applicateCoupon);
        }

        orderBuilder
                .totalGoodsPrice(totalGoodsPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .totalPayPrice(totalPayPrice.add(createOrderRequest.getDeliveryPrice()));

        Order order = orderRepository.save(orderBuilder.build()); //order 저장
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