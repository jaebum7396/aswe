package com.aswe.order.repository;

import com.aswe.coupon.model.entity.QCoupon;
import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.model.entity.QGoods;
import com.aswe.goods.model.entity.QGoodsPrice;
import com.aswe.goods.repository.GoodsRepositoryQ;
import com.aswe.order.model.entity.Order;
import com.aswe.order.model.entity.QOrder;
import com.aswe.order.model.entity.QOrderDetail;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class OrderRepositoryQImpl implements OrderRepositoryQ {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Order> getOrder(String orderCd) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QOrder order = QOrder.order;
        QOrderDetail orderDetail = QOrderDetail.orderDetail;

        Order orderEntity = queryFactory
                .selectFrom(order)
                .leftJoin(order.orderDetails, orderDetail).fetchJoin()
                .where(order.orderCd.eq(orderCd))
                .where(order.deleteYn.eq("N"))
                .fetchFirst();

        return Optional.ofNullable(orderEntity);
    }
}