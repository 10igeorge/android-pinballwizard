package com.isabellegeorge.pinballwizard.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.FirebaseLocationsListAdapter;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.util.OnStartDragListener;
import com.isabellegeorge.pinballwizard.util.SimpleItemTouchHelperCallback;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SavedLocationsFragment extends Fragment implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    private Query query;
    private Firebase ref;
    private FirebaseLocationsListAdapter adapter;
    private SharedPreferences sharedPref;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    public static SavedLocationsFragment newInstance(Location location){
        SavedLocationsFragment savedLocationsFragment = new SavedLocationsFragment();
        return savedLocationsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS);
        String uid = sharedPref.getString(Constants.KEY_UID, null);
        String location = ref.child(uid).toString();
        query = new Firebase(location);
        adapter = new FirebaseLocationsListAdapter(query, Location.class, this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_locations, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
