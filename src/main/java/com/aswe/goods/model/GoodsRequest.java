package com.aswe.goods.model;

import com.aswe.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsRequest {
    @ApiModelProperty(value="goodsNm", example="mart", required=true)
    private String goodsNm;

    @ApiModelProperty(value="price", example="10000", required=true)
    private BigDecimal price;

    public Goods toEntity() {
        Goods goods =Goods.builder()
                .goodsNm(goodsNm)
                .build();
        return goods;
    }
}