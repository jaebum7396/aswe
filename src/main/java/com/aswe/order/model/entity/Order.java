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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    @JoinColumn(name = "ORDER_CD")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Column( name = "DELIVERY_PRICE") @Builder.Default
    private BigDecimal deliveryPrice = new BigDecimal("3000");

    @Column( name = "TOTAL_GOODS_PRICE") //주문서의 모든 상품 가격의 합
    private BigDecimal totalGoodsPrice;
    
    @Column( name = "TOTAL_DISCOUNT_PRICE") //할인 금액
    private BigDecimal totalDiscountPrice;

    @Column( name = "TOTAL_PAY_PRICE") //실제 결제해야되는 금액(모든 상품 가격의 합 - 할인 금액 + 배송비 와 일치해야함. 일치하지 않으면 에러)
    private BigDecimal totalPayPrice;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "COUPON_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL) // null인 경우 필드를 제외
    private Coupon coupon;
}