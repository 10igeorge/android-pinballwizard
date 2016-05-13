package com.isabellegeorge.pinballwizard.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Event;
import com.isabellegeorge.pinballwizard.models.Location;

import java.util.ArrayList;

/**
 * Created by Guest on 5/12/16.
 */
public class EventsListAdapter extends RecyclerView.Adapter<EventsViewHolder> {
    private ArrayList<Event> mEvents = new ArrayList<>();
    private Context c;

    public EventsListAdapter(Context context, ArrayList<Event> events){
        mEvents = events;
        c = context;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        EventsViewHolder viewHolder = new EventsViewHolder(v, mEvents);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position){
        holder.bindEvent(mEvents.get(position));
    }

    @Override
    public int getItemCount(){
        return mEvents.size();
    }
}
