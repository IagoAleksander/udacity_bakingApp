package com.iaz.bakingapp.presentation.ui.activities;

import android.os.Bundle;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.presentation.ui.fragments.RecipeStepsFragment;
import com.iaz.bakingapp.util.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class RecipeStepsActivity extends AppCompatActivity {

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
            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fv_fragment, recipeStepsFragment)
                    .commit();
        }

    }

}
