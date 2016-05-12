package com.isabellegeorge.pinballwizard.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.ui.LocationDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/4/16.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.locationName) TextView locationName;
    @Bind(R.id.locationType) TextView locationType;
    private Context c;
    private ArrayList<com.isabellegeorge.pinballwizard.models.Location> mLocations = new ArrayList<>();

    public LocationsViewHolder(View itemView, ArrayList<com.isabellegeorge.pinballwizard.models.Location> locations) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        c = itemView.getContext();
        mLocations = locations;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                Intent i = new Intent(c, LocationDetailActivity.class);
                i.putExtra("startPosition", itemPosition+"");
                i.putExtra("locations", Parcels.wrap(mLocations));
                c.startActivity(i);
            }
        });
    }

    public void bindLocation(com.isabellegeorge.pinballwizard.models.Location location){
        locationName.setText(location.getName());
        locationType.setText(location.getLocationType());
    }
}
