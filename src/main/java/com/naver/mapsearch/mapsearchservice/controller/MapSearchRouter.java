package com.naver.mapsearch.mapsearchservice.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableWebFlux
public class MapSearchRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(MapSearchHandler handler) {
        return RouterFunctions.route(GET("/helloworld/{name}"), handler::helloworld)
                .andRoute(GET("/keywordsearch/{keyword}"), handler::searchWithKeyword)
                .andRoute(GET("/keywordsearch/{ip}/{keyword}/{latitude}/{longitude}"), handler::searchWithKeywordAndLatLon)
                ;

    }
}