package com.ecomics.order_service.controller;

import com.ecomics.order_service.entity.Order;
import com.ecomics.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService service;

    // USER places order (secured at Gateway)
    @Operation(summary = "Place a new order")
    @PostMapping
    public Order placeOrder(
            @RequestBody Order order,
            @RequestHeader(value = "X-USER-NAME", required = false) String username) {

        if (username != null) {
            order.setUsername(username);
        }

        return service.placeOrder(order);
    }

    // USER sees own orders
    @Operation(summary = "Get my orders")
    @GetMapping("/my")
    public List<Order> myOrders(
            @RequestHeader(value = "X-USER-NAME", required = false) String username) {

        return service.getOrdersByUser(username);
    }

    // ADMIN sees all orders
    @Operation(summary = "Get all orders")
    @GetMapping
    public List<Order> allOrders() {
        return service.getAllOrders();
    }





    @GetMapping("/hello")
    public String hello() {
        return "Hello from ORDER-SERVICE";
    }


}
