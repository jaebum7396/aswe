package com.aswe.order.model.entity;

import com.aswe.common.model.BaseEntity;
import com.aswe.goods.model.entity.Goods;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_ORDER_DETAIL")
public class OrderDetail extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "ORDER_DETAIL_CD")
    private String orderDetailCd;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "GOODS_CD")
    private Goods goods;

    @Column( name = "QUANTITY")
    private int quantity;
}