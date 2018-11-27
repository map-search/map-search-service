package com.naver.mapsearch.mapsearchservice.service;

import com.naver.mapsearch.mapsearchservice.repository.MapSearchLogRepository;
import com.naver.mapsearch.mapsearchservice.repository.MapSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapSearchService {

    @Autowired
    MapSearchRepository mapSearchRepository;

    @Autowired
    MapSearchLogRepository mapSearchLogRepository;


}
