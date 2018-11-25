package com.ddolsoon.mapsearch.controller;

import com.ddolsoon.mapsearch.domain.MapSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mapsearch")
public class MapSearchController {

    @GetMapping("/helloworld/{name}")
    public Mono<String> hello(@PathVariable("name") String name) {
        return Mono.just("Hello, World!!! your name is " + name);
    }

    @GetMapping("/keywordsearch/{keyword}")
    public Mono<MapSearch> searchWithKeyword(@PathVariable("keyword") String keyword) {

        return Mono.just(new MapSearch());
    }

    @GetMapping("/keywordsearch/{keyword}/{latitude}/{longitude}")
    public Mono<MapSearch> searchWithKeywordAndLatLon
            (@PathVariable("keyword") String keyword,
             @PathVariable("latitude")Double latitude,
             @PathVariable("longitude")Double longitude) {

        return Mono.just(new MapSearch());
    }
}
