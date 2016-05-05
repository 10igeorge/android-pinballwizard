package com.isabellegeorge.pinballwizard.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.isabellegeorge.pinballwizard.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.registerTextView) TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mRegister){
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
            finish();
        }
    }
}
