package com.isabellegeorge.pinballwizard.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.FirebaseLocationsListAdapter;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.util.OnStartDragListener;
import com.isabellegeorge.pinballwizard.util.SimpleItemTouchHelperCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedLocationsActivity extends NavDrawerActivity implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    private Query query;
    private Firebase ref;
    private FirebaseLocationsListAdapter mAdapter;
    private SharedPreferences sharedPref;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.noLocations) TextView mNoLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_saved_locations, null, false);
        drawer.addView(contentView, 0);
        ButterKnife.bind(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS);
        String uid = sharedPref.getString(Constants.KEY_UID, null);
        String location = ref.child(uid).toString();
        query = new Firebase(location);

        setUpRecyclerView();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseLocationsListAdapter(query, Location.class, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
