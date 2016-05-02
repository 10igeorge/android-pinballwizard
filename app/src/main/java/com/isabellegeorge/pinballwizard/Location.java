package com.isabellegeorge.pinballwizard;

import java.util.ArrayList;

/**
 * Created by Epicodus on 4/29/16.
 */
public class Location {
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String locationType;
    private String phone;
    private ArrayList<String> machines;
    private ArrayList<String> machineConditions;

    public Location(String name, String address, String city, String state, String zip, String phone, ArrayList<String> machines, ArrayList<String> machineConditions){
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.machines = machines;
        this.machineConditions = machineConditions;
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

    public String getPhone(){
        return phone;
    }

    public ArrayList<String> getMachines(){
        return machines;
    }

    public ArrayList<String> getMachineConditions(){
        return machineConditions;
    }
}



