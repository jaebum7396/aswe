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
    @ApiModelProperty(example="1")
    private String goodsCd;
    @ApiModelProperty(example="1")
    private int quantity;
}