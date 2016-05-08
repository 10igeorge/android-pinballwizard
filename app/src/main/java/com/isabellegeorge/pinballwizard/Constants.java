package com.isabellegeorge.pinballwizard;

/**
 * Created by Epicodus on 4/28/16.
 */
public class Constants {
    public static final String REGION_URL = "http://pinballmap.com/api/v1/regions";
    public static final String LOCATIONS_URL = "http://pinballmap.com/api/v1/region/";
    public static final String MACHINES_REGION_URL = "http://pinballmap.com/api/v1/region/";
    public static final String LOCATION_TYPES_URL = "http://pinballmap.com/api/v1/location_types";
    public static final String PREFERENCES_REGION_KEY = "region";
    public static final String PREFERENCES_CITY_KEY = "city";
    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATIONS = "locations";
    public static final String FIREBASE_URL_LOCATIONS = FIREBASE_URL + "/" + FIREBASE_LOCATIONS;
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String KEY_UID = "UID";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";
}
