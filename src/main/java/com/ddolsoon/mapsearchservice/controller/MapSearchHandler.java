package com.ddolsoon.mapsearchservice.controller;


import com.ddolsoon.mapsearchservice.domain.MapSearch;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MapSearchHandler {

    public Mono<ServerResponse> helloworld(ServerRequest request) {
        String name = request.pathVariable("name");
        Mono<String> helloworldMono = Mono.just("hello, world!! your name is " + name);
        return ServerResponse.ok().body(helloworldMono, String.class);
    }

    public Mono<ServerResponse> searchWithKeyword(ServerRequest serverRequest) {

        String keyword = serverRequest.pathVariable("keyword");

        Mono<MapSearch> searchResult = null; //MapSearchRepository 결과값

        return ServerResponse.ok().body(searchResult, MapSearch.class);
    }

    public Mono<ServerResponse> searchWithKeywordAndLatLon(ServerRequest serverRequest) {

        String keyword = serverRequest.pathVariable("keyword");
        Double latitude = Double.parseDouble(serverRequest.pathVariable("latitude"));
        Double longitude = Double.parseDouble(serverRequest.pathVariable("longitude"));


        Mono<MapSearch> searchResult = null; //MapSearchRepository 결과값

        return ServerResponse.ok().body(searchResult, MapSearch.class);
    }
}