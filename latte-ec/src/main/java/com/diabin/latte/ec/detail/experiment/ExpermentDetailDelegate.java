package com.diabin.latte.ec.detail.experiment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.alibaba.fastjson.JSONObject;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;

import butterknife.BindView;


/**
 * Created by fei on 2017/8/3.
 */

public class ExpermentDetailDelegate extends LatteDelegate{
    @BindView(R2.id.vv_experiment_detail)
    VideoView mVideoView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_experiment_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private void initVideo(JSONObject data) {
        String path = data.getString("video");
        final MediaController mc = new MediaController(this.getContext());
        mc.setMediaPlayer(new MediaController.MediaPlayerControl() {
            @Override
            public void start() {
                mVideoView.start();
            }

            @Override
            public void pause() {
                mVideoView.pause();
            }

            @Override
            public int getDuration() {
                return 0;
            }

            @Override
            public int getCurrentPosition() {
                return 0;
            }

            @Override
            public void seekTo(int pos) {

            }

            @Override
            public boolean isPlaying() {
                return false;
            }

            @Override
            public int getBufferPercentage() {
                return 0;
            }

            @Override
            public boolean canPause() {
                return false;
            }

            @Override
            public boolean canSeekBackward() {
                return false;
            }

            @Override
            public boolean canSeekForward() {
                return false;
            }

            @Override
            public int getAudioSessionId() {
                return 0;
            }
        });
        mVideoView.setMediaController(mc);
        mVideoView.setVideoPath(path);
    }
}
