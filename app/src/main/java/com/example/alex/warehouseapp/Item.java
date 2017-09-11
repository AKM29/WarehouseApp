package com.example.alex.warehouseapp;

/**
 * Created by Alex on 11/09/2017.
 */

public class Item {
    //Variables
    private String name;
    private String description;
    private double price;
    private int id;

    //Constructor
    public Item(String Name, String Description, double Price, int Id) {
        name = Name;
        description = Description;
        price = Price;
        id = Id;
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
