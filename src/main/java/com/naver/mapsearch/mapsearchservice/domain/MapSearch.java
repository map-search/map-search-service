package com.naver.mapsearch.mapsearchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MapSearch {
    private String location;
    private List<String> location_tokens;
    private Double latitude;
    private Double longitude;
}
