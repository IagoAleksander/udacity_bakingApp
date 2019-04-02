package com.iaz.bakingapp.presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.ItemIngredientBinding;
import com.iaz.bakingapp.databinding.ItemStepBinding;
import com.iaz.bakingapp.models.Ingredient;
import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.presentation.ui.activities.RecipeStepsActivity;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> ingredientsList;

    public RecipeIngredientsAdapter(@NonNull Context context, ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public RecipeIngredientsAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false);


        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapter.IngredientViewHolder recipeViewHolder, int i) {

        final Ingredient ingredient = ingredientsList.get(i);

        if (ingredient != null && ingredient.getIngredient() != null && !ingredient.getIngredient().isEmpty()
                && ingredient.getQuantity() != null && !ingredient.getQuantity().isEmpty()
                && ingredient.getMeasure() != null && !ingredient.getMeasure().isEmpty()) {
            recipeViewHolder.binding.tvDescription.setText(String.format("- %s %s of %s", ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
            recipeViewHolder.binding.tvDescription.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvDescription.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void setNewList(ArrayList<Ingredient> results) {
        this.ingredientsList = results;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
