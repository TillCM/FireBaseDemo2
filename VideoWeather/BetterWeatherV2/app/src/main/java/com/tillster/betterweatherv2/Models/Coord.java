package com.tillster.betterweatherv2.Models;

public class Coord
{
    public Coord() { }

    private double lon ;
    private double lat ;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
