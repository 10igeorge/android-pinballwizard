package com.isabellegeorge.pinballwizard.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.isabellegeorge.pinballwizard.models.Location;
import com.isabellegeorge.pinballwizard.ui.LocationDetailFragment;

import java.util.ArrayList;

/**
 * Created by Epicodus on 5/2/16.
 */
public class LocationPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Location> mLocations;

    public LocationPagerAdapter(FragmentManager fm, ArrayList<Location> locations){
        super(fm);
        mLocations = locations;
    }

    @Override
    public Fragment getItem(int position){
        return LocationDetailFragment.newInstance(mLocations.get(position));
    }

    @Override
    public int getCount(){
        return mLocations.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mLocations.get(position).getLocationName();
    }
}


