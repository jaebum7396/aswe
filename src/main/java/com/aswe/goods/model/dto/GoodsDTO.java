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
    // 상품 이름
    @ApiModelProperty(value = "goodsNm", example = "test_goods")
    private String goodsNm;

    // 상품 가격
    @ApiModelProperty(value = "price", example = "12300")
    private BigDecimal price;

    // GoodsDTO 객체를 Goods 엔티티로 변환하는 메서드
    public Goods toEntity() {
        // Goods 엔티티를 생성하고 상품 이름을 설정
        Goods goods = Goods.builder()
                .goodsNm(goodsNm)
                .build();

        // 상품 가격을 GoodsPrice 엔티티로 추가하고 Goods 엔티티에 연결
        goods.addGoodsPrice(GoodsPrice.builder()
                .goods(goods)
                .goodsPrice(price)
                .build());
        return goods;
    }
}