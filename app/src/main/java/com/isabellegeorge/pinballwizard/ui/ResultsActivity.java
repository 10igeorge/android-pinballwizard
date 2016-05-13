package com.isabellegeorge.pinballwizard.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.EndlessScrollListener;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.models.Machine;
import com.isabellegeorge.pinballwizard.services.PinballService;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.LocationsListAdapter;
import com.isabellegeorge.pinballwizard.adapters.MachinesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends NavDrawerActivity implements AdapterView.OnItemSelectedListener {
    @Bind(R.id.resultsTextView) TextView mResultsTextView;
    @Bind(R.id.filterSpinner) Spinner mFilter;
    @Bind(R.id.locationsWithPinball) RecyclerView locationsRecycler;
    @Bind(R.id.noResults) TextView noResults;
    private String name, city, mRegion, item;
    private SharedPreferences mSharedPref;
    private ProgressDialog loadingResults;
    private LocationsListAdapter adapter;
    private MachinesListAdapter machineAdapter;
    public ArrayList<Location> locations = new ArrayList<>();
    public ArrayList<Location> searchLocations = new ArrayList<>();
    public ArrayList<Machine> searchMachines = new ArrayList<>();
    public ArrayList<Machine> machines = new ArrayList<>();

    String[] filters = new String[] {
            "Locations",
            "Machines"
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_results, null, false);
        drawer.addView(contentView, 0);

        ButterKnife.bind(this);

        mFilter.setOnItemSelectedListener(ResultsActivity.this);
        List<String> spinnerFilters = new ArrayList<>(Arrays.asList(filters));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerFilters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilter.setAdapter(spinnerAdapter);

        Intent i = getIntent();
        city = i.getStringExtra("city");
        name = i.getStringExtra("name");

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mRegion = mSharedPref.getString(Constants.PREFERENCES_REGION_KEY, null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultsActivity.this, EventsActivity.class);
                i.putExtra("name", name);
                i.putExtra("city", city);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) MenuItemCompat.getActionView(item);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocations = new ArrayList<>();
                searchList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void searchList(String query){
        if(item.equals("Locations")){
            boolean locationFound = false;
            if(!(query.trim().equals(""))){
                for(int i=0; i<locations.size(); i++){
                    if(locations.get(i).getName().toLowerCase().contains(query)){
                        searchLocations.add(locations.get(i));
                        locationFound = true;
                    }
                }
                if(searchLocations.size() == 0 || !locationFound){
                    Toast.makeText(ResultsActivity.this, "No results found", Toast.LENGTH_LONG).show();
                } else {
                    adapter = new LocationsListAdapter(getApplicationContext(), searchLocations);
                    locationsRecycler.setAdapter(adapter);
                }
            }
        } else {
            boolean machineFound = false;
            if(!(query.trim().equals(""))){
                for(int i=0; i<machines.size(); i++){
                    if(machines.get(i).getMachineName().toLowerCase().contains(query)){
                        searchMachines.add(machines.get(i));
                        machineFound = true;
                    }
                }
                if(searchMachines.size() == 0 || !machineFound){
                    Toast.makeText(ResultsActivity.this, "No results found", Toast.LENGTH_LONG).show();
                } else {
                    machineAdapter = new MachinesListAdapter(getApplicationContext(), searchMachines);
                    locationsRecycler.setAdapter(machineAdapter);
                }
            }
        }
    }

    private void getLocations(String city){
        loadingResults.show();

        final PinballService pinballService = new PinballService();

        pinballService.findLocations(city, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                locations = pinballService.processLocations(response);

                for(int i=0; i<locations.size(); i++){
                    if(mRegion != null){
                        locations.get(i).setUrlPath(mRegion);
                    } else {
                        locations.get(i).setUrlPath(name);
                    }
                }

                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!locations.isEmpty()){
                            adapter = new LocationsListAdapter(getApplicationContext(), locations);
                            locationsRecycler.setAdapter(adapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ResultsActivity.this);
                            locationsRecycler.setLayoutManager(layoutManager);
                            locationsRecycler.setHasFixedSize(true);
                            loadingResults.dismiss();
                            Toast.makeText(getBaseContext(), "Filtering by " + item.toLowerCase(), Toast.LENGTH_LONG).show();
                        } else {
                            noResults.setText("Sorry, no results were found");
                            loadingResults.dismiss();
                        }

                    }
                });
            }
        });
    }

    private void getMachines(String name){
        loadingResults.show();

        final PinballService pinballService = new PinballService();

        pinballService.findMachinesInRegion(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                machines = pinballService.processMachinesInRegion(response);

                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!machines.isEmpty()){
                            Collections.sort(machines, new Comparator<Machine>() {
                                @Override
                                public int compare(Machine machine, Machine t1) {
                                    return machine.getMachineName().compareTo(t1.getMachineName());
                                }
                            });
                            machineAdapter = new MachinesListAdapter(getApplicationContext(), machines);
                            locationsRecycler.setAdapter(machineAdapter);
                            locationsRecycler.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
                            locationsRecycler.setHasFixedSize(true);
                            loadingResults.dismiss();
                            Toast.makeText(getBaseContext(), "Filtering by " + item.toLowerCase(), Toast.LENGTH_LONG).show();
                        } else {
                            noResults.setText("Sorry, no results were found");
                            loadingResults.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        item = parent.getItemAtPosition(position).toString();
        if(item.equals("Locations")){
            loadingResults = new ProgressDialog(this);
            loadingResults.setTitle("Loading...");
            loadingResults.setMessage("Finding locations in your region...");
            loadingResults.setCancelable(false);
            if(mRegion != null) {
                mResultsTextView.setText("Pinball near " + mSharedPref.getString(Constants.PREFERENCES_CITY_KEY, null));
                getLocations(mRegion);
            } else {
                mResultsTextView.setText("Pinball near " + city);
                getLocations(name);
            }
        } else {
            loadingResults = new ProgressDialog(this);
            loadingResults.setTitle("Loading...");
            loadingResults.setMessage("Finding machines in your region...");
            loadingResults.setCancelable(false);
            getMachines(name);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){
        //do nothing
    }

}
