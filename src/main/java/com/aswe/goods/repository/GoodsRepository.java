package com.aswe.goods.repository;

import com.aswe.goods.model.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String>, QuerydslPredicateExecutor<Goods> {
}