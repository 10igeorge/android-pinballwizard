package com.isabellegeorge.pinballwizard.models;

import java.util.ArrayList;

/**
 * Created by Guest on 5/12/16.
 */
public class Event {
    private String name, description, startDate, endDate;

    public Event(String name, String description, String startDate, String endDate){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName(){return name;}

    public String getDescription(){
        return description;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

}
