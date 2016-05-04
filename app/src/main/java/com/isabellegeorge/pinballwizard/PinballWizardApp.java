package com.isabellegeorge.pinballwizard;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Guest on 5/4/16.
 */
public class PinballWizardApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
