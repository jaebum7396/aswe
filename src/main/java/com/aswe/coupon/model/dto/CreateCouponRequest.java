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
    @ApiModelProperty(example="블랙 프라이데이 할인쿠폰", required = false)
    private String couponNm;

    @ApiModelProperty(example="enum(FIX,RATE)", required = true)
    private CouponType couponType;

    //couponType이 "rate" 일 경우 비율 / "fix" 일 경우 고정값
    @ApiModelProperty(example="20", required = true)
    private BigDecimal discount;

    //적용 가능한 상품(없을 시에는 주문 전체에 적용)
    @ApiModelProperty(example="1", required = false)
    private String goodsCd;

    public Coupon toEntity() {
        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponType(this.couponType.toString())
                .discount(this.discount);
        if(!this.couponNm.isEmpty()){
            couponBuilder.couponNm(this.couponNm);
        }
        return couponBuilder.build();
    }
}