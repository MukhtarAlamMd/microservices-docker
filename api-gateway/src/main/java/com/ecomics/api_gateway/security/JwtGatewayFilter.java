package com.ecomics.api_gateway.security;

import com.ecomics.api_gateway.exception.GatewayErrorHandler;
import com.ecomics.api_gateway.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ Public APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return GatewayErrorHandler.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Missing Authorization header or Bearer token"
            );
        }

        String token = authHeader.substring(7);

        // ❌ Invalid / Expired token
        if (!jwtUtils.validateToken(token)) {
            return GatewayErrorHandler.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired JWT token"
            );
        }

        String role = jwtUtils.extractRole(token);

        // 🔒 ADMIN only
        if (path.startsWith("/admin") && !"ADMIN".equals(role)) {
            return GatewayErrorHandler.writeErrorResponse(
                    exchange,
                    HttpStatus.FORBIDDEN,
                    "Access denied: ADMIN role required"
            );
        }

        // 🔒 USER only
        if (path.startsWith("/orders") && !"USER".equals(role)) {
            return GatewayErrorHandler.writeErrorResponse(
                    exchange,
                    HttpStatus.FORBIDDEN,
                    "Access denied: USER role required"
            );
        }

        return chain.filter(exchange);
    }
}

//package com.ecomics.api_gateway.security;
//
//import com.ecomics.api_gateway.utils.JwtUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtGatewayFilter implements GlobalFilter, Ordered {
//
//    private final JwtUtils jwtUtils;
//
//    public JwtGatewayFilter(JwtUtils jwtUtils) {
//        this.jwtUtils = jwtUtils;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        String path = exchange.getRequest().getURI().getPath();
//        System.out.println("GATEWAY PATH = " + path);
//
//        // ✅ PUBLIC ENDPOINTS (NO JWT)
//        if (path.startsWith("/auth/")
//                || path.startsWith("/users/")
//                || path.startsWith("/products/")
//                || path.startsWith("/orders/")
//                || path.startsWith("/swagger-ui")
//                || path.contains("/v3/api-docs")
//                || path.startsWith("/actuator")) {
//
//            return chain.filter(exchange);
//        }
//
//        // 🔐 JWT REQUIRED
//        String authHeader = exchange.getRequest()
//                .getHeaders()
//                .getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            System.out.println("❌ Missing Authorization header");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String token = authHeader.substring(7);
//
//        if (!jwtUtils.validateToken(token)) {
//            System.out.println("❌ Token validation failed");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // ✅ Forward user details to downstream services
//        ServerWebExchange modifiedExchange = exchange.mutate()
//                .request(builder -> builder
//                        .header("X-USER-NAME", jwtUtils.extractUsername(token))
//                        .header("X-USER-ROLE", jwtUtils.extractRole(token))
//                )
//                .build();
//
//        return chain.filter(modifiedExchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1; // Highest priority
//    }
//}
