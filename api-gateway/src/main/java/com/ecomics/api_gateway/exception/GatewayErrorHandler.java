package com.ecomics.api_gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GatewayErrorHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Mono<Void> writeErrorResponse(
            ServerWebExchange exchange,
            HttpStatus status,
            String message
    ) {

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.name(),
                message,
                exchange.getRequest().getURI().getPath()
        );

        byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(errorResponse);
        } catch (Exception e) {
            return Mono.error(e);
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse()
                .writeWith(Mono.just(
                        exchange.getResponse()
                                .bufferFactory()
                                .wrap(bytes)
                ));
    }
}
