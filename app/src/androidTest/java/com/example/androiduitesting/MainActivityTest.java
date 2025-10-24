package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void testAddCity() {
        // click the add button
        onView(withId(R.id.button_add)).perform(click());

        // type edmonton
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        // press confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // check that edmonton is in the list
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // add another
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // click button
        onView(withId(R.id.button_clear)).perform(click());

        // check if they exist
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Calgary")).check(doesNotExist());

    }


    @Test
    public void testListView() {
        // add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // check if at top
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches(withText("Edmonton")));

    }


    @Test
    public void testSwitchActivities() {
        // Create new city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the list item at position 0
        onData(org.hamcrest.Matchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Check to see if activity switched here
        onView(withId(R.id.received_text_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testCorrectCityNameOnClick() {
        // Create new city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on city
        onData(org.hamcrest.Matchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Check if text matches what was clicked
        onView(withId(R.id.received_text_view)).check(matches(withText("Edmonton")));
    }

    @Test
    public void testBackButtonClick() {
        // Create new city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on city
        onData(org.hamcrest.Matchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Click on back button
        onView(withId(R.id.button_back)).perform(click());

        // Check that list in in the same state as before (saved items and not just new activity)
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
