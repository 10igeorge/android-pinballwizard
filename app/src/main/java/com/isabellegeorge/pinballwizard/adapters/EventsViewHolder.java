package com.isabellegeorge.pinballwizard.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Event;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.ui.LocationDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/12/16.
 */
public class EventsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.eventName) TextView mEventName;
        @Bind(R.id.eventDescription) TextView mEventDescription;
        @Bind(R.id.eventStartDate) TextView mStartDate;
        @Bind(R.id.eventEndDate) TextView mEndDate;
        private Context c;
        private ArrayList<Event> mEvents = new ArrayList<>();

        public EventsViewHolder(View itemView, ArrayList<com.isabellegeorge.pinballwizard.models.Event> events) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            c = itemView.getContext();
            mEvents = events;
        }

        public void bindEvent(com.isabellegeorge.pinballwizard.models.Event event){
            mEventName.setText(event.getName());
            mEventDescription.setText(event.getDescription());
            mStartDate.setText(event.getStartDate());
            mEndDate.setText(event.getEndDate());
        }

}
