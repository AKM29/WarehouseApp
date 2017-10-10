package com.example.alex.warehouseapp;

import java.util.ArrayList;

/**
 * Created by WardB on 10/10/2017.
 */

public class Users {

    private String Username;
    private String Password;
    private String fav_department;
    private ArrayList Chart;


    public Users(String Username, String Password, String fav_department, ArrayList Chart){
        this.Username = Username;
        this.Password = Password;
        this.fav_department = fav_department;
        this.Chart = Chart;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFav_department() {
        return fav_department;
    }

    public void setFav_department(String fav_department) {
        this.fav_department = fav_department;
    }

    public ArrayList getChart() {
        return Chart;
    }

    public void setChart(ArrayList chart) {
        Chart = chart;
    }
}
