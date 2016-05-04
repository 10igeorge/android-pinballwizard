package com.isabellegeorge.pinballwizard.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.ui.LocationDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Epicodus on 4/29/16.
 */
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsViewHolder>{
    private ArrayList<Location> mLocations = new ArrayList<>();
    private Context c;

    public LocationsListAdapter(Context context, ArrayList<Location> locations){
        mLocations = locations;
        c = context;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        LocationsViewHolder viewHolder = new LocationsViewHolder(v, mLocations);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position){
        holder.bindLocation(mLocations.get(position));
    }

    @Override
    public int getItemCount(){
        return mLocations.size();
    }



}
