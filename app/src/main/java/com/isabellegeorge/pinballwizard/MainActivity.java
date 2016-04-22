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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.locationEditText) EditText mLocation;
    @Bind(R.id.searchRegionButton) Button mSearchRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSearchRegion.setOnClickListener(this);


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

    @Override
        public void onClick(View v){
        if(v == mSearchRegion){
            String location = mLocation.getText().toString();
            Intent i = new Intent(MainActivity.this, ResultsActivity.class);
            i.putExtra("location", location);
            startActivity(i);
        }
    }

}
