package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.locationEditText) EditText mLocation;
    @Bind(R.id.searchRegionButton) Button mSearchRegion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSearchRegion.setOnClickListener(this);
    }

    @Override
        public void onClick(View v){
        if(v == mSearchRegion){
            String location = mLocation.getText().toString();
            Intent i = new Intent(MainActivity.this, ResultsActivity.class);
            i.putExtra("location", location);
            startActivity(i);
        }
    }
}
