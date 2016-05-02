package com.isabellegeorge.pinballwizard.models;

/**
 * Created by Epicodus on 4/28/16.
 */
public class Region {
    private String name;
    private String city;
    private int id;

    public Region(String name, String city, int id){
        this.name = name;
        this.city = city;
        this.id = id;
    }

    public String getUrlName(){
        return name;
    }

    public String getCity(){
        return city;
    }

    public int getId(){
        return id;
    }
}
