package com.naver.mapsearch.mapsearchservice.repository;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapSearchLogRepository {

    private final String INDEX = "tests";
    private final String TYPE = "test";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


}
