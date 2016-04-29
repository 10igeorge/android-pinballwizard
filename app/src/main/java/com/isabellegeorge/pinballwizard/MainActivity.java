package com.isabellegeorge.pinballwizard;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.regionList) ListView regionList;
    public ArrayList<Region> mRegions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mSearchRegion.setOnClickListener(this);
        getRegions();

        LayoutInflater inflater = getLayoutInflater();
        View text = inflater.inflate(R.layout.instructions_toast, null);
        //TODO option for do not show again
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
            .setView(text)
            .setPositiveButton("Got it", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface instructions_toast, int BUTTON_POSITIVE) {
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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

//    @Override
//        public void onClick(View v){
//        if(v == mSearchRegion){
//            String location = mLocation.getText().toString();
//            Intent i = new Intent(MainActivity.this, ResultsActivity.class);
//            i.putExtra("location", location);
//            startActivity(i);
//        }
//    }
}
