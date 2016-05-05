package com.isabellegeorge.pinballwizard.ui;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.services.PinballService;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.Region;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.regionList) ListView regionList;
    @Bind(R.id.rememberLocation) CheckBox mSaveRegion;
    private SharedPreferences mSharedPreferences;
    public ArrayList<Region> mRegions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

//        LayoutInflater inflater = getLayoutInflater();
//        View text = inflater.inflate(R.layout.instructions_toast, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
//            .setView(text)
//            .setPositiveButton("OK", new
//                DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface instructions_toast, int BUTTON_POSITIVE) {
//                        if(checkBox.isChecked()){
//                            mSharedPreferences.edit().putBoolean("noShow", true);
//                        }
//                    }
//                });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();

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
                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, regionNames);
                        regionList.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
