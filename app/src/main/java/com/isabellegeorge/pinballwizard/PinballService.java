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

        String url = HttpUrl.parse(Constants.BASE_URL).toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);

//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
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
}
