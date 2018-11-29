package com.naver.mapsearch.mapsearchservice.repository;

import com.naver.mapsearch.mapsearchservice.controller.MapSearchHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MapSearchLogRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapSearchHandler.class);

    public void saveSearchLog(String ip, String keyword, Double latitude, Double longitude) {

        String log_template = ip + ", " + keyword + ", " + latitude.toString() + ", " + longitude.toString();
        LOGGER.info("mapsearch 로그임!! : {}", log_template);
    }
}
