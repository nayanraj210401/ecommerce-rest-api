package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/orders/**")
                        .uri("lb://ORDER-SERVICE"))
                .route(r -> r.path("/payments/**")
                        .uri("lb://PAYMENT-SERVICE"))
                .route(r -> r.path("/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .build();
    }
}
