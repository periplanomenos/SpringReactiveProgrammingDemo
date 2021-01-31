package org.example.srpd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Class: RestRouter
 */
@Slf4j
@Configuration
public class RestRouter {

    @Bean
    public RouterFunction<ServerResponse> rout(RestHandler restHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        restHandler::hello)
                .andRoute(RequestPredicates.GET("/employees").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        restHandler::getAllEmployeeData)
                .andRoute(RequestPredicates.GET("/employees/{id}").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        restHandler::getSingleEmployeeData);
    }

}
