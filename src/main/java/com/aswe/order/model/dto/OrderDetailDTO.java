package com.aswe.order.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class OrderDetailDTO {
    // 상품 코드
    @ApiModelProperty(example="1")
    private String goodsCd;

    // 주문 수량
    @ApiModelProperty(example="1")
    private int quantity;
}