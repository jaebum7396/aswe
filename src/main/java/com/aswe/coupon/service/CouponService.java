package com.aswe.coupon.service;

import com.aswe.common.CommonUtils;
import com.aswe.common.Constants.Constants;
import com.aswe.common.exception.CalculateConsistencyException;
import com.aswe.common.exception.NotFoundException;
import com.aswe.coupon.model.dto.CouponType;
import com.aswe.coupon.model.dto.CreateCouponRequest;
import com.aswe.coupon.model.entity.Coupon;
import com.aswe.coupon.repository.CouponRepository;
import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.repository.GoodsRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {
    @Autowired
    CouponRepository couponRepository;
    @Autowired GoodsRepository goodsRepository;
    @Autowired CommonUtils commonUtils;

    // 쿠폰 생성 메서드
    public Map<String, Object> createCoupon(HttpServletRequest request, CreateCouponRequest createCouponRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // JWT에서 클레임을 추출
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String, String>> roles = claim.get("roles", ArrayList.class);

        // 현재 유저가 'MART' 권한을 가졌는지 확인
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException(Constants.BAD_CREDENTIAL_MART));

        // CreateCouponRequest를 Coupon 엔티티로 변환
        Coupon coupon = createCouponRequest.toEntity();

        if (createCouponRequest.getGoodsCd() != null) {
            String goodsCd = createCouponRequest.getGoodsCd();
            Goods goods = goodsRepository.getGoods(goodsCd).orElseThrow(() -> new NotFoundException(Constants.PRODUCT_NOT_FOUND));

            if (coupon.getCouponType().equals(String.valueOf(CouponType.FIX))) {
                // 고정 할인 쿠폰의 경우 할인 금액이 상품 가격보다 큰지 확인
                if (goods.getGoodsPrices().get(0).getGoodsPrice().compareTo(coupon.getDiscount()) < 0) {
                    throw new CalculateConsistencyException("할인 금액이 상품 가격보다 큽니다.");
                }
            }

            coupon.setApplicableGoods(goods);
        }

        if (coupon.getCouponType().equals(String.valueOf(CouponType.RATE))) {
            // 할인율이 상품가액의 100%를 초과하는지 확인
            if (coupon.getDiscount().compareTo(new BigDecimal(100)) > 0) {
                throw new CalculateConsistencyException("할인율이 100%를 초과합니다.");
            } else if (coupon.getDiscount().compareTo(new BigDecimal(0)) < 0) {
                throw new CalculateConsistencyException("할인율이 0% 미만입니다.");
            }
        }

        // 쿠폰을 저장하고 결과를 resultMap에 추가
        coupon = couponRepository.save(coupon);
        resultMap.put("coupon", coupon);
        return resultMap;
    }
}