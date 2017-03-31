package com.example.brianturloff.moviedescription;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayMovieActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private VideoView mVideoView;
    private MediaController mController;
    private MediaMetadataRetriever mMetadataRetriever;
    private String fName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_movie);

        Intent in =  getIntent();
        fName = in.getStringExtra("fileName");
        mVideoView = (VideoView) findViewById(R.id.avideoview);
        mVideoView.setVideoPath(getString(R.string.urlprefix) + fName);
        //mVideoView.setVideoPath(getString(R.string.urlprefix) + "Crazy.mp3");
        MediaController mediaController = new MediaController((Context)this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setOnPreparedListener(this);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        android.util.Log.d(this.getClass().getSimpleName(), "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }



    @Override
    public void onPrepared(MediaPlayer mp) {
        android.util.Log.d(this.getClass().getSimpleName(), "onPrepared called. Video Duration: "
                + mVideoView.getDuration());
        mVideoView.start();
    }
}
