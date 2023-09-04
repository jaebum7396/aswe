package com.aswe.coupon.model;

import com.aswe.common.model.BaseEntity;
import com.aswe.goods.model.Goods;
import com.aswe.goods.model.GoodsPrice;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity(name = "TB_COUPON")
public class Coupon extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "COUPON_CD")
    private String couponCd;

    @Column(name = "COUPON_TYPE",nullable = true) @ColumnDefault("1")
    private String couponType;

    @ManyToOne
    private Goods goods;
}