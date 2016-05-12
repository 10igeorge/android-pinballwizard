package com.isabellegeorge.pinballwizard.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    @Bind(R.id.numberMachines) TextView mNumberMachines;
    @Bind(R.id.saveLocationButton) Button mSaveLocation;
    @Bind(R.id.addressFragment) TextView mAddressLocation;
    @Bind(R.id.locationMachines) ListView mMachines;
    @Bind(R.id.loadingMachines) ProgressBar mLoading;
    @Bind(R.id.selectedMachineName) TextView mSelectedMachineName;
    @Bind(R.id.selectedMachineInfo) TextView mSelectedMachineInfo;
    @Bind(R.id.selectedMachine) RelativeLayout mSelectedMachine;
    private ArrayList<Machine> machines = new ArrayList<>();
    private int machineCount = 0;
    private Location mLocation;
    private SharedPreferences mSharedPref;

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
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    public LocationDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        machineCount = 0;
        setMachinesForLocation(mLocation.getUrlPath());
        View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
        ButterKnife.bind(this, view);
        mAddressLocation.setOnClickListener(this);
        mSaveLocation.setOnClickListener(this);
        mNameLabel.setText(mLocation.getName());
        mTypeLabel.setText(mLocation.getLocationType());
        mAddressLocation.setText(mLocation.getAddress() + " " + mLocation.getCity() + ", " + mLocation.getState() + " " + mLocation.getZip());
        return view;
    }

    public void setMachinesForLocation(String name){
        final PinballService pinballService = new PinballService();
        pinballService.findMachinesInRegion(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                machines = pinballService.processMachinesForLocation(response, mLocation.getId());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String[] machineNames = new String[machines.size()];
                        for(int i=0; i<machineNames.length; i++){
                            machineNames[i] = machines.get(i).getMachineName();
                            machineCount ++;
                        }

                        if(machineNames.length < 1){
                            mLoading.setVisibility(View.VISIBLE);
                        } else {
                            mLoading.setVisibility(View.GONE);
                            mNumberMachines.setText(machineCount + " Machines");
                        }

                        ArrayAdapter<String> machineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, machineNames);
                        mMachines.setAdapter(machineAdapter);
                        mMachines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String item = mMachines.getItemAtPosition(i).toString();
                                for(Machine machine: machines){
                                    if(item.equals(machine.getMachineName())){
                                        mMachines.setVisibility(View.INVISIBLE);
                                        mSelectedMachine.setVisibility(View.VISIBLE);
                                        mSelectedMachineName.setText(machine.getMachineName());
                                        mSelectedMachineInfo.setText(machine.getManufacturer() + " (" + machine.getMachineYear()+")");
                                    }
                                }
                            }
                        });
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
                String uid = mSharedPref.getString(Constants.KEY_UID, null);
                Firebase ref = new Firebase(Constants.FIREBASE_URL_LOCATIONS).child(uid);
                Firebase pushRef = ref.push();
                mLocation.setPushId(pushRef.getKey());
                pushRef.setValue(mLocation);
                Toast.makeText(getContext(), "Location saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
