package com.aswe.goods.model.entity;

import com.aswe.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_GOODS")
public class Goods extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "GOODS_CD")
    @JsonIgnore
    private String goodsCd;

    // 상품 이름
    @Column( name = "GOODS_NM")
    private String goodsNm;

    // 상품 가격 목록 (Lazy 로딩)
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    private List<GoodsPrice> goodsPrices = new ArrayList<>();

    // GoodsPrice 객체를 상품에 추가하는 메서드
    public void addGoodsPrice(GoodsPrice goodsPrice) {
        this.goodsPrices.add(goodsPrice);
    }
}