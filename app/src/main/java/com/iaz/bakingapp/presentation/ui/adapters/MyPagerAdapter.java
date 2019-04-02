package com.iaz.bakingapp.presentation.ui.adapters;

import android.util.SparseArray;
import android.view.ViewGroup;

import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.presentation.ui.fragments.StepDetailsFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    final private ArrayList<Step> stepsList;
    final private SparseArray<StepDetailsFragment> mFragmentsHolded = new SparseArray<>();

    public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Step> stepsList) {
        super(fragmentManager);
        this.stepsList = stepsList;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return stepsList.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return StepDetailsFragment.newInstance(stepsList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        if (fragment instanceof StepDetailsFragment) {
            mFragmentsHolded.append(position, (StepDetailsFragment) fragment);
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragmentsHolded.delete(position);
//        super.destroyItem(container, position, object);
    }

    public StepDetailsFragment getCachedItem(int position) {
        return mFragmentsHolded.get(position, null);
    }

}