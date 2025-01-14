package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("order-service", r -> r.path("/orders/**")
                .uri("lb://ORDER-SERVICE"))
            .route("ECOMMERCE", r -> r.path("/api/**")
                .uri("lb://ECOMMERCE"))
            .build();
    }
}