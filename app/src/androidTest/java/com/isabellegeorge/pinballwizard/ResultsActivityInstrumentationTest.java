package com.isabellegeorge.pinballwizard;

import android.support.test.rule.ActivityTestRule;

import com.isabellegeorge.pinballwizard.ui.ResultsActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class ResultsActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<ResultsActivity> activityTestRule =
            new ActivityTestRule<>(ResultsActivity.class);

    @Test
    public void spinnerSelect() {
        onView(withId(R.id.filterSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Machines"))).perform(click());
        onView(withId(R.id.filterSpinner)).check(matches(withSpinnerText("Machines")));
    }
}
