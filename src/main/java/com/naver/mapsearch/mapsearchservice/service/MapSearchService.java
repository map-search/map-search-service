package com.naver.mapsearch.mapsearchservice.service;

import com.naver.mapsearch.mapsearchservice.domain.MapSearch;
import com.naver.mapsearch.mapsearchservice.repository.MapSearchLogRepository;
import com.naver.mapsearch.mapsearchservice.repository.MapSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MapSearchService {

    @Autowired
    MapSearchRepository mapSearchRepository;

    @Autowired
    MapSearchLogRepository mapSearchLogRepository;


    public Flux<MapSearch> searchWithKeyword(String keyword) {

        return mapSearchRepository.searchWithKeyword(keyword);
    }

    public Flux<MapSearch> searchWithKeywordAndLatLon(String ip,String keyword, Double latitude, Double longitude) {

        mapSearchLogRepository.saveSearchLog(ip, keyword, latitude, longitude);

        return mapSearchRepository.searchWithKeywordAndLatLon(keyword, latitude, longitude);
    }
}
