package com.iaz.bakingapp.presentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.models.Ingredient;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.presentation.ui.fragments.RecipeIngredientsFragment;
import com.iaz.bakingapp.presentation.ui.fragments.StepDetailsFragment;
import com.iaz.bakingapp.util.Constants;
import com.iaz.bakingapp.widget.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    public FragmentManager fragmentManager;
    public HashMap<String, Long> videoPositionHash = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(Constants.RECIPE_BUNDLE);
            videoPositionHash = (HashMap<String, Long>) savedInstanceState.getSerializable(Constants.VIDEO_STATE);
        } else if (getIntent().getExtras() != null) {
            recipe = getIntent().getParcelableExtra(Constants.RECIPE_BUNDLE);
        }

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null && recipe != null) {
            RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipe);
            StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance(recipe.getSteps().get(0));

            fragmentManager.beginTransaction()
                    .add(R.id.fl_items, recipeIngredientsFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .add(R.id.fv_step_details, stepDetailsFragment)
                    .commit();

        }

        //update widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, appWidgetIds, recipe);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, SelectRecipeActivity.class);
        intent.putExtra(Constants.RECIPE_BUNDLE, recipe);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.RECIPE_BUNDLE, recipe);
        outState.putSerializable(Constants.VIDEO_STATE, videoPositionHash);
    }
}
