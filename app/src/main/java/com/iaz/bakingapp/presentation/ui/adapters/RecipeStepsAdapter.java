package com.iaz.bakingapp.presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.ItemStepBinding;
import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.presentation.ui.activities.RecipeActivity;
import com.iaz.bakingapp.presentation.ui.activities.RecipeStepsActivity;
import com.iaz.bakingapp.presentation.ui.activities.StepDetailsActivity;
import com.iaz.bakingapp.presentation.ui.fragments.RecipeStepsFragment;
import com.iaz.bakingapp.presentation.ui.fragments.StepDetailsFragment;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private ArrayList<Step> stepsList;
    private final Context context;

    public RecipeStepsAdapter(@NonNull Context context, ArrayList<Step> stepsList) {

        this.context = context;
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public RecipeStepsAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_step, parent, false);


        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapter.StepViewHolder recipeViewHolder, int i) {

        final Step step = stepsList.get(i);

        if (step.getShortDescription() != null && !step.getShortDescription().isEmpty()) {
            recipeViewHolder.binding.tvDescription.setText(String.format("%d. %s", i, step.getShortDescription()));
            recipeViewHolder.binding.tvDescription.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvDescription.setVisibility(View.GONE);
        }

        recipeViewHolder.binding.llStep.setOnClickListener(view -> {
            if (context instanceof RecipeActivity) {
                ((RecipeActivity) context).fragmentManager.beginTransaction()
                        .replace(R.id.fv_step_details, StepDetailsFragment.newInstance(stepsList.get(i)))
                        .commit();
            } else {
                Intent intent = new Intent(context, StepDetailsActivity.class);
                intent.putExtra(Constants.STEPS_ARRAY, stepsList);
                intent.putExtra(Constants.STEP_ID, i);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public void setNewList(ArrayList<Step> results) {
        this.stepsList = results;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        private final ItemStepBinding binding;

        StepViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
