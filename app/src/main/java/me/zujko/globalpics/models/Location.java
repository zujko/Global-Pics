package me.zujko.globalpics.models;

public class Location {
    public double lat;

    public double lng;

    public String city;

    public Location(double lat, double lng, String city) {
        this.lat = lat;
        this.lng = lng;
        this.city = city;
    }
}
