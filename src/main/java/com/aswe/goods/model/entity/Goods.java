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

    @Column( name = "GOODS_NM")
    private String goodsNm;

    @OneToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.ALL) @Builder.Default
    private List<GoodsPrice> goodsPrices = new ArrayList<>();

    public void addGoodsPrice(GoodsPrice goodsPrice) {
        this.goodsPrices.add(goodsPrice);
    }
}