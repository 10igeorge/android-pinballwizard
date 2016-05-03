package com.isabellegeorge.pinballwizard.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.models.Machine;
import com.isabellegeorge.pinballwizard.services.PinballService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LocationDetailFragment extends Fragment {
    @Bind(R.id.locationTypeFragment) TextView mTypeLabel;
    @Bind(R.id.locationNameFragment) TextView mNameLabel;
    @Bind(R.id.numberMachineFragment) TextView mNumberMachines;
    @Bind(R.id.saveLocationButton) Button mSaveLocation;
    @Bind(R.id.addressFragment) TextView mAddressLocation;
    @Bind(R.id.locationMachines) ListView mMachines;
    private Location mLocation;

    public static LocationDetailFragment newInstance(Location location){
        LocationDetailFragment locationDetailFragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("location", Parcels.wrap(location));
        locationDetailFragment.setArguments(args);
        return locationDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mLocation = Parcels.unwrap(getArguments().getParcelable("location"));
    }




    public LocationDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
        ButterKnife.bind(this, view);

        mNameLabel.setText(mLocation.getLocationName());
        mTypeLabel.setText(mLocation.getLocationType());
        ArrayAdapter<Machine> machineAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, mLocation.getMachines());
        mMachines.setAdapter(machineAdapter);
        Log.v("MACHINAS", ""+mLocation.getMachines());
        mNumberMachines.setText(mLocation.getNumberMachines()+" Machines");
        mAddressLocation.setText(mLocation.getAddress() + " " + mLocation.getCity() + " " + mLocation.getState() + " " + mLocation.getZip());
        return view;
    }

}
