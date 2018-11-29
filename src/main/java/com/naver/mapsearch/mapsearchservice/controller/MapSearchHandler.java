package com.naver.mapsearch.mapsearchservice.controller;


import com.naver.mapsearch.mapsearchservice.domain.MapSearch;
import com.naver.mapsearch.mapsearchservice.service.MapSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class MapSearchHandler {

    private final MapSearchService mapSearchService;

    @Autowired
    public MapSearchHandler(MapSearchService mapSearchService) {
        this.mapSearchService = mapSearchService;
    }

    Mono<ServerResponse> helloworld(ServerRequest request) {
        String name = request.pathVariable("name");
        Mono<String> helloworld = Mono.just("hello, world!! your name is " + name);
        return ServerResponse.ok().body(helloworld, String.class);
    }

    Mono<ServerResponse> searchWithKeyword(ServerRequest serverRequest) {

        String keyword = serverRequest.pathVariable("keyword");

        Flux<MapSearch> searchResult = mapSearchService.searchWithKeyword(keyword); //MapSearchRepository 결과값

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(searchResult, MapSearch.class);
    }

    Mono<ServerResponse> searchWithKeywordAndLatLon(ServerRequest serverRequest) {

        String ip = serverRequest.pathVariable("ip");
        String keyword = serverRequest.pathVariable("keyword");
        double latitude = Double.parseDouble(serverRequest.pathVariable("latitude"));
        double longitude = Double.parseDouble(serverRequest.pathVariable("longitude"));

        log.info("ip: {}", ip);
        log.info("keyword:  {}", keyword);
        log.info("latitude: {}", latitude);
        log.info("longitude: {}", longitude);

        Flux<MapSearch> searchResult = mapSearchService.searchWithKeywordAndLatLon(ip,keyword, latitude, longitude); //MapSearchRepository 결과값

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(searchResult, MapSearch.class);
    }
}