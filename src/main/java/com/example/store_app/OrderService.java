package com.example.store_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }
}