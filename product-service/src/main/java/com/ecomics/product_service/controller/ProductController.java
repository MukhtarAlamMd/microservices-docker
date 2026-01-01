package com.ecomics.product_service.controller;

import com.ecomics.product_service.entity.Product;
import com.ecomics.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;


    // ADMIN ONLY
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product,
                          @RequestHeader("X-USER-ROLE") String role) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied");
        }
        return service.create(product);
    }

    // PUBLIC / USER
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ADMIN ONLY
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody Product product,
                          @RequestHeader("X-USER-ROLE") String role) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied");
        }
        return service.update(id, product);
    }

    // ADMIN ONLY
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("X-USER-ROLE") String role) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied");
        }
        service.delete(id);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from PRODUCT-SERVICE";
    }
}
