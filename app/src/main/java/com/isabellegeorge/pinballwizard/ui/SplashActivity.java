package com.isabellegeorge.pinballwizard.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.splashLogo) TextView mSplashLogo;
    private SharedPreferences mSharedPref;
    private Firebase ref = new Firebase(Constants.FIREBASE_URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ButterKnife.bind(this);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Typeface monoton = Typeface.createFromAsset(getAssets(), "fonts/Monoton-Regular.ttf");
        mSplashLogo.setTypeface(monoton);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mSharedPref.getBoolean("Remember User", false)){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 4000);
    }
}
