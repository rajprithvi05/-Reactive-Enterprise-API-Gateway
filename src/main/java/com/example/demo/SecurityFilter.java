package com.example.demo;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Look for the "Authorization" header (This is where a JWT would be)
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 2. If it's missing or wrong, instantly reject the request (401 Unauthorized)
        // In a real app, you would mathematically decode the JWT here instead of a simple string check.
        if (authHeader == null || !authHeader.equals("Bearer my-secret-token")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); 
        }

        // 3. Token is valid! Let them pass to the Rate Limiter and Router.
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // The order is -1 so it runs FIRST, before any other filters
        return -1; 
    }
}