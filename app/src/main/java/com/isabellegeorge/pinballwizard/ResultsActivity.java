package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultsActivity extends AppCompatActivity {
    @Bind(R.id.resultsTextView) TextView mResultsTextView;
    @Bind(R.id.locationsWithPinball) ListView mLocations;
    String[] hardCodedLocations = new String[] {
            "Ground Kontrol",
            "Scoreboard",
            "Momo",
            "Kelly's Olympian",
            "Yamhill Pub",
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, hardCodedLocations);
        mLocations.setAdapter(adapter);







        Intent i = getIntent();
        String location = i.getStringExtra("location");
        mResultsTextView.setText("Pinball near: " + location);
    }
}
