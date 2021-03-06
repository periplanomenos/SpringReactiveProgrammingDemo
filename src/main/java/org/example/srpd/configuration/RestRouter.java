package org.example.srpd.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.srpd.controller.component.RestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

/**
 * Class: RestRouter
 */
@Slf4j
@Configuration
public class RestRouter {

    @Bean
    public RouterFunction<ServerResponse> route(RestHandler restHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(accept(MediaType.TEXT_PLAIN)),
                        restHandler::hello)
                .andRoute(RequestPredicates.GET("/employees").and(accept(MediaType.TEXT_PLAIN)),
                        restHandler::getAllEmployeeData)
                .andRoute(RequestPredicates.GET("/employee/{id}").and(accept(MediaType.TEXT_PLAIN)),
                        restHandler::getSingleEmployeeData)
                .andRoute(RequestPredicates.POST("/create")
                                .and(accept(MediaType.APPLICATION_JSON))
                                .and(contentType(MediaType.APPLICATION_JSON)),
                        restHandler::createNewEmployeeRecord)
                .andRoute(RequestPredicates.GET("/hazelcast/{imapKey}/{objectKey}")
                                .and(accept(MediaType.TEXT_PLAIN)),
                        restHandler::getHazelcastMapByKeys);
    }

}
