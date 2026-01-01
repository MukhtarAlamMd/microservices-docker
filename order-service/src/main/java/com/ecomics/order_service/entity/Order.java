package com.ecomics.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // From Gateway Header
    @NotNull
    private String username;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double totalPrice;

    private LocalDateTime orderDate;
}
