package com.naver.mapsearch.mapsearchservice.domain;

public class LatLon {
    private double latitude;
    private double longitude;


    public LatLon(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LatLon{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
