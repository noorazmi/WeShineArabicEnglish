package com.moderneng.framents;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.VideoView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.activities.MazeMenuActivity;
import com.moderneng.providers.CustomAPEZProvider;
import com.moderneng.utils.AppConstant;

public class GameEndVideoFragment extends BaseFragment {

    private String mVideoFileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);
        int videoDuration = getArguments().getInt(AppConstant.BUNDLE_EXTRA_VIDEO_DURATION);
        View v = getFragmentView();
        VideoView mVideoView = (VideoView) v.findViewById(R.id.videoview);
        mVideoFileName = getArguments().getString(AppConstant.VIDEO_FILE_NAME);
        Uri uri = CustomAPEZProvider.buildUri(WeShineApp.MEDIA_FILE_BASE_PATH + mVideoFileName + ".mp4");
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.setOnCompletionListener(this);
        mVideoView.start();

        new CountDownTimer(videoDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 2500) {
                    startNextMaze();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.game_end_video_fragment;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp = null;
    }

    private void startNextMaze() {
        if (mVideoFileName.equals("maze1_end_video")) {
            ((MazeMenuActivity) getActivity()).AttachGameFragment(AppConstant.GAME_LEVEL_1);
        } else if (mVideoFileName.equals("maze2_end_video")) {
            ((MazeMenuActivity) getActivity()).AttachGameFragment(AppConstant.GAME_LEVEL_2);
        } else if (mVideoFileName.equals("maze3_end_video")) {
            ((MazeMenuActivity) getActivity()).AttachGameFragment(AppConstant.GAME_LEVEL_3);
        } else if (mVideoFileName.equals("maze4_end_video")) {
            ((MazeMenuActivity) getActivity()).AttachGameFragment(AppConstant.GAME_LEVEL_4);
        } else if (mVideoFileName.equals("maze5_end_video")) {
            ((MazeMenuActivity) getActivity()).gotToMazeGameMenu();
        }
    }

    @Override
    protected void onAudioComplete(String audioFileId) {
    }

}
