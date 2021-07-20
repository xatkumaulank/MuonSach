package com.example.muonsach.obj;

public class GeoLocation {
    private double longtitude;
    private double latitude;

    public GeoLocation(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public GeoLocation() {
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
