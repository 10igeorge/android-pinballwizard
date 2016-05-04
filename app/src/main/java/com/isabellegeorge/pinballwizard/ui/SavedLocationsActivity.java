package com.isabellegeorge.pinballwizard.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.FirebaseLocationsListAdapter;
import com.isabellegeorge.pinballwizard.models.Location;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/4/16.
 */
public class SavedLocationsActivity extends AppCompatActivity {
    private Query mQuery;
    private Firebase ref;
    private FirebaseLocationsListAdapter mAdapter;
    @Bind(R.id.locationsWithPinball) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery(){
        String location = ref.toString();
        mQuery = new Firebase(location);
    }

    private void setUpRecyclerView(){
        mAdapter = new FirebaseLocationsListAdapter(mQuery, Location.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
