package techflake.com.techflake.controller;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.List;

import androidx.annotation.NonNull;
import io.objectbox.Box;
import techflake.com.techflake.R;
import techflake.com.techflake.Utils.BundleBuilder;
import techflake.com.techflake.Utils.ObjectBox;
import techflake.com.techflake.databinding.VideoControllerBinding;
import techflake.com.techflake.models.video.Original;
import techflake.com.techflake.models.video.Original_;

public class VideoViewController extends Controller {

    private static String TAG="VideoViewController";
    VideoControllerBinding videoControllerBinding;
    /*Video Player variable*/
    SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer videoPlayer;
    TrackSelector trackSelector;
    LoadControl loadControl;
    DefaultDataSourceFactory dataSourceFactory;
    MediaSource mediaSource;

    /*View data variable*/
    private static final String KEY_VIDEO = "KEY_VIDEO";
    private Original original = getArgs().getParcelable(KEY_VIDEO);
    /*ObjectBox variable*/
    private Box<Original> originalBox;

    public VideoViewController(Original original) {

        this(new BundleBuilder(new Bundle())
                .putParcelable(KEY_VIDEO, original)
                .build());
        setHasOptionsMenu(true);
    }

    public VideoViewController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        videoControllerBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),R.layout.video_controller,
                container,false);
        CreateVidepPlayerView();

        originalBox = ObjectBox.get().boxFor(Original.class);
        if(originalBox == null)
            ObjectBox.init(getActivity());
        return videoControllerBinding.getRoot();
    }

    private void CreateVidepPlayerView() {

        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        loadControl = new DefaultLoadControl();
        // 3. Create the player
        simpleExoPlayerView = new SimpleExoPlayerView(getActivity());
        simpleExoPlayerView = videoControllerBinding.playerVideoView;
        //Set media controller
        simpleExoPlayerView.setUseController(false);
        simpleExoPlayerView.requestFocus();

        PlayLiveMediaPlayer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Original> selectOriginal = originalBox.query().equal(Original_.hash, original.getHash()).build().find();
                if(selectOriginal.size() == 0){
                    videoControllerBinding.imgFavorite.setVisibility(View.GONE);
                }else{
                    videoControllerBinding.imgFavorite.setVisibility(View.VISIBLE);
                }
            }
        },500);

        videoControllerBinding.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Original> selectOriginal = originalBox.query().equal(Original_.hash, original.getHash()).build().find();
                originalBox.remove(selectOriginal);
                videoControllerBinding.imgUnFavorite.setVisibility(View.VISIBLE);
                videoControllerBinding.imgFavorite.setVisibility(View.GONE);
            }
        });

        videoControllerBinding.imgUnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalBox.put(original);
                videoControllerBinding.imgUnFavorite.setVisibility(View.GONE);
                videoControllerBinding.imgFavorite.setVisibility(View.VISIBLE);

            }
        });

    }

    public void PlayLiveMediaPlayer(){
        //String videoUrl = "https://media3.giphy.com/media/10mmN7OHKHsW9q/giphy.mp4";
        String videoUrl = original.getMp4();
        Log.d("videoUrl","videoUrl = "+videoUrl);
        Uri mp4VideoUri = Uri.parse(videoUrl);
        videoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(videoPlayer);

        Log.d("mp4VideoUri","mp4VideoUri = "+mp4VideoUri.getPath());

        //Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "mediaPlayerSample"), bandwidthMeterA);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        mediaSource = new ExtractorMediaSource(mp4VideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        videoPlayer.prepare(mediaSource);
        videoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG, "Listener-onTimelineChanged..."+timeline.getPeriodCount());
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged...");

            }
            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG, "Listener-onLoadingChanged...isLoading:"+isLoading);
                if(isLoading){

                }
            }

            @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
                if (playbackState == ExoPlayer.STATE_ENDED){
                    videoPlayer.prepare(mediaSource);
                }
            }


            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError..."+ error.getMessage());
               /* livePlayer.stop();
                livePlayer.prepare(loopingSource);
                livePlayer.setPlayWhenReady(true);*/

            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG, "Listener-onPositionDiscontinuity...");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.v(TAG, "Listener-onPlaybackParametersChanged...");
            }
        });

        videoPlayer.setPlayWhenReady(true); //run file/link when ready to play.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
