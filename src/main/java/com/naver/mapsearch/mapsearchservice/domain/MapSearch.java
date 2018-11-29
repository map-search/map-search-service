package com.naver.mapsearch.mapsearchservice.domain;

import java.util.List;

public class MapSearch {

    private String location;
    private List<String> location_tokens;
    private Double latitude;
    private Double longitude;

    public MapSearch(String location, List<String> location_tokens, Double latitude, Double longitude) {
        this.location = location;
        this.location_tokens = location_tokens;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getLocation_tokens() {
        return location_tokens;
    }

    public void setLocation_tokens(List<String> location_tokens) {
        this.location_tokens = location_tokens;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "MapSearch{" +
                "location='" + location + '\'' +
                ", location_tokens=" + location_tokens +    
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
