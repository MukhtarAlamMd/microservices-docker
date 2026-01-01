package com.ecomics.api_gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback/users")
    public ResponseEntity<Map<String, Object>> userFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "USER-SERVICE is currently unavailable",
                        "status", 503
                ));
    }

    @GetMapping("/fallback/products")
    public ResponseEntity<Map<String, Object>> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "PRODUCT-SERVICE is currently unavailable",
                        "status", 503
                ));
    }

    @GetMapping("/fallback/orders")
    public ResponseEntity<Map<String, Object>> orderFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "message", "ORDER-SERVICE is currently unavailable",
                        "status", 503
                ));
    }
}
