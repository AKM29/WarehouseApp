package com.example.alex.warehouseapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 11/09/2017.
 */

public class Store {
    //Variables
    private String name;
    private double latitude;
    private double longitude;

    private ArrayList<Item> deals = new ArrayList<>();

    //Constructor
    public Store(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Methods
    public void addItem(Item item){
        deals.add(item);
    }

    public Map<String, Object> map() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);

        return result;
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

    public ArrayList<Item> getDeals() { return deals; }
}
