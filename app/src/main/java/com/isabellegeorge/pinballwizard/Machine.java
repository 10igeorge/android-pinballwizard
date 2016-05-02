package com.isabellegeorge.pinballwizard;

import java.util.ArrayList;

/**
 * Created by Epicodus on 4/29/16.
 */
public class Machine {
    private Integer id;
    private String name;
    private Integer year;
    private String manufacturer;

    public Machine(Integer id, String name, Integer year, String manufacturer){
        this.name = name;
        this.id = id;
        this.year = year;
        this.manufacturer = manufacturer;
    }


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

