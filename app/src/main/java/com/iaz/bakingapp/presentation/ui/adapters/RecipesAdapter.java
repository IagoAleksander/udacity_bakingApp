package com.iaz.bakingapp.presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.ItemRecipeBinding;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.presentation.ui.activities.RecipeActivity;
import com.iaz.bakingapp.presentation.ui.activities.RecipeIngredientsActivity;
import com.iaz.bakingapp.presentation.ui.activities.RecipeStepsActivity;
import com.iaz.bakingapp.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipiesList;
    private final Context context;

    public RecipesAdapter(@NonNull Context context, ArrayList<Recipe> recipiesList) {

        this.context = context;
        this.recipiesList = recipiesList;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_recipe, parent, false);


        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder recipeViewHolder, int i) {

        final Recipe recipe = recipiesList.get(i);

        if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
            recipeViewHolder.binding.ivPicture.setVisibility(View.VISIBLE);
            Picasso.with(context).load(recipe.getImage()).into(recipeViewHolder.binding.ivPicture);
        } else {
            recipeViewHolder.binding.ivPicture.setVisibility(View.GONE);
        }

        if (recipe.getName() != null && !recipe.getName().isEmpty()) {
            recipeViewHolder.binding.tvName.setText(recipe.getName());
            recipeViewHolder.binding.tvName.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvName.setVisibility(View.GONE);
        }

        if (recipe.getServings() != null && !recipe.getServings().isEmpty()) {
            recipeViewHolder.binding.tvServings.setText(String.format("Servings: %s", recipe.getServings()));
            recipeViewHolder.binding.tvServings.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvServings.setVisibility(View.GONE);
        }

        recipeViewHolder.binding.cvRecipe.setOnClickListener(view -> {

            if (context.getResources().getBoolean(R.bool.is_tablet)) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(Constants.RECIPE_BUNDLE, recipe);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, RecipeIngredientsActivity.class);
                intent.putExtra(Constants.RECIPE_BUNDLE, recipe);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipiesList.size();
    }

    public void setNewList(ArrayList<Recipe> results) {
        this.recipiesList = results;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeBinding binding;

        RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
