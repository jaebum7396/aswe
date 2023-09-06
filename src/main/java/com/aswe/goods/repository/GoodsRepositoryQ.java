package com.aswe.goods.repository;

import com.aswe.goods.model.entity.Goods;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GoodsRepositoryQ {
    Optional<Goods> getGoods(String goodsCd);
    Optional<Goods> getGoodsPrice(String goodsCd, String insertDT);
}
