package com.iaz.bakingapp;

import com.iaz.bakingapp.presentation.ui.activities.SelectRecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SelectRecipeTest {

    private static final String RECIPE_NAME_NUTELLA_PIE = "Nutella Pie";
    private static final String RECIPE_NAME_BROWNIES = "Brownies";
    private static final String RECIPE_NAME_YELLOW_CAKE = "Yellow Cake";
    private static final String RECIPE_NAME_CHEESECAKE = "Cheesecake";

    private static final String RECIPE_STEPS = "RECIPE STEPS";

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule
            = new ActivityTestRule<>(SelectRecipeActivity.class);

    @Test
    public void checkRecipeTitles() throws InterruptedException {

        Thread.sleep(5000);

        onView(withId(R.id.recycler_view)).perform(scrollToPosition(0));
        onView(withText(RECIPE_NAME_NUTELLA_PIE)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(scrollToPosition(1));
        onView(withText(RECIPE_NAME_BROWNIES)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(scrollToPosition(2));
        onView(withText(RECIPE_NAME_YELLOW_CAKE)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(scrollToPosition(3));
        onView(withText(RECIPE_NAME_CHEESECAKE)).check(matches(isDisplayed()));
    }

    @Test
    public  void clickRecipeItem() throws InterruptedException {

        Thread.sleep(5000);

        onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.bt_recipe_steps)).check(matches(withText(RECIPE_STEPS)));
    }
}