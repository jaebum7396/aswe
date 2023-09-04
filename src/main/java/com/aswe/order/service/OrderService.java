package com.aswe.order.service;

import com.aswe.order.model.Order;
import com.aswe.order.repository.OrderRepository;
import com.aswe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Map<String, Object> searchOrder(String orderCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> searchOrderPrice(String orderCd, LocalDateTime insertDT) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> createOrder(Order order) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> updateOrder(Order order) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> deleteOrder(Order order) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }
}