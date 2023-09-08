package com.aswe.order.model.entity;

import com.aswe.common.model.BaseEntity;
import com.aswe.coupon.model.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_ORDER")
public class Order extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "ORDER_CD")
    private String orderCd;

    // 주문 상세 정보 목록 (Lazy 로딩)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    @JoinColumn(name = "ORDER_CD")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // 배송 비용
    @Column( name = "DELIVERY_PRICE") @Builder.Default
    private BigDecimal deliveryPrice = new BigDecimal("3000");

    // 주문서의 모든 상품 가격의 합
    @Column(name = "TOTAL_GOODS_PRICE")
    private BigDecimal totalGoodsPrice;

    // 할인 금액
    @Column(name = "TOTAL_DISCOUNT_PRICE")
    private BigDecimal totalDiscountPrice;

    //실제 결제해야되는 금액(모든 상품 가격의 합 - 할인 금액 + 배송비 와 일치)
    @Column( name = "TOTAL_PAY_PRICE")
    private BigDecimal totalPayPrice;

    // 쿠폰 정보 (Lazy 로딩, null인 경우 필드를 제외)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Coupon coupon;
}