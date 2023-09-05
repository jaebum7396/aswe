package com.aswe.goods.model.dto;

import com.aswe.goods.model.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateGoodsRequest extends GoodsRequest {
    public Goods toEntity() {
        Goods goods = super.toEntity();
        return goods;
    }
}