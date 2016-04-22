package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.splashLogo) TextView mSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ButterKnife.bind(this);

        Typeface monoton = Typeface.createFromAsset(getAssets(), "fonts/Monoton-Regular.ttf");
        mSplashLogo.setTypeface(monoton);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }
}
