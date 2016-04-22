package com.isabellegeorge.pinballwizard;

import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.containsString;

public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

//TODO: figure out why withText method doesn't work
//    @Test
//    public void validateEditText() {
//        onView(withId(R.id.locationEditText)).perform(typeText("97204"))
//                .check(matches(withText("97204")));
//    }
//
//    @Test
//    public void zipCodeIsSentToResultsActivity() {
//        String location = "78704";
//        onView(withId(R.id.locationEditText)).perform(typeText(location));
//        onView(withId(R.id.searchRegionButton)).perform(click());
//        onView(withId(R.id.locationsWithPinball)).check(matches
//                (withText("Pinball near " + location)));
//    }
}