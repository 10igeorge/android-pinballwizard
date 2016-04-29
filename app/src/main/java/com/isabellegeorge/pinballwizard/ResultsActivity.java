package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
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
    @Bind(R.id.locationsWithPinball) ListView mLocations;
    @Bind(R.id.filterSpinner) Spinner mFilter;

    String[] hardCodedLocations = new String[] {
            "Ground Kontrol",
            "Scoreboard",
            "Momo",
            "Kelly's Olympian",
            "Yamhill Pub",
        };

    String[] hardCodedMachines = new String[] {
            "Monster Bash",
            "ACDC",
            "Attack from Mars",
            "Addams Family",
            "Creature from the Black Lagoon",
        };

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
        String city = i.getStringExtra("city");
        String id = i.getStringExtra("id");
        mResultsTextView.setText("Pinball near " + city);
        getLocations(city);
    }

    private void getLocations(String city){
        final PinballService pinballService = new PinballService();

        pinballService.findLocations(city, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("hey", "hey");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Filtering by " + item.toLowerCase(), Toast.LENGTH_LONG).show();
        if(item.equals("Locations")){
            ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, hardCodedLocations);
            mLocations.setAdapter(listAdapter);
        } else {
            ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, hardCodedMachines);
            mLocations.setAdapter(listAdapter);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){
        //do nothing
    }
}




//TODO: assign fragment to FAB for upcoming events w/ Pinball API