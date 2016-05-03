package com.isabellegeorge.pinballwizard.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Epicodus on 4/29/16.
 */

@Parcel
public class Machine {
    private int locationId;
    private Integer id;
    private String name;
    private Integer year;
    private String manufacturer;

    public Machine(){}

    public Machine(int locationId, Integer id, String name, Integer year, String manufacturer){
        this.locationId = locationId;
        this.name = name;
        this.id = id;
        this.year = year;
        this.manufacturer = manufacturer;
    }

    public int getLocationId(){return locationId;}

    public Integer getMachineId(){
        return id;
    }

    public void setMachineName(String name){
        this.name = name;
    }

    public String getMachineName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public int getMachineYear(){
        return year;
    }

}

