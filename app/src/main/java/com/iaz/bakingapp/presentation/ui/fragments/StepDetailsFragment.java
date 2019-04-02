package com.iaz.bakingapp.presentation.ui.fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.iaz.bakingapp.R;
import com.iaz.bakingapp.databinding.FragmentStepDetailsBinding;
import com.iaz.bakingapp.models.Step;
import com.iaz.bakingapp.util.Constants;

public class StepDetailsFragment extends Fragment {

    private Step step;
    private FragmentStepDetailsBinding binding;
    private SimpleExoPlayer mExoPlayer;
    private Uri mediaUri;
    private long videoPosition = C.TIME_UNSET;

    public static StepDetailsFragment newInstance(Step step) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.STEP_BUNDLE, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(Constants.STEP_BUNDLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_step_details, container, false);

        if (step != null) {
            if (step.getShortDescription() != null && !step.getShortDescription().isEmpty()) {
                binding.tvShortDescription.setText(step.getShortDescription());
                binding.tvShortDescription.setVisibility(View.VISIBLE);
            } else {
                binding.tvShortDescription.setVisibility(View.GONE);
            }

            if (step.getDescription() != null && !step.getDescription().isEmpty()
                    && (step.getShortDescription() == null
                    || (step.getShortDescription() != null && !step.getDescription().equals(step.getShortDescription())))) {
                binding.tvDescription.setText(step.getDescription());
                binding.tvDescription.setVisibility(View.VISIBLE);
            } else {
                binding.tvDescription.setVisibility(View.GONE);
            }

            binding.vvStepVideo.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.ic_launcher_background));

            // Initialize the player.
            if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
                mediaUri = Uri.parse(step.getVideoURL());
            } else if (step.getThumbnailURL() != null && !step.getThumbnailURL().isEmpty()) {
                mediaUri = Uri.parse(step.getThumbnailURL());
            } else {
                binding.vvStepVideo.setVisibility(View.GONE);
            }

            if (mediaUri != null)
                initializePlayer();


        }

        return binding.getRoot();
    }

    private void initializePlayer() {
        if (mExoPlayer == null && mediaUri != null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            binding.vvStepVideo.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);

            if (videoPosition != C.TIME_UNSET) {
                mExoPlayer.seekTo(videoPosition);
            }

            mExoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayer != null)
            releasePlayer();
    }

    private void savePlayerState() {
        if (mExoPlayer != null) {
            videoPosition = mExoPlayer.getCurrentPosition();
        }
    }

    /**
     * This method is only used by viewpager because the viewpager doesn't call onPause after
     * changing the fragment
     */
    public void losingVisibility() {
        if (mExoPlayer != null) {
            savePlayerState();
            releasePlayer();
        }
    }

    /**
     * This method is only used by viewpager because the viewpager doesn't call onPause after
     * changing the fragment
     */
    public void gainVisibility() {
        initializePlayer();
    }

}
