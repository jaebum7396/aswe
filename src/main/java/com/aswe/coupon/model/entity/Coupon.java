package com.aswe.coupon.model.entity;

import com.aswe.common.model.BaseEntity;
import com.aswe.coupon.model.dto.CouponType;
import com.aswe.goods.model.entity.Goods;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_COUPON")
public class Coupon extends BaseEntity implements Serializable {
    // 쿠폰 고유 식별자(UUID)
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "COUPON_CD")
    private String couponCd;

    // 쿠폰 이름 (선택 사항)
    @Column(name = "COUPON_NM", nullable = true)
    private String couponNm;

    // 쿠폰 유형 (필수)
    @Column(name = "COUPON_TYPE", nullable = false)
    private String couponType;

    // 할인 금액 또는 할인 비율 (필수, 기본값 "1")
    // couponType이 "rate"인 경우 비율 (예: 20은 20% 할인을 나타냄)
    // couponType이 "fix"인 경우 고정 금액 (예: 20000)
    @Column(name = "DISCOUNT", nullable = false)
    private BigDecimal discount;

    // 적용 가능한 상품 (없을 시 주문 전체에 적용, Lazy 로딩)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_CD")
    @JsonIgnore
    private Goods applicableGoods;
}