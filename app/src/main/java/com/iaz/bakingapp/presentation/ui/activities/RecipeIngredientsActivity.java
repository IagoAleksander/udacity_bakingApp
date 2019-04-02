package com.iaz.bakingapp.presentation.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.presentation.ui.fragments.RecipeIngredientsFragment;
import com.iaz.bakingapp.util.Constants;
import com.iaz.bakingapp.widget.BakingWidgetProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class RecipeIngredientsActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(Constants.RECIPE_BUNDLE);
        } else if (getIntent().getExtras() != null) {
            recipe = getIntent().getParcelableExtra(Constants.RECIPE_BUNDLE);
        }

        if (savedInstanceState == null && recipe != null) {
            RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fv_fragment, recipeIngredientsFragment)
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
}
