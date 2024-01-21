package com.mariocosta.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("artist", r -> r.path("/api/v1/artist/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://artist"))
                .route("rating", r -> r.path("/api/v1/rating/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://rating"))
                .route("donation", r -> r.path("/api/v1/donation/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://donation"))
                .route("localization", r -> r.path("/api/v1/localization/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://localization"))
                .route("user", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user"))
                .route("auth", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth"))
                .build();
    };
}
