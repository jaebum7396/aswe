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
public class CreateGoodsRequest extends GoodsDTO { // CreateGoodsRequest 클래스는 GoodsDTO 클래스를 확장합니다.
    // CreateGoodsRequest 객체를 Goods 엔티티로 변환하는 메서드
    public Goods toEntity() {
        // 상속받은 슈퍼 클래스인 GoodsDTO의 toEntity() 메서드를 호출하여 Goods 엔티티를 생성합니다.
        Goods goods = super.toEntity();
        return goods;
    }
}