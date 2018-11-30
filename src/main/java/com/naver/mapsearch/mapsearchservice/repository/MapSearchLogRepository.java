package com.naver.mapsearch.mapsearchservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MapSearchLogRepository {

    public void saveSearchLog(String ip, String keyword, Double latitude, Double longitude)  {

        String SearchLogFormat = String.format("ip:%s,keyword:%s,latitude:%f,longitude:%f",ip,keyword,latitude,longitude);
        log.info("MapSearchLogJSON : {}", SearchLogFormat);
    }
}
