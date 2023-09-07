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

    @Column(name = "GOODS_PRICE", nullable = true)
    private BigDecimal goodsPrice;

    @Column(name = "CURRENT_PRICE_YN", nullable = true) // 현재가 여부
    @JsonIgnore
    private String currentPriceYn;

    @ManyToOne
    @JoinColumn(name = "GOODS_CD")
    @JsonIgnore
    private Goods goods;
}