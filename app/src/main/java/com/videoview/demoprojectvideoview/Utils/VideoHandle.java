package com.videoview.demoprojectvideoview.Utils;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.videoview.demoprojectvideoview.DTO.VideoDTO;
import com.videoview.demoprojectvideoview.R;

/**
 * Created by Vanhungpc on 3/3/2016.
 */
public class VideoHandle {
    private static VideoHandle instance;
    public static VideoHandle getInstance(){
        if (instance == null)
            instance = new VideoHandle();
        return  instance;
    }

    public void autoNextVideo(int currVideo, int mMillisInFuture, int timeOption, int currArray, VideoView mVideoView,  VideoDTO _video) {
        currVideo = 0;
        int checkautoNext = TimeSetup.getInstance().checkAuToNextDownTime(mMillisInFuture, timeOption);
        if (checkautoNext == 1) {
            currArray = 0;
            currVideo++;
            playVideo(_video.get_arrList().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 2) {
            currArray = 1;
            currVideo++;
            playVideo(_video.get_arrList1().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 3) {
            currArray = 1;
            currVideo++;
            playVideo(_video.get_arrList1().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 4) {
            currArray = 2;
            currVideo++;
            playVideo(_video.get_arrList2().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 5) {
            currArray = 2;
            currVideo++;
            playVideo(_video.get_arrList2().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 6) {
            currArray = 3;
            currVideo++;
            playVideo(_video.get_arrList3().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 7) {
            currArray = 3;
            currVideo++;
            playVideo(_video.get_arrList3().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 8) {
            currArray = 4;
            currVideo++;
            playVideo(_video.get_arrList4().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 9) {
            currArray = 4;
            currVideo++;
            playVideo(_video.get_arrList4().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 10) {
            currArray = 5;
            currVideo++;
            playVideo(_video.get_arrList5().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 11) {
            currArray = 5;
            currVideo++;
            playVideo(_video.get_arrList5().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 12) {
            currArray = 6;
            currVideo++;
            playVideo(_video.get_arrList6().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        } else if (checkautoNext == 13) {
            currArray = 6;
            currVideo++;
            playVideo(_video.get_arrList6().get(currVideo), mVideoView);
            mVideoView.requestFocus();
            mVideoView.start();
        }
    }

    //play video by url
    public void playVideo(String url, VideoView mVideoView) {
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mVideoView.requestFocus();
    }

}
