package com.iaz.bakingapp.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.ActivityStepDetailsBinding;
import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.presentation.ui.adapters.MyPagerAdapter;
import com.iaz.bakingapp.presentation.ui.fragments.StepDetailsFragment;
import com.iaz.bakingapp.util.Constants;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

public class StepDetailsActivity extends AppCompatActivity {


    private ArrayList<Step> stepsList;
    private int position = 0;
    private MyPagerAdapter adapterViewPager;

    private ActivityStepDetailsBinding binding;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_details);

        if (savedInstanceState != null) {
            stepsList = savedInstanceState.getParcelableArrayList(Constants.STEPS_ARRAY);
            position = savedInstanceState.getInt(Constants.STEP_ID, 0);
            mCurrentPosition = position;
        } else if (getIntent().getExtras() != null) {
            stepsList = getIntent().getParcelableArrayListExtra(Constants.STEPS_ARRAY);
            position = getIntent().getIntExtra(Constants.STEP_ID, 0);
            mCurrentPosition = position;
        }

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), stepsList);
        binding.viewPager.setAdapter(adapterViewPager);

        binding.previousButton.setOnClickListener(v -> {

            position--;
            binding.viewPager.setCurrentItem(position);
        });

        binding.nextButton.setOnClickListener(v -> {

            position++;
            binding.viewPager.setCurrentItem(position);
        });

        binding.previousButton.setVisibility(View.VISIBLE);
        binding.nextButton.setVisibility(View.VISIBLE);

        if (position == 0) {
            binding.previousButton.setVisibility(View.GONE);
        }
        if (position == stepsList.size() - 1) {
            binding.nextButton.setVisibility(View.GONE);
        }

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                binding.previousButton.setVisibility(View.VISIBLE);
                binding.nextButton.setVisibility(View.VISIBLE);

                if (position == 0) {
                    binding.previousButton.setVisibility(View.GONE);
                }
                if (position == stepsList.size() - 1) {
                    binding.nextButton.setVisibility(View.GONE);
                }

                StepDetailsFragment cachedFragmentLeaving = adapterViewPager.getCachedItem(mCurrentPosition);
                if (cachedFragmentLeaving != null) {
                    cachedFragmentLeaving.losingVisibility();
                }

                mCurrentPosition = position;
                StepDetailsFragment cachedFragmentEntering = adapterViewPager.getCachedItem(mCurrentPosition);
                if (cachedFragmentEntering != null) {
                    cachedFragmentEntering.gainVisibility();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.viewPager.setCurrentItem(position);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.STEPS_ARRAY, stepsList);
        outState.putInt(Constants.STEP_ID, position);
    }

}
