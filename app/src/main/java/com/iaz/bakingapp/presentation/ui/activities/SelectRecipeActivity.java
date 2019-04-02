package com.iaz.bakingapp.presentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.ActivityMainBinding;
import com.iaz.bakingapp.models.Recipe;
import com.iaz.bakingapp.networkUtils.BakingInfoApi;
import com.iaz.bakingapp.presentation.ui.adapters.RecipesAdapter;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SelectRecipeActivity extends AppCompatActivity {

    private RecipesAdapter recipesAdapter;
    private ActivityMainBinding binding;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Parcelable listPosition;
    private int columnsNumber = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        columnsNumber = getResources().getInteger(R.integer.columns_number);

        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(Constants.RECIPE_BUNDLE);
            listPosition = savedInstanceState.getParcelable(Constants.LIST_STATE);
            setAdapter(recipes);
        } else {
            BakingInfoApi.getBakingInfo((new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {

                    if (response.body() != null) {
                        recipes = new ArrayList<>(response.body());
                        setAdapter(recipes);
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(SelectRecipeActivity.this, getString(R.string.error_general), Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    private void setAdapter(ArrayList<Recipe> results) {

        if (recipesAdapter == null)
            recipesAdapter = new RecipesAdapter(SelectRecipeActivity.this, results);
        else {
            recipesAdapter.setNewList(results);
            recipesAdapter.notifyDataSetChanged();
        }

        binding.recyclerView.setLayoutManager(new GridLayoutManager(SelectRecipeActivity.this, columnsNumber));

//        Utilities.BottomOffsetDecoration bottomOffsetDecoration = new Utilities.BottomOffsetDecoration(8);
//        binding.recyclerMovies.addItemDecoration(bottomOffsetDecoration);
        binding.recyclerView.setAdapter(recipesAdapter);

        if (listPosition != null)
            binding.recyclerView.getLayoutManager().onRestoreInstanceState(listPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (recipes != null) {
            outState.putParcelableArrayList(Constants.RECIPE_BUNDLE, recipes);
            outState.putParcelable(Constants.LIST_STATE, binding.recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }
}
