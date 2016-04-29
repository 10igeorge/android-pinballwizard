package com.isabellegeorge.pinballwizard;

/**
 * Created by Epicodus on 4/28/16.
 */
public class Region {
    private String city;
    private int id;

    public Region(String city, int id){
        this.city = city;
        this.id = id;
    }

    public String getCity(){
        return city;
    }

    public int getId(){
        return id;
    }
}
