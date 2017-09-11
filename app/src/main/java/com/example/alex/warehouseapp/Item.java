package com.example.alex.warehouseapp;

/**
 * Created by Alex on 11/09/2017.
 */

public class Item {
    //Variables
    private String name;
    private String description;
    private String department;
    private double price;
    private int id;

    //Constructor


    public Item(String name, String description, String department, double price, int id) {
        this.name = name;
        this.description = description;
        this.department = department;
        this.price = price;
        this.id = id;
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

    public int getId() {
        return id;
    }
}
