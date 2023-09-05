package com.aswe.goods.model.dto;

import com.aswe.goods.model.entity.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateGoodsRequest extends GoodsRequest {
    @ApiModelProperty(value="goodsCd", example="000000-0000-0000-0000-000000000000", required=true)
    private String goodsCd;

    public Goods toEntity() {
        Goods goods = super.toEntity();
        goods.setGoodsCd(goodsCd);
        return goods;
    }
}