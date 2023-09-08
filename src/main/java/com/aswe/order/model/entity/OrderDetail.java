package com.aswe.order.model.entity;

import com.aswe.common.model.BaseEntity;
import com.aswe.coupon.model.entity.Coupon;
import com.aswe.goods.model.entity.Goods;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_ORDER_DETAIL")
public class OrderDetail extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ORDER_DETAIL_CD")
    private String orderDetailCd;

    // 주문 상세 정보에 해당하는 상품 (Lazy 로딩, null인 경우 필드를 제외)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Goods goods;

    // 상품 수량
    @Column(name = "QUANTITY")
    private int quantity;

    // 상품 가격 (상품 가격 x 수량의 합)
    @Column(name = "GOODS_PRICE")
    private BigDecimal goodsPrice;

    // 할인 금액 (기본값 0)
    @Column(name = "DISCOUNT_PRICE")
    @Builder.Default
    private BigDecimal discountPrice = new BigDecimal(0);

    // 결제해야되는 금액 (해당 상품 가격의 합 - 할인 금액)
    @Column(name = "PAY_PRICE")
    private BigDecimal payPrice;

    // 쿠폰 정보 (Lazy 로딩, null인 경우 필드를 제외)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Coupon coupon;
}