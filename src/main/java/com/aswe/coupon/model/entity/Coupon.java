package com.aswe.coupon.model.entity;

import com.aswe.common.model.BaseEntity;
import com.aswe.coupon.model.dto.CouponType;
import com.aswe.goods.model.entity.Goods;
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
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "COUPON_CD")
    private String couponCd;

    @Column(name = "COUPON_NM",nullable = true) @ColumnDefault("1")
    private String couponNm;

    @Column(name = "COUPON_TYPE",nullable = false) @ColumnDefault("1")
    private String couponType;

    //couponType이 "rate" 일 경우 비율 / "fix" 일 경우 고정값
    @Column(name = "DISCOUNT",nullable = false) @ColumnDefault("1")
    private BigDecimal discount;

    //적용 가능한 상품(없을 시에는 주문 전체에 적용)
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "GOODS_CD")
    private Goods applicableGoods;
}