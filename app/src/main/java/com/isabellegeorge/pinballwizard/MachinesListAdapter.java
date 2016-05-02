package com.isabellegeorge.pinballwizard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Epicodus on 4/29/16.
 */
public class MachinesListAdapter extends RecyclerView.Adapter<MachinesListAdapter.MachinesViewHolder>{
    private ArrayList<Machine> machines = new ArrayList<>();
    private Context c;


    public MachinesListAdapter(Context c, ArrayList<Machine> machines){
        this.machines = machines;
        this.c = c;
        Log.v("yo", "yo");

    }

    @Override
    public MachinesListAdapter.MachinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        MachinesViewHolder viewHolder = new MachinesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MachinesListAdapter.MachinesViewHolder holder, int position){
        holder.bindMachine(machines.get(position));
    }

    @Override
    public int getItemCount(){
        return machines.size();
    }


    public class MachinesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.locationName) TextView locationName;
        @Bind(R.id.locationType) TextView locationType;
        private Context c;

        public MachinesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            c = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    Intent i = new Intent(c, MachineDetailActivity.class);
                    //putExtras
                    c.startActivity(i);
                }
            });
        }

        public void bindMachine(Machine location){
            locationName.setText(location.getMachineName());
            locationType.setText(location.getManufacturer());
        }
    }
}
