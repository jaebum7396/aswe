package com.aswe.order.model.dto;

import com.aswe.goods.model.entity.Goods;
import com.aswe.order.model.entity.Order;
import com.aswe.order.model.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateOrderRequest {
    // 주문 상세 정보 목록
    ArrayList<OrderDetailDTO> orderDetailList;

    // 배송 비용
    @ApiModelProperty(example = "2500")
    private BigDecimal deliveryPrice;

    // 쿠폰 코드 (선택 사항)
    @ApiModelProperty(example = "1", required = false)
    private String couponCd;
}