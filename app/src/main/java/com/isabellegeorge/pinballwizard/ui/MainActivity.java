package com.isabellegeorge.pinballwizard.ui;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.adapters.LocationsListAdapter;
import com.isabellegeorge.pinballwizard.adapters.MachinesListAdapter;
import com.isabellegeorge.pinballwizard.services.PinballService;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends NavDrawerActivity {
    @Bind(R.id.regionList) ListView regionList;
    @Bind(R.id.rememberLocation) CheckBox mSaveRegion;
    private SharedPreferences mSharedPreferences;
    public ArrayAdapter adapter;
    public ArrayList<Region> searchedRegions = new ArrayList<>();
    public ArrayList<Region> mRegions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(contentView, 0);

        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(mSharedPreferences.getString(Constants.PREFERENCES_REGION_KEY, null) != null){
            startActivity(new Intent(this, ResultsActivity.class));
        } else {
            getRegions();
        }

        regionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = regionList.getItemAtPosition(i).toString();
                for (Region region: mRegions){
                    if(item.equals(region.getCity())){
                        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                        intent.putExtra("name", region.getUrlName());
                        intent.putExtra("city", item);
                        startActivity(intent);
                        if(mSaveRegion.isChecked()){
                            mSharedPreferences.edit().putString(Constants.PREFERENCES_REGION_KEY, region.getUrlName()).apply();
                            mSharedPreferences.edit().putString(Constants.PREFERENCES_CITY_KEY, region.getCity()).apply();
                            finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) MenuItemCompat.getActionView(item);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchedRegions = new ArrayList<>();
                searchList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void searchList(String query){
        boolean regionFound = false;
        if(!(query.trim().equals(""))){
            for(int i=0; i<mRegions.size(); i++){
                if(mRegions.get(i).getCity().toLowerCase().contains(query)){
                    searchedRegions.add(mRegions.get(i));
                    regionFound = true;
                }
            }
            if(searchedRegions.size() == 0 || !regionFound){
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_LONG).show();
            } else {
                String[] searchedNames = new String[searchedRegions.size()];
                for(int i=0; i<searchedNames.length; i++){
                    searchedNames[i] = searchedRegions.get(i).getCity();
                }
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, searchedNames);
                regionList.setAdapter(adapter);
            }
        }
    }

    private void getRegions(){
        final PinballService pinballService = new PinballService();
        pinballService.findRegions(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {

                mRegions = pinballService.processRegions(response);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] regionNames = new String[mRegions.size()];
                        for(int i=0; i<regionNames.length; i++){
                            regionNames[i] = mRegions.get(i).getCity();
                        }

                        Arrays.sort(regionNames);
                        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, regionNames);
                        regionList.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
