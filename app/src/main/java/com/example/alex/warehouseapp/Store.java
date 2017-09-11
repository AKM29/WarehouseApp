package com.example.alex.warehouseapp;

/**
 * Created by Alex on 11/09/2017.
 */

public class Store {
    //Variables
    private String name;
    private double latitude;
    private double longitude;

    //Constructor
    public Store(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Getters
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
