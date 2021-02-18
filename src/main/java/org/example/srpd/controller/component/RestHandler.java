package org.example.srpd.controller.component;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.my_digital_bank.model.entity.User;
import org.json.JSONObject;
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
@RequiredArgsConstructor
public class RestHandler {

    private final WebClient webClient;
    private final HazelcastInstance hazelcastInstance;

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

    public Mono<ServerResponse> getHazelcastMapByKeys(ServerRequest serverRequest) {
        IMap imap = hazelcastInstance.getMap(serverRequest.pathVariable("imapKey"));
        User user = (User) imap.get(serverRequest.pathVariable("objectKey"));
        User newUser = new User();

        if(user != null) {
            newUser.setId(user.getId());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setEnabled(user.isEnabled());
            newUser.setTimeUpdated(user.getTimeUpdated());
        }

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newUser));
    }

}
