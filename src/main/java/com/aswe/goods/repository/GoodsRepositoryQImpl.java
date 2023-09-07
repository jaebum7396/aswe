package com.aswe.goods.repository;

import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.model.entity.QGoods;
import com.aswe.goods.model.entity.QGoodsPrice;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class GoodsRepositoryQImpl implements GoodsRepositoryQ {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Goods> getGoods(String goodsCd) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QGoods goods = QGoods.goods;
        QGoodsPrice goodsPrice = QGoodsPrice.goodsPrice1;

        // Subquery to find the most recent insertDt for the given goodsCd
        SubQueryExpression<String> subCurrentGoodsPriceCd = JPAExpressions
                .select(goodsPrice.goodsPriceCd)
                .from(goodsPrice)
                .where(goodsPrice.goods.goodsCd.eq(goodsCd))
                .where(goodsPrice.currentPriceYn.eq("Y"));

        Goods goodsEntity = queryFactory
                .selectFrom(goods)
                .leftJoin(goods.goodsPrices, goodsPrice).fetchJoin()
                .where(goods.goodsCd.eq(goodsCd))
                .where(goods.deleteYn.eq("N"))
                .where(goodsPrice.goodsPriceCd.eq(subCurrentGoodsPriceCd))
                .fetchFirst();

        return Optional.ofNullable(goodsEntity);
    }

    @Override
    public Optional<Goods> getGoodsPrice(String goodsCd, String insertDT) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QGoods goods = QGoods.goods;
        QGoodsPrice goodsPrice = QGoodsPrice.goodsPrice1;

        Goods goodsEntity = queryFactory
                .selectFrom(goods)
                .leftJoin(goods.goodsPrices, goodsPrice).fetchJoin()
                .where(goods.goodsCd.eq(goodsCd))
                .where(goods.deleteYn.eq("N"))
                .where(goodsPrice.insertDt.stringValue().like('%' + insertDT + '%'))
                .fetchFirst();

        return Optional.ofNullable(goodsEntity);
    }
}