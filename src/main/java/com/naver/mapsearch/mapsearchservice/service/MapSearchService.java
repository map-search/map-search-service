package com.naver.mapsearch.mapsearchservice.service;

import com.naver.mapsearch.mapsearchservice.domain.MapSearch;
import com.naver.mapsearch.mapsearchservice.repository.MapSearchLogRepository;
import com.naver.mapsearch.mapsearchservice.repository.MapSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MapSearchService {

    @Autowired
    MapSearchRepository mapSearchRepository;

    @Autowired
    MapSearchLogRepository mapSearchLogRepository;


    public Mono<List<MapSearch>> searchWithKeyword(String keyword) {

        return mapSearchRepository.searchWithKeyword(keyword);
    }
}
