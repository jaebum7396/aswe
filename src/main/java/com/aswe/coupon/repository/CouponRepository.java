package com.aswe.coupon.repository;

import com.aswe.coupon.model.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String>, QuerydslPredicateExecutor<Coupon> {
}