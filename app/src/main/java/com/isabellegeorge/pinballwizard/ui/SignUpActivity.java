package com.isabellegeorge.pinballwizard.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
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
    private SharedPreferences mSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        ref = new Firebase(Constants.FIREBASE_URL);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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

                ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        if(authData != null){
                            String uid = authData.getUid();
                            mSharedPref.edit().putString(Constants.KEY_UID, uid).apply();
                            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError e) {
                        switch(e.getCode()){
                            case FirebaseError.INVALID_EMAIL:
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                mEmail.setError("This email has not been registered");
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                mPassword.setError(e.getMessage());
                                break;
                            case FirebaseError.NETWORK_ERROR:
                                showErrorToast("There was a problem with the network connection");
                                break;
                            default:
                                showErrorToast(e.toString());
                        }
                    }
                });
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

    private void showErrorToast(String message){
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
