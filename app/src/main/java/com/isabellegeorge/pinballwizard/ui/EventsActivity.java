package com.isabellegeorge.pinballwizard.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.EventsListAdapter;
import com.isabellegeorge.pinballwizard.adapters.LocationsListAdapter;
import com.isabellegeorge.pinballwizard.models.Event;
import com.isabellegeorge.pinballwizard.services.PinballService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EventsActivity extends NavDrawerActivity {
    @Bind(R.id.eventsRecyclerView) RecyclerView mEventsRecyclerView;
    @Bind(R.id.noEvents) TextView mNoEvents;
    @Bind(R.id.eventsTextView) TextView mTitle;
    private EventsListAdapter adapter;
    private String name, city;
    private ArrayList<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_events, null, false);
        drawer.addView(contentView, 0);

        ButterKnife.bind(this);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        city = i.getStringExtra("city");
        getEvents(name);

        mTitle.setText("Events in " + city);
    }

    private void getEvents(String name){
        final PinballService pinballService = new PinballService();

        pinballService.findEvents(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                events = pinballService.processEvents(response);
                EventsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!events.isEmpty()){
                            adapter = new EventsListAdapter(getApplicationContext(), events);
                            mEventsRecyclerView.setAdapter(adapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(EventsActivity.this);
                            mEventsRecyclerView.setLayoutManager(layoutManager);
                            mEventsRecyclerView.setHasFixedSize(true);
                        } else {
                            mNoEvents.setText("There are no upcoming events in this region");
                        }

                    }
                });
            }
        });
    }
}
