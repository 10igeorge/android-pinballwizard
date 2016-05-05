package com.isabellegeorge.pinballwizard.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.isabellegeorge.pinballwizard.Constants;
import com.isabellegeorge.pinballwizard.R;
import com.isabellegeorge.pinballwizard.models.User;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.createUserButton) Button mNewUser;
    @Bind(R.id.nameEditText) EditText mName;
    @Bind(R.id.emailEditText) EditText mEmail;
    @Bind(R.id.passwordEditText) EditText mPassword;
    @Bind(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @Bind(R.id.loginTextView) TextView mLogin;
    private Firebase ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        ref = new Firebase(Constants.FIREBASE_URL);
        mNewUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mNewUser){
            createNewUser();
        }
    }

    public void createNewUser(){
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String confirmPassword = mConfirmPassword.getText().toString();
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result){
                String uid = result.get("uid").toString();
                saveUserInFirebase(name, email, uid);
            }

            @Override
            public void onError(FirebaseError e){
                Log.d("Error", "Error occurred" + e);
            }
        });
    }

    private void saveUserInFirebase(final String name, final String email, final String uid){
        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(uid);
        User newUser = new User(name, email);
        userLocation.setValue(newUser);
    }
}
