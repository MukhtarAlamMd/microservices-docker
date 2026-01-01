package com.ecomics.order_service.service;

import com.ecomics.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);

    List<Order> getOrdersByUser(String username);

    List<Order> getAllOrders();
}
