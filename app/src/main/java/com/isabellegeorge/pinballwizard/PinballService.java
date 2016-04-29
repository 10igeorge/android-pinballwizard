package com.isabellegeorge.pinballwizard;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Epicodus on 4/28/16.
 */
public class PinballService {
    public static void findRegions(Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = HttpUrl.parse(Constants.REGION_URL).toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Region> processRegions(Response response){
        ArrayList<Region> regions = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()){
                JSONObject regionJSON = new JSONObject(jsonData);
                JSONArray regionsJSON = regionJSON.getJSONArray("regions");
                for (int i = 0; i<regionsJSON.length(); i++){
                    JSONObject detailsJSON = regionsJSON.getJSONObject(i);
                    String city = detailsJSON.getString("full_name");
                    int id = detailsJSON.getInt("id");
                    Region region = new Region(city, id);
                    regions.add(region);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }
        return regions;
    }

    public static void findLocations(String city, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LOCATIONS_URL).newBuilder();
        urlBuilder.addPathSegment(city+"/locations");
        String url = urlBuilder.toString();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Location> processLocations(Response response){
        ArrayList<Location> locations = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject locationJSON = new JSONObject(jsonData);
                JSONArray locationsJSON = locationJSON.getJSONArray("locations");
                for (int i = 0; i < locationsJSON.length(); i++) {
                    JSONObject detailsJSON = locationsJSON.getJSONObject(i);
                    String name = detailsJSON.getString("name");
                    String address = detailsJSON.getString("street");
                    String city = detailsJSON.getString("city");
                    String state = detailsJSON.getString("state");
                    String zip = detailsJSON.getString("zip");
                    int locationTypeId = detailsJSON.getInt("location_type_id");
                    String phone = detailsJSON.getString("phone");
                    ArrayList<String> machines = new ArrayList<>();
                    ArrayList<String> machineConditions = new ArrayList<>();
                    JSONArray machinesJSON = detailsJSON.getJSONArray("location_machine_xrefs");
                    for(int j=0; j<machinesJSON.length(); j++){
                        JSONObject machineDetailsJSON = machinesJSON.getJSONObject(i);
                        int machineId = machineDetailsJSON.getInt("machine_id");
                        //TODO: get machine name from id

                        JSONArray machineConditionsJSON = machineDetailsJSON.getJSONArray("machine_conditions");
                        for(int k=0; k<machineConditionsJSON.length(); k++){
                            JSONObject commentsJSON =  machineConditionsJSON.getJSONObject(k);
                            String comment = commentsJSON.getString("comment");
                            machineConditions.add(comment);
                        }
                    }
                    Location location = new Location(name, address, city, state, zip, locationTypeId, phone,machines,machineConditions);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return locations;
    }
}
