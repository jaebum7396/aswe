package com.aswe.goods.model.entity;

import com.aswe.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_GOODS_PRICE")
public class GoodsPrice extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "GOODS_PRICE_CD")
    @JsonIgnore
    private String goodsPriceCd;

    // 상품 가격
    @Column(name = "GOODS_PRICE", nullable = true)
    private BigDecimal goodsPrice;

    // 현재가 여부를 나타내는 필드
    @Column(name = "CURRENT_PRICE_YN") // 현재가 여부
    @JsonIgnore
    private String currentPriceYn;

    // 이 가격이 속한 상품을 나타내는 연관 관계 (Lazy 로딩)
    @ManyToOne
    @JoinColumn(name = "GOODS_CD")
    @JsonIgnore
    private Goods goods;
}