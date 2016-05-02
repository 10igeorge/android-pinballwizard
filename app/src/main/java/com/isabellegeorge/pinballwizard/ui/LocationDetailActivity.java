package com.isabellegeorge.pinballwizard.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.adapters.LocationPagerAdapter;
import com.isabellegeorge.pinballwizard.models.Location;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private LocationPagerAdapter adapterViewPager;
    ArrayList<Location> mLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        ButterKnife.bind(this);

        mLocations = Parcels.unwrap(getIntent().getParcelableExtra("locations"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("startPosition"));
        adapterViewPager = new LocationPagerAdapter(getSupportFragmentManager(), mLocations);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
