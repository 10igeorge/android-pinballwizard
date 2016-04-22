package com.isabellegeorge.pinballwizard;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        View layout = inflater.inflate(R.layout.instructions_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView mToastText = (TextView) layout.findViewById(R.id.toastText);
        mToastText.setText("");

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

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
