package com.aswe.order.repository;

import com.aswe.order.model.entity.Order;

import java.util.Optional;

public interface OrderRepositoryQ {
    Optional<Order> getOrder(String orderCd);
}
