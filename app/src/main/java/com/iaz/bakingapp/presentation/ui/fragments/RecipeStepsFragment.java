package com.iaz.bakingapp.presentation.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.FragmentRecipeIngredientsBinding;
import com.iaz.bakingapp.databinding.FragmentRecipeStepsBinding;
import com.iaz.bakingapp.models.Ingredient;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.presentation.ui.activities.RecipeActivity;
import com.iaz.bakingapp.presentation.ui.activities.RecipeStepsActivity;
import com.iaz.bakingapp.presentation.ui.adapters.RecipeIngredientsAdapter;
import com.iaz.bakingapp.presentation.ui.adapters.RecipeStepsAdapter;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment {


    private FragmentRecipeStepsBinding binding;
    private RecipeStepsAdapter stepsAdapter;
    private Recipe recipe;
    private Parcelable listPosition;

    public static RecipeStepsFragment newInstance(Recipe recipe) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.RECIPE_BUNDLE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(Constants.RECIPE_BUNDLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_recipe_steps, container, false);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(Constants.RECIPE_BUNDLE);
            listPosition = savedInstanceState.getParcelable(Constants.LIST_STATE);
        }

        if (recipe != null && recipe.getSteps() != null && !recipe.getSteps().isEmpty())
            setAdapter(recipe.getSteps());

        binding.btRecipeIngredients.setOnClickListener(view -> {

            if (getActivity() instanceof RecipeActivity) {
                ((RecipeActivity) getActivity()).fragmentManager.beginTransaction()
                        .replace(R.id.fl_items, RecipeIngredientsFragment.newInstance(recipe))
                        .commit();
            } else {
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }

    private void setAdapter(ArrayList<Step> steps) {

        if (stepsAdapter == null)
            stepsAdapter = new RecipeStepsAdapter(getActivity(), steps);
        else {
            stepsAdapter.setNewList(steps);
            stepsAdapter.notifyDataSetChanged();
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.recyclerView.setAdapter(stepsAdapter);

        if (listPosition != null)
            binding.recyclerView.getLayoutManager().onRestoreInstanceState(listPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.RECIPE_BUNDLE, recipe);
        outState.putParcelable(Constants.LIST_STATE, binding.recyclerView.getLayoutManager().onSaveInstanceState());
    }

}
