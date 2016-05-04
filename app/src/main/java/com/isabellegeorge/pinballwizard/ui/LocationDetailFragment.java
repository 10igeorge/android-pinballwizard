package com.isabellegeorge.pinballwizard.ui;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.isabellegeorge.pinballwizard.Constants;
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

public class LocationDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.locationTypeFragment) TextView mTypeLabel;
    @Bind(R.id.locationNameFragment) TextView mNameLabel;
//    @Bind(R.id.numberMachineFragment) TextView mNumberMachines;
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


    public LocationDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setMachinesForLocation(mLocation.getUrlPath());

        View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
        ButterKnife.bind(this, view);
        mAddressLocation.setOnClickListener(this);
        mSaveLocation.setOnClickListener(this);
        mNameLabel.setText(mLocation.getName());
        mTypeLabel.setText(mLocation.getLocationType());
//        mNumberMachines.setText(mLocation.getNumberMachines()+" Machines");
        mAddressLocation.setText(mLocation.getAddress() + " " + mLocation.getCity() + ", " + mLocation.getState() + " " + mLocation.getZip());
        return view;
    }


    public void setMachinesForLocation(String name){
        final PinballService pinballService = new PinballService();
        final ArrayList<Machine> setMachines = new ArrayList<>();
        pinballService.findMachinesInRegion(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ArrayList<Machine> machines = pinballService.processMachinesInRegion(response);
                for(int i=0; i<machines.size(); i++){
                    if(machines.get(i).getLocationId() == mLocation.getId()){
                        setMachines.add(machines.get(i));
                    }
                }
                final String[] machineNames = new String[setMachines.size()];
                for(int i=0; i<machineNames.length; i++){
                    machineNames[i] = setMachines.get(i).getMachineName();
                }
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayAdapter<String> machineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, machineNames);
                        mMachines.setAdapter(machineAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case (R.id.addressFragment):
                Intent map = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:" + mLocation.getLat()
                                + "," + mLocation.getLon()
                                + "?q=(" + mLocation.getName() + ")"));
                startActivity(map);
                break;
            case (R.id.saveLocationButton):
                Firebase ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS);
                ref.push().setValue(mLocation);
                Toast.makeText(getContext(), "Location saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
