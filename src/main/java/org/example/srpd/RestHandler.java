package org.example.srpd;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Class: RestHandler
 */
@Slf4j
@Component
public class RestHandler {

    @Autowired
    WebClient webClient;

    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, World!"));
    }

    public Mono<ServerResponse> getAllEmployeeData(ServerRequest serverRequest) {
        return webClient
                .get()
                .uri("/employees")
                .exchange()
                .flatMap((ClientResponse mapper) -> {
                    return ServerResponse.status(mapper.statusCode())
                            .headers(c -> mapper.headers().asHttpHeaders().forEach((name, value) -> c.put(name, value)))
                            .body(mapper.bodyToFlux(DataBuffer.class), DataBuffer.class);
                });
    }

    public Mono<ServerResponse> getSingleEmployeeData(ServerRequest serverRequest) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee/{id}")
                        .build(serverRequest.pathVariable("id")))
                .exchange()
                .flatMap((ClientResponse mapper) -> {
                    return ServerResponse.status(mapper.statusCode())
                            .headers(c -> mapper.headers().asHttpHeaders().forEach((name, value) -> c.put(name, value)))
                            .body(mapper.bodyToFlux(DataBuffer.class), DataBuffer.class);
                });
    }

    public Mono<ServerResponse> createNewEmployeeRecord(ServerRequest serverRequest) {
        return webClient
                .post()
                .uri("/create")
                .body(Mono.just(serverRequest.bodyToMono(JSONObject.class)), JSONObject.class)
                .exchange()
                .flatMap((ClientResponse mapper) -> {
                    return ServerResponse.status(mapper.statusCode())
                            .headers(c -> mapper.headers().asHttpHeaders().forEach((name, value) -> c.put(name, value)))
                            .body(mapper.bodyToFlux(DataBuffer.class), DataBuffer.class);
                });
    }

}
