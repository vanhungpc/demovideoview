package com.videoview.demoprojectvideoview;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.natasa.progressviews.CircleProgressBar;

/**
 * Created by Vanhungpc on 2/27/2016.
 */
public class CountdownTimerHandle extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    private Context context;
    boolean isPlayVideo;
    CircleProgressBar circleProgress1;
    public static  long currTime = 0;
    public CountdownTimerHandle(Context context, long millisInFuture, long countDownInterval, boolean isPlayVideo, CircleProgressBar circleProgress1) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.isPlayVideo = isPlayVideo;
        this.circleProgress1 = circleProgress1;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long millis = millisUntilFinished;
        if(isPlayVideo){
            cancel();
            currTime = millis;
        }else {
            int seconds = (int) (millis / 1000) % 60;
            int minutes = (int) ((millis / (1000 * 60)) % 60);
            String text = String.format("%02d:%02d", minutes, seconds);
            circleProgress1.setText(text, Color.DKGRAY);
        }

    }

    @Override
    public void onFinish() {

    }
}
