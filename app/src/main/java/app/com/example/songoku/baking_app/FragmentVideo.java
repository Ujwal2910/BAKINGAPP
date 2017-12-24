package app.com.example.songoku.baking_app;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by songoku on 19/12/17.
 */

public class FragmentVideo extends Fragment {

    private String thumbnailURL;
    private String videoURL;
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private static final String PLAYWHENREADY = "ready_to_play";
    private static final String PLAYBACKPOSITION = "play_back";
    private static final String CURRENTWINDOW = "window_current";
    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady = true;
    public ImageView novideo;

    public FragmentVideo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(PLAYWHENREADY);
            currentWindow = savedInstanceState.getInt(CURRENTWINDOW);
            playbackPosition = savedInstanceState.getLong(PLAYBACKPOSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        thumbnailURL = getArguments().getString("bThumb");
        videoURL = getArguments().getString("bVideo");
        novideo= (ImageView) rootView.findViewById(R.id.no_video_image);

        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.fragment_video_player);
        if(videoURL == null) {
//            simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeFile(thumbnailURL));
//            simpleExoPlayerView.setDefaultArtwork(getResources().getDrawable(R.drawable.no_video));
            simpleExoPlayerView.setVisibility(View.GONE);
            novideo.setVisibility(View.VISIBLE);
            novideo.setImageResource(R.drawable.no_video);
        }

        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAYWHENREADY, playWhenReady);
        outState.putInt(CURRENTWINDOW, currentWindow);
        outState.putLong(PLAYBACKPOSITION, playbackPosition);
    }
    private MediaSource buildMediaSource(Uri uri)
    {
        return new ExtractorMediaSource(uri
                , new DefaultHttpDataSourceFactory("ua")
                , new DefaultExtractorsFactory(), null,
                null);
    }

    private void initializePlayer()
    {

        if (videoURL == null)
        {return;}
        simpleExoPlayer = ExoPlayerFactory.
                newSimpleInstance(new DefaultRenderersFactory(getActivity())
                        , new DefaultTrackSelector(),
                        new DefaultLoadControl());
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource);
    }

    private void releasePlayer()
    {
        if (simpleExoPlayer != null){
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private void hideSystemUI()
    {
        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        hideSystemUI();
        if (Util.SDK_INT > 23){
            initializePlayer();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        hideSystemUI();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null){
            initializePlayer();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (Util.SDK_INT > 23){
            releasePlayer();
        }
    }
}
