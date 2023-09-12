package com.aswe.coupon.model.dto;

import com.aswe.coupon.model.entity.Coupon;
import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.model.entity.GoodsPrice;
import com.aswe.order.model.dto.OrderDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateCouponRequest {
    // 쿠폰 이름 (선택 사항)
    @ApiModelProperty(example="블랙 프라이데이 할인쿠폰", required = false)
    private String couponNm;

    // 쿠폰 유형 (FIX 또는 RATE, 필수)
    @ApiModelProperty(example="enum(FIX,RATE)", required = true)
    private CouponType couponType;

    // 할인 금액 또는 할인 비율 (필수)
    // couponType이 "rate"인 경우 비율 (예: 20)
    // couponType이 "fix"인 경우 고정 금액 (예: 20000)
    @ApiModelProperty(example="20", required = true)
    private BigDecimal discount;

    // 적용 가능한 상품 코드 (선택 사항, 없을 시 주문 전체에 적용)
    @ApiModelProperty(example="1", required = false)
    private String goodsCd;

    // CreateCouponRequest 객체를 Coupon 엔티티로 변환하는 메서드
    public Coupon toEntity() {
        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponType(this.couponType.toString())
                .discount(this.discount);
        if (!this.couponNm.isEmpty()) {
            couponBuilder.couponNm(this.couponNm);
        }
        return couponBuilder.build();
    }
}