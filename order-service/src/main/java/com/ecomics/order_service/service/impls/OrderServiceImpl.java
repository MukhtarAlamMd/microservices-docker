package com.ecomics.order_service.service.impls;

import com.ecomics.order_service.entity.Order;
import com.ecomics.order_service.repository.OrderRepository;
import com.ecomics.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        return repository.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
