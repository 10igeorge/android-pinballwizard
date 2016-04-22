package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultsActivity extends AppCompatActivity {
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
    String[] filters = new filters[] {
            "Locations",
            "Machines"
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        mFilter.setOnItemSelectedListener(this);
        List<String> spinnerFilters = new ArrayList<>(Arrays.asList(filters));


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, hardCodedLocations);
        mLocations.setAdapter(adapter);

        Intent i = getIntent();
        String location = i.getStringExtra("location");
        mResultsTextView.setText("Pinball near: " + location);
    }
}
