package com.naver.mapsearch.mapsearchservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.mapsearch.mapsearchservice.domain.MapSearchLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MapSearchLogRepository {

    @Autowired
    private ObjectMapper objectMapper;

    public void saveSearchLog(String ip, String keyword, Double latitude, Double longitude)  {

        MapSearchLog mapSearchLog = new MapSearchLog(ip, keyword, latitude, longitude);

        String jsonMapSearchLog = null;
        try {
            jsonMapSearchLog = objectMapper.writeValueAsString(mapSearchLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("MapSearchLogJSON : {}", jsonMapSearchLog);
    }
}
