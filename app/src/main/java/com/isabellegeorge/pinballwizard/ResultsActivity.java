package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
            "Ground Kontrol",
            "Scoreboard",
            "Momo",
            "Kelly's Olympian",
            "Yamhill Pub",
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

        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, hardCodedLocations);

        mLocations.setAdapter(listAdapter);

        Intent i = getIntent();
        String location = i.getStringExtra("location");
        mResultsTextView.setText("Pinball near " + location);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0){
        //do nothing
    }
}
