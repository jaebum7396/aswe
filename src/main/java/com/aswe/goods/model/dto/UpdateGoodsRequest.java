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
public class UpdateGoodsRequest extends GoodsDTO {
    @ApiModelProperty(value="goodsCd", example="2", required=true)
    private String goodsCd;

    public Goods toEntity() {
        Goods goods = super.toEntity();
        goods.setGoodsCd(goodsCd);
        return goods;
    }
}