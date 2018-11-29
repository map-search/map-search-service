package com.naver.mapsearch.mapsearchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class MapSearchLog {

    private String ip;
    private String searchKeyword;
    private Date searchDate;
    private Double latitude;
    private Double longitude;
}
