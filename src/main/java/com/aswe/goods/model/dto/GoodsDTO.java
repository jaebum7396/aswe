package com.aswe.goods.model.dto;

import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.model.entity.GoodsPrice;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    @ApiModelProperty(value="goodsNm", example="test_goods3")
    private String goodsNm;
    @ApiModelProperty(value="price", example="10000")
    private BigDecimal price;

    public Goods toEntity() {
        Goods goods = Goods.builder()
                .goodsNm(goodsNm)
                .build();
        goods.addGoodsPrice(GoodsPrice.builder()
                .goods(goods)
                .goodsPrice(price)
                .build());
        return goods;
    }
}