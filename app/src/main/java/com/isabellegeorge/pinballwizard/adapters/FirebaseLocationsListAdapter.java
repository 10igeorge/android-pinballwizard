package com.isabellegeorge.pinballwizard.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.util.FirebaseRecyclerAdapter;
import com.isabellegeorge.pinballwizard.util.ItemTouchHelperAdapter;
import com.isabellegeorge.pinballwizard.util.OnStartDragListener;

import java.util.Collections;

/**
 * Created by Guest on 5/4/16.
 */
public class FirebaseLocationsListAdapter extends FirebaseRecyclerAdapter<LocationsViewHolder, Location> implements ItemTouchHelperAdapter {
    private final OnStartDragListener mStartDragListener;
    private Context c;

    public FirebaseLocationsListAdapter(Query query, Class<Location> itemClass, OnStartDragListener startDragListener){
        super(query, itemClass);
        mStartDragListener = startDragListener;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        c = parent.getContext();
        View v = LayoutInflater.from(c)
                .inflate(R.layout.location_list_item_drag, parent, false);
        return new LocationsViewHolder(v,getItems());
    }

    @Override
    public void onBindViewHolder(final LocationsViewHolder holder, int position) {
        holder.bindLocation(getItem(position));
        holder.mItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN){
                    mStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        String uid = sharedPreferences.getString(Constants.KEY_UID, null);
        Firebase ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS).child(uid);
        String locationKey = getItem(position).getPushId();
        ref.child(locationKey).removeValue();
    }

    @Override
    public int getItemCount() {
        return getItems().size();
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
