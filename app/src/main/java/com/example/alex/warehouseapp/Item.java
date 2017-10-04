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
    private String image;

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
        result.put("Name", name);
        result.put("Description", description);
        result.put("Department", department);
        result.put("Price", price);
        return result;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getDepartment() { return department; }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
