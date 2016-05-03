package com.isabellegeorge.pinballwizard.models;

import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Epicodus on 4/29/16.
 */

@Parcel
public class Location {
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String locationType;
    private String urlPath;
    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<String> machineConditions;

    public Location(int id, String name, String address, String city, String state, String zip, ArrayList<Machine> machines, ArrayList<String> machineConditions){
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.machines = machines;
        this.machineConditions = machineConditions;
    }

    public Location() {}

    public int getLocationId(){
        return id;
    }

    public void setUrlPath(String urlPath){
        this.urlPath = urlPath;
    }

    public String getUrlPath(){
        return urlPath;
    }

    public String getLocationName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getZip(){
        return zip;
    }

    public String getLocationType(){
        return locationType;
    }
    public void setLocationType(String type){
        this.locationType = type;
    }

    public int getNumberMachines(){
        int machineCount=0;
        for(int i=0; i<machines.size(); i++){
            machineCount++;
        }
        return machineCount;
    }

    public ArrayList<String> getMachineConditions(){
        return machineConditions;
    }
}



