package com.naver.mapsearch.mapsearchservice.controller;


import com.naver.mapsearch.mapsearchservice.domain.MapSearch;
import com.naver.mapsearch.mapsearchservice.service.MapSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MapSearchHandler {

    @Autowired
    MapSearchService mapSearchService;

    public Mono<ServerResponse> helloworld(ServerRequest request) {
        String name = request.pathVariable("name");
        Mono<String> helloworldMono = Mono.just("hello, world!! your name is " + name);
        return ServerResponse.ok().body(helloworldMono, String.class);
    }

    public Mono<ServerResponse> searchWithKeyword(ServerRequest serverRequest) {

        String keyword = serverRequest.pathVariable("keyword");

        Mono<List<MapSearch>> searchResult = mapSearchService.searchWithKeyword(keyword); //MapSearchRepository 결과값

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(searchResult.flatMapMany(Flux::fromIterable), MapSearch.class);
    }

    public Mono<ServerResponse> searchWithKeywordAndLatLon(ServerRequest serverRequest) {

        String keyword = serverRequest.pathVariable("keyword");
        Double latitude = Double.parseDouble(serverRequest.pathVariable("latitude"));
        Double longitude = Double.parseDouble(serverRequest.pathVariable("longitude"));


        Mono<MapSearch> searchResult = null; //MapSearchRepository 결과값

        return ServerResponse.ok().body(searchResult, MapSearch.class);
    }
}