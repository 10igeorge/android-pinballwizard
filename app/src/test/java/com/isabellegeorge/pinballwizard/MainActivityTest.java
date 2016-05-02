package com.isabellegeorge.pinballwizard;

import android.content.Intent;
import android.os.Build;
import android.widget.EditText;

import com.isabellegeorge.pinballwizard.ui.MainActivity;
import com.isabellegeorge.pinballwizard.ui.ResultsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateEditTextOnMainActivity(){
        EditText locationEditText = (EditText) activity.findViewById(R.id.locationEditText);
        assertTrue("Enter zip code".equals(locationEditText.getHint().toString()));
    }

    @Test
    public void resultsActivityInstantiated(){
        activity.findViewById(R.id.searchRegionButton).performClick();
        Intent expected = new Intent(activity, ResultsActivity.class);
        ShadowActivity shadow = org.robolectric.Shadows.shadowOf(activity);
        Intent actual = shadow.getNextStartedActivity();
        assertTrue(actual.filterEquals(expected));
    }
}
