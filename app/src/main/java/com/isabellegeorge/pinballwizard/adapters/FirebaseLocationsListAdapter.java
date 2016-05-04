package com.isabellegeorge.pinballwizard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.util.FirebaseRecyclerAdapter;

/**
 * Created by Guest on 5/4/16.
 */
public class FirebaseLocationsListAdapter extends FirebaseRecyclerAdapter<LocationsViewHolder, Location>{
    public FirebaseLocationsListAdapter(Query query, Class<Location> itemClass){
        super(query, itemClass);
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_item, parent, false);
        return new LocationsViewHolder(v,getItems());
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        holder.bindLocation(getItem(position));
    }

    @Override
    protected void itemAdded(Location item, String key, int position) {

    }

    @Override
    protected void itemChanged(Location oldItem, Location newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Location item, String key, int position) {

    }

    @Override
    protected void itemMoved(Location item, String key, int oldPosition, int newPosition) {

    }
}
