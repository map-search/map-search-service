package com.naver.mapsearch.mapsearchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MapSearchLog {

    private String ip;
    private String searchKeyword;
    private Double latitude;
    private Double longitude;
}
