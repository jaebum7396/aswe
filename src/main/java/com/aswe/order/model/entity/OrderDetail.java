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
    @Column( name = "ORDER_DETAIL_CD")
    private String orderDetailCd;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "GOODS_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL) // null인 경우 필드를 제외
    private Goods goods;

    @Column( name = "QUANTITY")
    private int quantity;

    @Column( name = "GOODS_PRICE") //해당 상품 가격의 합(상품x수량)
    private BigDecimal goodsPrice;

    @Column( name = "DISCOUNT_PRICE") @Builder.Default //할인 금액
    private BigDecimal discountPrice = new BigDecimal(0);

    @Column( name = "PAY_PRICE") //실제 결제해야되는 금액(해당 상품 가격의 합 - 할인 금액)
    private BigDecimal payPrice;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "COUPON_CD")
    @JsonInclude(JsonInclude.Include.NON_NULL) // null인 경우 필드를 제외
    private Coupon coupon;
}