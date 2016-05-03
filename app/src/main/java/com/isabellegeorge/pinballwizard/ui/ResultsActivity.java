package com.isabellegeorge.pinballwizard.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.models.Machine;
import com.isabellegeorge.pinballwizard.services.PinballService;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.LocationsListAdapter;
import com.isabellegeorge.pinballwizard.adapters.MachinesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Bind(R.id.resultsTextView) TextView mResultsTextView;
    @Bind(R.id.filterSpinner) Spinner mFilter;
    @Bind(R.id.locationsWithPinball) RecyclerView locationsRecycler;
    private String name;
    private int regionId;
    public ArrayList<Location> locations = new ArrayList<>();
    public ArrayList<Machine> machines = new ArrayList<>();


    String[] filters = new String[] {
            "Locations",
            "Machines"
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        mFilter.setOnItemSelectedListener(ResultsActivity.this);

        List<String> spinnerFilters = new ArrayList<>(Arrays.asList(filters));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerFilters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilter.setAdapter(spinnerAdapter);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        regionId = i.getIntExtra("id", 0);
        String city = i.getStringExtra("city");
        mResultsTextView.setText("Pinball near " + city);
    }

    private void getLocations(String city){
        final PinballService pinballService = new PinballService();

        pinballService.findLocations(city, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                locations = pinballService.processLocations(regionId, response);
                for(int i=0; i<locations.size(); i++){
                    setMachines(locations.get(i));
                }
                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationsRecycler.setAdapter(new LocationsListAdapter(getApplicationContext(), locations));
                        locationsRecycler.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
                        locationsRecycler.setHasFixedSize(true);

                    }
                });
            }
        });
    }

    private void setMachines(final Location location){
        final PinballService pinballService = new PinballService();
        pinballService.findMachines(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pinballService.getLocationMachines(name, location);
                Log.v("here?", "here!");
//                ResultsActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        locationsRecycler.setAdapter(new MachinesListAdapter(getApplicationContext(), machines));
//                        locationsRecycler.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
//                        locationsRecycler.setHasFixedSize(true);
//                    }
//                });
            }
        });
    }

    private void getMachines(String name){
        final PinballService pinballService = new PinballService();

        pinballService.findMachines(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                machines = pinballService.processMachines(response);
                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locationsRecycler.setAdapter(new MachinesListAdapter(getApplicationContext(), machines));
                        locationsRecycler.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
                        locationsRecycler.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Filtering by " + item.toLowerCase(), Toast.LENGTH_LONG).show();
        if(item.equals("Locations")){
            getLocations(name);
        } else {
            getMachines(name);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){
        //do nothing
    }
}




//TODO: assign fragment to FAB for upcoming events w/ Pinball API