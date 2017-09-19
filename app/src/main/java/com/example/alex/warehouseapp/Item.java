package com.example.alex.warehouseapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 11/09/2017.
 */

public class Item {
    //Variables
    private String name;
    private String description;
    private String department;
    private double price;

    //Constructor
    public Item(String name, String description, String department, double price) {
        this.name = name;
        this.description = description;
        this.department = department;
        this.price = price;
    }

    //Methods
    public Map<String, Object> map() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("department", department);
        result.put("price", price);
        return result;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
