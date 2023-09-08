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
    // 상품 코드 (필수)
    @ApiModelProperty(value = "goodsCd", example = "2", required = true)
    private String goodsCd;

    // UpdateGoodsRequest 객체를 Goods 엔티티로 변환하는 메서드
    public Goods toEntity() {
        // 상속받은 슈퍼 클래스인 GoodsDTO의 toEntity() 메서드를 호출하여 Goods 엔티티를 생성합니다.
        Goods goods = super.toEntity();
        // 상품 코드를 설정합니다.
        goods.setGoodsCd(goodsCd);

        return goods;
    }
}