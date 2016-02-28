package com.videoview.demoprojectvideoview;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.natasa.progressviews.CircleProgressBar;
import com.natasa.progressviews.utils.OnProgressViewListener;
import com.natasa.progressviews.utils.ProgressStartPoint;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements View.OnClickListener, OnCompletionListener {
    @InjectView(R.id.mVideoView)
    VideoView mVideoView;
    @InjectView(R.id.btnOption1)
    Button btnOption1;
    @InjectView(R.id.btnOption2)
    Button btnOption2;
    @InjectView(R.id.circle_progress1)
    CircleProgressBar circleProgress1;
    @InjectView(R.id.imgPreview)
    LinearLayout imgPreview;
    @InjectView(R.id.imgPlay)
    LinearLayout imgPlay;
    @InjectView(R.id.imgPause)
    LinearLayout imgPause;
    @InjectView(R.id.imgNext)
    LinearLayout imgNext;
    private CircleProgressBar circleProgressBar1;
    private Runnable mTimer;
    int progress;
    private Handler mHandler;
    private CountDownTimer countDownTimer;
    int millisToGo = 0 * 1000 + 7 * 1000 * 60;
    int currVideo = 0;
    MediaPlayer mPlayer;
    MediaPlayer mPlayerNextPreview;
    ArrayList<String> arrList = new ArrayList<String>();
    ArrayList<String> arrList1 = new ArrayList<String>();
    ArrayList<String> arrList2 = new ArrayList<String>();
    ArrayList<String> arrList3 = new ArrayList<String>();
    ArrayList<String> arrList4 = new ArrayList<String>();
    ArrayList<String> arrList5 = new ArrayList<String>();
    ArrayList<String> arrList6 = new ArrayList<String>();
    int[] arrVideo = {R.raw.video1, R.raw.video2, R.raw.video3, R.raw.video4, R.raw.video5, R.raw.video6, R.raw.video7};
    String fileName = "";

    boolean isPlayVideo = false;
    boolean isPausedVideo = false;
    boolean isProgressVideo = false;
    int timeOption = 3 * 100;
    long millisCurr = 0;
    int currPossition = 0;
    //    CountdownTimerHandle handleTime;
    int currentTime = 0;
    private Handler handlerCurrTime;
    int currArray = 0;

    int Currentperiod = 0;

    Handler tickResponseHandler = new Handler() {
        public void handleMessage(Message msg) {
            currentTime = (Integer.parseInt(msg.obj.toString()));
            //make toast or do what you want
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mHandler = new Handler();

        handlerCurrTime = new Handler();
        imgNext.setOnClickListener(this);
        imgPreview.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgPause.setOnClickListener(this);
        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);

        circleProgress1.setStartPositionInDegrees(ProgressStartPoint.DEFAULT);
        circleProgress1.setLinearGradientProgress(true);
        circleProgress1.setOnClickListener(this);
        circleProgress1.setOnProgressViewListener(new OnProgressViewListener() {
            @Override
            public void onFinish() {
//                handleTime.cancel();
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
                if (currArray == 6 && Currentperiod == 1299) {
                    isProgressVideo = false;
                    circleProgress1.setText("00:00");
                    currVideo = 0;
                } else {
                    isProgressVideo = true;
                    circleProgress1.setText("Next");

                }

            }

            @Override
            public void onProgressUpdate(float progress) {
//                Currentperiod++;
//                circleProgress1.setText("Currentperiod"+Currentperiod, Color.DKGRAY);
            }
        });
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayer.setLooping(true);
        mPlayerNextPreview = MediaPlayer.create(this, R.raw.forwardback);
        mVideoView.setOnCompletionListener(this);

        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[0];
            arrList.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[1];
            arrList1.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[2];
            arrList2.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[3];
            arrList3.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[4];
            arrList4.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[5];
            arrList5.add(fileName);
        }
        for (int i = 0; i < 2; i++) {
            fileName = "android.resource://" + getPackageName() + "/"
                    + arrVideo[6];
            arrList6.add(fileName);
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
        mHandler.removeCallbacksAndMessages(mTimer);
    }

    private void SetTimeCountDown(int millisToGos) {

//        handleTime = new CountdownTimerHandle(MainActivity.this, 1000,millisToGos, isPlayVideo,circleProgress1);
//        handleTime.start();
        //setTimer();
        countDownTimer = new CountDownTimer(millisToGos, 1000) {

            @Override
            public void onTick(long millis) {
                if (isPausedVideo) {
                    cancel();
                    isPausedVideo = false;
                } else {
                    int seconds = (int) (millis / 1000) % 60;
                    int minutes = (int) ((millis / (1000 * 60)) % 60);
                    String text = String.format("%02d:%02d", minutes, seconds);
                    circleProgress1.setText(text, Color.DKGRAY);


//
                }
            }

            @Override
            public void onFinish() {
                circleProgress1.setText("00:00");
//                circleProgress1.setProgress(0);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:

                if (!isPlayVideo) {
                    playVideo(arrList.get(currVideo));
                    isPlayVideo = true;
                }
                if (!mVideoView.isPlaying()) {
                    mVideoView.seekTo(currPossition);
                    mVideoView.start();
                    isPausedVideo = false;
                    //millisToGo = currentTime;
                }

                mPlayerNextPreview.start();
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                mPlayer.start();
                SetTimeCountDown(millisToGo);
                mHandler.removeCallbacks(mTimer);
                mHandler.removeCallbacksAndMessages(mTimer);
                setTimerProgres(timeOption);

                break;
            case R.id.imgPause:
                isPausedVideo = true;
                isPlayVideo = false;
                if (mVideoView.isPlaying()) {
                    currPossition = mVideoView.getCurrentPosition();
                    mVideoView.pause();
                }
                mPlayerNextPreview.start();
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.GONE);
                mPlayer.pause();
                break;
            case R.id.imgNext:
                nextAction();
                break;

            case R.id.imgPreview:
                previewAction();

                break;
            case R.id.btnOption1:
                Currentperiod = 0;
                currArray = 0;
                timeOption = 290;
                currVideo = 0;
                progress = 0;
                mHandler.removeCallbacks(mTimer);
                mHandler.removeCallbacksAndMessages(mTimer);
                mHandler = new Handler();
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }

//                handleTime.cancel();
                setTimeAction(currArray);
                circleProgress1.resetProgressBar();
                setTimerProgres(timeOption);
                btnOption1.setBackgroundResource(R.drawable.background_button_selected);
                btnOption2.setBackgroundResource(R.drawable.background_button_none);
                btnOption1.setTextColor(Color.BLACK);
                btnOption2.setTextColor(Color.WHITE);
                playVideo(arrList.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                break;

            case R.id.btnOption2:
                Currentperiod = 0;
                currArray = 0;
                timeOption = 440;
                currVideo = 0;
                progress = 0;
                mHandler.removeCallbacks(mTimer);
                mHandler.removeCallbacksAndMessages(mTimer);
                mHandler = new Handler();
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                handleTime.cancel();
                setTimeAction(currArray);
                circleProgress1.resetProgressBar();
                setTimerProgres(timeOption);
                btnOption2.setBackgroundResource(R.drawable.background_button_selected);
                btnOption1.setBackgroundResource(R.drawable.background_button_none);
                btnOption2.setTextColor(Color.BLACK);
                btnOption1.setTextColor(Color.WHITE);
                playVideo(arrList.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                break;
            case R.id.circle_progress1:
                if (isProgressVideo) {
                    nextAction();
                }

                break;
        }

    }

    private void setTimerProgres(final int _timeProcess) {
        mTimer = new Runnable() {
            @Override
            public void run() {
                progress += 1;
                if (progress <= _timeProcess)
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (!isPausedVideo) {
                                Currentperiod++;

                                if(Currentperiod == 201 || Currentperiod == 401  ||Currentperiod == 601 ||Currentperiod == 801||Currentperiod == 1001 ||Currentperiod == 1201){
                                    progress = 0;
                                    mHandler.removeCallbacks(mTimer);
                                    mHandler.removeCallbacksAndMessages(mTimer);
                                    mHandler.removeMessages(0);
                                    mHandler = new Handler();

                                    circleProgress1.clearAnimation();

                                    circleProgress1.resetProgressBar();
                                    setTimeAction(currArray);
                                    setTimerProgres(timeOption*30);

                                }else {
                                    circleProgress1.setProgress(progress);

                                }
                            }
                        }
                    });
                mHandler.postDelayed(this, _timeProcess);
               //
            }

        };
        mHandler.postDelayed(mTimer, 1000);
    }

    //play video by url
    private void playVideo(String url) {
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mVideoView.requestFocus();
    }


    //play all video in disk
    @Override
    public void onCompletion(MediaPlayer mp) {
            currVideo = 0;
            if(Currentperiod < 100 && Currentperiod > 0){
                currArray = 0;
                currVideo ++;
                playVideo(arrList.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else if(Currentperiod >= 100 && Currentperiod < 200){
                currArray = 1;
                currVideo ++;
                playVideo(arrList1.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);

            }
            else  if(Currentperiod >= 200 && Currentperiod < 300){
                currArray = 1;

                currVideo ++;
                playVideo(arrList1.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else if(Currentperiod >= 300 && Currentperiod < 400){
                currArray = 2;
                currVideo ++;
                playVideo(arrList2.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 400 && Currentperiod < 500){
                currArray = 2;
                currVideo ++;
                playVideo(arrList2.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 500 && Currentperiod < 600){
                currArray = 3;
                currVideo ++;
                playVideo(arrList3.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else if(Currentperiod >= 600 && Currentperiod < 700){
                currArray = 3;
                currVideo ++;
                playVideo(arrList3.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else if(Currentperiod >= 700 && Currentperiod < 800){
                currArray = 4;
                currVideo ++;
                playVideo(arrList4.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 800 && Currentperiod < 900){
                currArray = 4;
                currVideo ++;
                playVideo(arrList4.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else if(Currentperiod >= 900 && Currentperiod < 1000){
                currArray = 5;
                currVideo ++;
                playVideo(arrList5.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 1000 && Currentperiod < 1100){
                currArray = 5;
                currVideo ++;
                playVideo(arrList5.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 1100 && Currentperiod < 1200){
                currArray = 6;
                currVideo ++;
                playVideo(arrList6.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }else if(Currentperiod >= 1200 && Currentperiod < 1300){
                currArray = 6;
                currVideo ++;
                playVideo(arrList6.get(currVideo));
                mVideoView.requestFocus();
                mVideoView.start();
                setTimeAction(currArray);
            }
            else{
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.GONE);
                mVideoView.stopPlayback();
            }


    }

    public void setTimeAction(int _currArray) {
        if (_currArray == 0 && Currentperiod == 0) {
            millisToGo = 0 * 1000 + 7 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 1 && Currentperiod == 201) {
            millisToGo = 0 * 1000 + 6 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 2 && Currentperiod == 401) {
            millisToGo = 0 * 1000 + 5 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 3 && Currentperiod == 601) {
            millisToGo = 0 * 1000 + 4 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 4 && Currentperiod == 801) {
            millisToGo = 0 * 1000 + 3 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 5 && Currentperiod == 1001) {
            millisToGo = 0 * 1000 + 2 * 1000 * 60;
            SetTimeCountDown(millisToGo);
        } else if (_currArray == 6 && Currentperiod == 1201) {
            millisToGo = 0 * 1000 + 1 * 1000 * 60;
            SetTimeCountDown(millisToGo);
        }
    }
    public  void nextAction(){
        currVideo=0;
        if(currArray < 6){

            currArray ++;
            currVideo = 0;
            progress = 0;
            mHandler.removeCallbacks(mTimer);
            mHandler.removeCallbacksAndMessages(mTimer);
            mHandler = new Handler();
            circleProgress1.resetProgressBar();
            setTimerProgres(timeOption);
            Currentperiod();
            if(currArray == 0){

                currVideo ++;
                playVideo(arrList.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }

//                        handleTime.cancel();

                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 1){
                currVideo ++;
                playVideo(arrList1.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 2){
                currVideo ++;
                playVideo(arrList2.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 3){
                currVideo ++;
                playVideo(arrList3.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 4){
                currVideo ++;
                playVideo(arrList4.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 5){
                currVideo ++;
                playVideo(arrList5.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 6){
                currVideo ++;
                playVideo(arrList6.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }

        }
        else {
            currVideo = 0;
        }
    }
    public  void previewAction(){
        currVideo=0;
        if(currArray <= 6 && currArray > 0){

            currArray --;
            currVideo = 0;
            progress = 0;
            mHandler.removeCallbacks(mTimer);
            mHandler.removeCallbacksAndMessages(mTimer);
            mHandler = new Handler();
            circleProgress1.resetProgressBar();
            setTimerProgres(timeOption);
            Currentperiod();
            if(currArray == 0){
                currVideo ++;
                playVideo(arrList.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 1){
                currVideo ++;
                playVideo(arrList1.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 2){
                currVideo ++;
                playVideo(arrList2.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 3){
                currVideo ++;
                playVideo(arrList3.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 4){
                currVideo ++;
                playVideo(arrList4.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 5){
                currVideo ++;
                playVideo(arrList5.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }else if(currArray == 6) {
                currVideo ++;
                playVideo(arrList6.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if(mVideoView.isPlaying()){
                    countDownTimer.cancel();
                }
//                        handleTime.cancel();
                setTimeAction(currArray);
                isProgressVideo = false;

            }

        }
        else {
            currVideo = 0;
        }
    }
    public void Currentperiod(){
        if(currArray == 0){
            Currentperiod = 0;
        }else if(currArray == 1){
            Currentperiod = 201;
        }else if(currArray == 2){
            Currentperiod = 401;
        }else if(currArray == 3){
            Currentperiod = 601;
        }else if(currArray == 4){
            Currentperiod = 801;
        }else if(currArray == 5){
            Currentperiod = 1001;
        }else if(currArray == 6){
            Currentperiod = 1201;

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.videoview.demoprojectvideoview/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

}
