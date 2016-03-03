package com.videoview.demoprojectvideoview;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.natasa.progressviews.CircleProgressBar;
import com.videoview.demoprojectvideoview.DTO.VideoDTO;
import com.videoview.demoprojectvideoview.Model.VideoModel;
import com.videoview.demoprojectvideoview.Utils.Constact;
import com.videoview.demoprojectvideoview.Utils.DataApp;
import com.videoview.demoprojectvideoview.Utils.TimeSetup;
import com.videoview.demoprojectvideoview.Utils.VideoHandle;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements View.OnClickListener, OnCompletionListener, RatingBar.OnRatingBarChangeListener {
    @InjectView(R.id.mVideoView)
    VideoView mVideoView;
    @InjectView(R.id.btnOption1)
    Button btnOption1;
    @InjectView(R.id.btnOption2)
    Button btnOption2;
    @InjectView(R.id.imgPlay)
    LinearLayout imgPlay;
    @InjectView(R.id.imgPause)
    LinearLayout imgPause;
    @InjectView(R.id.imgNext)
    LinearLayout imgNext;
    @InjectView(R.id.lnRate)
    LinearLayout lnRate;
    @InjectView(R.id.myTextProgress)
    TextView myTextProgress;
    @InjectView(R.id.rating)
    RatingBar rating;
    @InjectView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @InjectView(R.id.img_Close)
    ImageView imgClose;
    @InjectView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @InjectView(R.id.lnRateStart)
    RelativeLayout lnRateStart;
    @InjectView(R.id.imgPreview)
    LinearLayout imgPreview;
    @InjectView(R.id.circleProgress1)
    com.videoview.demoprojectvideoview.CustomView.CircleProgressBar circleProgress1;

    private CircleProgressBar circleProgressBar1;
    private Runnable mTimer;
    int progress;
    private CountDownTimer countDownTimer;
    int millisToGo = 0 * 1000 + 7 * 1000 * 60;
    int currVideo = 0;
    MediaPlayer mPlayer;
    MediaPlayer mPlayerStart;
    MediaPlayer mPlayerFinish;
    MediaPlayer mPlayerNextPreview;
    boolean isPlayVideo = false;
    boolean isPausedVideo = false;
    boolean isProgressVideo = false;
    int timeOption = 300;
    int currPossition = 0;
    int currArray = 0;
    boolean isStartThead = false;

    private Object mPauseLock;
    private boolean mPaused;
    private boolean mFinished;
    boolean isStopThread = false;
    String _packageName = "";
    String resourceName = "android.resource://";
    VideoDTO _video;
    Dialog dialog;
    int statusOption = 1;

    Handler updateUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            progress = (Integer.parseInt(msg.obj.toString()));
            int timeset = TimeSetup.getInstance().checkCountTimeChange(timeOption, mMillisInFuture);
            int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
            if(timeset == 1 || timeset == 2){
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            }
            if (checkCurrCountDownTime() == 15 && currArray == 6) {
                isStartThead = false;
                isStopThread = true;
                isPlayVideo = false;
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                currArray = 0;
                currVideo = 0;
                isProgressVideo = false;
                myTextProgress.setText("0:00");
                mPlayerFinish.start();
                mPlayer.pause();
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.GONE);
                mVideoView.stopPlayback();
                threadTimerProgres.interrupt();
                threadTimerProgres = new Thread();
                updateUIHandler.removeCallbacks(null);
                updateUIHandler.removeMessages(0);

            } else {
                if (checkCountTimeChange() == 1) {
                    progress = 0;
                    Interrupted();
                } else if (checkCountTimeChange() == 2) {
                    progress = 0;

                } else if (checkCountTimeChange() != 3) {
                    //circleProgress1.setProgress(progress);
                }

            }


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
        _packageName = getPackageName();
        resourceName = "android.resource://" + _packageName + "/";


        imgNext.setOnClickListener(this);
        imgPreview.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgPause.setOnClickListener(this);

        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        lnRate.setOnClickListener(this);
        btnOption1.setBackgroundResource(R.drawable.background_button_selected);
        btnOption2.setBackgroundResource(R.drawable.background_button_none);
        btnOption1.setTextColor(Color.BLACK);
        btnOption2.setTextColor(Color.WHITE);
        circleProgress1.setOnClickListener(this);
        myTextProgress.setText("7:00");
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayer.setLooping(true);
        mPlayerNextPreview = MediaPlayer.create(this, R.raw.forwardback);
        mPlayerStart = MediaPlayer.create(this, R.raw.start);
        mPlayerFinish = MediaPlayer.create(this, R.raw.finish);
        mVideoView.setOnCompletionListener(this);
        _video = new VideoDTO();
        _video = VideoModel.getInstance().setDefaultVideo(resourceName);


        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;
        mPlayerStart.start();
        setProgressWithAnimation(timeOption*100);




        callIsStopThread();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.start();
        if (isPauseDevice) {

            mVideoView.seekTo(currPossition + 2);
            mVideoView.start();
            SetTimeCountDown(mMillisInFuture);
            isPausedVideo = false;
            isPauseDevice = false;
        }

    }
    ObjectAnimator objectAnimator;
    public void setProgressWithAnimation(int duration) {
        objectAnimator = ObjectAnimator.ofFloat(circleProgress1, "progress", 0.0F, 100.0F);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }


    boolean isPauseDevice = false;
    public int getDuration(){
        int optionProgress = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
        return optionProgress;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
        if (isPlayVideo) {

            currPossition = mVideoView.getCurrentPosition();
            mVideoView.pause();
            countDownTimer.cancel();

            isPausedVideo = true;
            isPauseDevice = true;

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPauseDevice) {
            finish();
        }
    }

    private int mMillisInFuture;

    private void SetTimeCountDown(int millisToGos) {
        countDownTimer = new CountDownTimer(millisToGos, 1000) {
            @Override
            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                if (isPausedVideo) {
                    mMillisInFuture = seconds * 1000 + minutes * 1000 * 60;
                    cancel();
                } else {
                    mMillisInFuture = seconds * 1000 + minutes * 1000 * 60;
                    String text = String.format("%d:%02d", minutes, seconds);
                    if (checkCurrCountDownTime() == 14) {
                        myTextProgress.setText(text);

                    } else {
                        if (timeOption == 300) {
                            if (seconds >= 31 && seconds <= 60) {
                                myTextProgress.setText(text);
                                isProgressVideo = false;
                            } else if (seconds >= 0 && seconds <= 31) {
                                if (minutes == 6 || minutes == 5 || minutes == 4 || minutes == 3 || minutes == 2 || minutes == 1) {
                                    if (seconds == 30) {
                                        myTextProgress.setText("NEXT");
                                    }
                                }
                                isProgressVideo = true;
                            }
                        } else if (timeOption == 450) {
                            if (seconds >= 16 && seconds <= 60) {
                                myTextProgress.setText(text);
                                isProgressVideo = false;
                            } else if (seconds >= 0 && seconds <= 16) {
                                if (minutes == 6 || minutes == 5 || minutes == 4 || minutes == 3 || minutes == 2 || minutes == 1) {
                                    if (seconds == 15) {
                                        myTextProgress.setText("NEXT");
                                    }
                                }
                                isProgressVideo = true;
                            }
                        } else if (timeOption == 150) {
                            if (seconds >= 16 && seconds <= 60) {
                                myTextProgress.setText(text);
                                isProgressVideo = false;
                            } else if (seconds >= 0 && seconds <= 16) {
                                if (minutes == 6 || minutes == 5 || minutes == 4 || minutes == 3 || minutes == 2 || minutes == 1) {
                                    if (seconds == 15) {
                                        myTextProgress.setText("NEXT");
                                    }
                                }
                                isProgressVideo = true;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
//                circleProgress1.setText("0:00", Color.WHITE);

//                circleProgress1.setProgress(0);
            }
        }.start();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                if (!isStopThread) {
                    if (!isPlayVideo) {
                        VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                        isPlayVideo = true;
                    } else {
                        mPlayerStart.start();
                    }

                    if (isPausedVideo) {
                        mVideoView.seekTo(currPossition + 2);
                        mVideoView.start();

                        SetTimeCountDown(mMillisInFuture);
                        isPausedVideo = false;
                        objectAnimator.resume();
//                    synchronized (mPauseLock) {
//                        mPaused = false;
//                        mPauseLock.notifyAll();
//                    }
                    } else {
                        SetTimeCountDown(millisToGo);

                    }

                    mPlayerNextPreview.start();
                    imgPlay.setVisibility(View.GONE);
                    imgPause.setVisibility(View.VISIBLE);
                    mPlayer.start();
                    if (!isStartThead) {
                        isStartThead = true;
                        threadTimerProgres.start();
                    } else {
                        Interrupted();
                    }
                    mPlayerStart.start();
                    circleProgress1.clearAnimation();
                    int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                    setProgressWithAnimation(duration * 100);
                } else {
                    callIsStopThread();
                }


                break;
            case R.id.imgPause:
                isPausedVideo = true;
                isPlayVideo = false;
                mPlayer.pause();
                if (mVideoView.isPlaying()) {
                    currPossition = mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    objectAnimator.pause();
                }
                countDownTimer.cancel();
//                synchronized (mPauseLock) {
//                    mPaused = true;
//                    circleProgress1.
//                }
                mPlayerNextPreview.start();
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.GONE);
                mPlayer.pause();
                break;
            case R.id.imgNext:
                mPlayerNextPreview.start();
                if (isStartThead) {
                    mPlayer.start();
                    nextAction();
                }

                break;

            case R.id.imgPreview:
                mPlayerNextPreview.start();
                if (isStartThead) {
                    mPlayer.start();
                    previewAction();
                }


                break;
            case R.id.btnOption1:
                statusOption = 1;
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                currArray = 0;
                timeOption = 300;
                currVideo = 0;
                progress = 0;

                if (mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                btnOption1.setBackgroundResource(R.drawable.background_button_selected);
                btnOption2.setBackgroundResource(R.drawable.background_button_none);
                btnOption1.setTextColor(Color.BLACK);
                btnOption2.setTextColor(Color.WHITE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                SetTimeCountDown(millisToGo);
                mPlayer.start();
                if (!isStartThead) {
                    isStartThead = true;
                    threadTimerProgres.start();
                } else {

                    Interrupted();
                }
                circleProgress1.clearAnimation();
                setProgressWithAnimation(timeOption * 100);

                break;

            case R.id.btnOption2:
                statusOption = 2;
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                currArray = 0;
                timeOption = 450;
                currVideo = 0;
                progress = 0;

                if (mVideoView.isPlaying() || isStartThead) {
                    countDownTimer.cancel();
                }
                btnOption2.setBackgroundResource(R.drawable.background_button_selected);
                btnOption1.setBackgroundResource(R.drawable.background_button_none);
                btnOption2.setTextColor(Color.BLACK);
                btnOption1.setTextColor(Color.WHITE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                mPlayer.start();
                isPlayVideo = true;
                isPausedVideo = false;
                SetTimeCountDown(millisToGo);
                if (!isStartThead) {
                    isStartThead = true;
                    threadTimerProgres.start();
                } else {

                    Interrupted();
                }
                circleProgress1.clearAnimation();
                setProgressWithAnimation(timeOption * 100);
                break;
            case R.id.circleProgress1:
                mPlayerNextPreview.start();
                if (isProgressVideo) {
                    nextAction();
                }

                break;
            case R.id.lnRate:
                lnRateStart.setVisibility(View.VISIBLE);
                imgClose.setOnClickListener(this);
                rating.setOnRatingBarChangeListener(this);
                break;
            case R.id.img_Close:
                lnRateStart.setVisibility(View.GONE);
                break;
        }

    }

    //call thread if finish
    public void callIsStopThread() {
        if (timeOption == 450) {
            timeOption = 450;
            btnOption1.setBackgroundResource(R.drawable.background_button_none);
            btnOption2.setBackgroundResource(R.drawable.background_button_selected);
        } else if (timeOption == 150) {
            timeOption = 450;
            btnOption1.setBackgroundResource(R.drawable.background_button_none);
            btnOption2.setBackgroundResource(R.drawable.background_button_selected);
        } else if (timeOption == 300) {
            timeOption = 300;
            btnOption1.setBackgroundResource(R.drawable.background_button_selected);
            btnOption2.setBackgroundResource(R.drawable.background_button_none);
        }
        isStopThread = false;
        millisToGo = 0 * 1000 + 7 * 1000 * 60;
        mMillisInFuture = 0 * 1000 + 7 * 1000 * 60;
        currArray = 0;
        currVideo = 0;
        progress = 0;

        if (mVideoView.isPlaying()) {
            countDownTimer.cancel();
        }

        btnOption1.setBackgroundResource(R.drawable.background_button_selected);
        btnOption2.setBackgroundResource(R.drawable.background_button_none);
        btnOption1.setTextColor(Color.BLACK);
        btnOption2.setTextColor(Color.WHITE);
        VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
        imgPlay.setVisibility(View.GONE);
        imgPause.setVisibility(View.VISIBLE);
        isPlayVideo = true;
        isPausedVideo = false;
        SetTimeCountDown(millisToGo);
        if (!isStartThead) {
            isStartThead = true;
            threadTimerProgres.start();
        } else {
            Interrupted();
        }
    }

    Thread threadTimerProgres = new Thread(new Runnable() {


        @Override
        public void run() {
            while (progress <= timeOption) {
                if (!isPausedVideo) {
                    progress++;
                    Message msg = updateUIHandler.obtainMessage(1, progress);
                    //msg.what = progress;
                    updateUIHandler.sendMessage(msg);
                    TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
//                    circleProgress1.setProgress(progress);
                    try {
                        Thread.sleep(timeOption);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();

                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }

            }

        }
    });


    //play all video in disk
    @Override
    public void onCompletion(MediaPlayer mp) {
        VideoHandle.getInstance().autoNextVideo(currVideo, mMillisInFuture, timeOption, currArray, mVideoView, _video);
//        autoNextVideo();
    }


    public int checkCurrCountDownTime() {
        DataApp.getInstance().setValueTimer(timeOption);
        int valueTime = 0;
        if (mMillisInFuture >= Constact.time66 && mMillisInFuture < Constact.time7) {
            valueTime = 1;
        } else if (mMillisInFuture >= Constact.time6 && mMillisInFuture < Constact.time66) {
            valueTime = 2;
        } else if (mMillisInFuture >= Constact.time55 && mMillisInFuture < Constact.time6) {
            valueTime = 3;
        } else if (mMillisInFuture >= Constact.time5 && mMillisInFuture < Constact.time55) {
            valueTime = 4;
        } else if (mMillisInFuture >= Constact.time44 && mMillisInFuture < Constact.time5) {
            valueTime = 5;
        } else if (mMillisInFuture >= Constact.time4 && mMillisInFuture < Constact.time44) {
            valueTime = 6;
        } else if (mMillisInFuture >= Constact.time33 && mMillisInFuture < Constact.time4) {
            valueTime = 7;
        } else if (mMillisInFuture >= Constact.time3 && mMillisInFuture < Constact.time33) {
            valueTime = 8;
        } else if (mMillisInFuture >= Constact.time22 && mMillisInFuture < Constact.time3) {
            valueTime = 9;
        } else if (mMillisInFuture >= Constact.time2 && mMillisInFuture < Constact.time22) {
            valueTime = 10;
        } else if (mMillisInFuture >= Constact.time11 && mMillisInFuture < Constact.time2) {
            valueTime = 11;
        } else if (mMillisInFuture >= Constact.time1 && mMillisInFuture < Constact.time11) {
            valueTime = 12;
        } else if (mMillisInFuture >= Constact.time0 && mMillisInFuture < Constact.time1) {
            valueTime = 13;
        } else if (mMillisInFuture > 1000 && mMillisInFuture < Constact.time0) {
            valueTime = 14;
        } else if (mMillisInFuture <= 1000) {
            valueTime = 15;
        }
        return valueTime;
    }

    public int checkCountTimeChange() {
        DataApp.getInstance().setValueTimer(timeOption);
        int valueTime = 0;
        if (mMillisInFuture == Constact.time66 || mMillisInFuture == Constact.time55 || mMillisInFuture == Constact.time44 || mMillisInFuture == Constact.time33 || mMillisInFuture == Constact.time22 || mMillisInFuture == Constact.time11 || mMillisInFuture == Constact.time0) {
            valueTime = 1;
        } else if (mMillisInFuture == Constact.time6 || mMillisInFuture == Constact.time5 || mMillisInFuture == Constact.time4 || mMillisInFuture == Constact.time3 || mMillisInFuture == Constact.time2 || mMillisInFuture == Constact.time1) {
            valueTime = 2;
        } else if (mMillisInFuture == Constact.time0) {
            valueTime = 3;
        } else {
            valueTime = 4;
        }
        return valueTime;
    }


    public int setDefaultCountTimeChange() {
        DataApp.getInstance().setValueTimer(timeOption);
        int valueTime = 0;
        if (mMillisInFuture == Constact.time66) {
            valueTime = 1;
        } else if (mMillisInFuture == Constact.time55) {
            valueTime = 2;
        } else if (mMillisInFuture == Constact.time44) {
            valueTime = 3;
        } else if (mMillisInFuture == Constact.time33) {
            valueTime = 4;
        } else if (mMillisInFuture == Constact.time22) {
            valueTime = 5;
        } else if (mMillisInFuture == Constact.time11) {
            valueTime = 6;
        }
        return valueTime;
    }

    public void nextAction() {
        currVideo = 0;
        if (currArray < 6) {

            progress = 0;
            if (!isStartThead) {
                isStartThead = true;
                threadTimerProgres.start();
            } else {
                Interrupted();
            }
            if (checkCurrCountDownTime() == 1) {
                //video 1
                currArray++;

            } else if (checkCurrCountDownTime() == 2) {
                currArray = 1;
                //video 2

            } else if (checkCurrCountDownTime() == 3) {
                //video 2
                currArray++;

            } else if (checkCurrCountDownTime() == 4) {
                //video 3
                currArray = 2;

            } else if (checkCurrCountDownTime() == 5) {
                //video 3
                currArray++;

            } else if (checkCurrCountDownTime() == 6) {
                //video 4
                currArray = 3;

            } else if (checkCurrCountDownTime() == 7) {
                //video 4
                currArray++;

            } else if (checkCurrCountDownTime() == 8) {
                //video 5
                currArray = 4;

            } else if (checkCurrCountDownTime() == 9) {
                //video 5
                currArray++;

            } else if (checkCurrCountDownTime() == 10) {
                //video 6
                currArray = 5;

            } else if (checkCurrCountDownTime() == 11) {
                //video 6
                currArray++;

            }
            if (currArray == 0) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }

                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 1) {
                currVideo++;
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 6 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            } else if (currArray == 2) {
                currVideo++;
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 5 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 3) {
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 4 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 4) {
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 3 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 5) {
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 2 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 6) {
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 1 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            }

        } else {
            currVideo = 0;
        }
    }

    public void previewAction() {
        currVideo = 0;
        if (currArray <= 6) {
            currArray--;
            progress = 0;
            if (!isStartThead) {
                isStartThead = true;
                threadTimerProgres.start();
            } else {
                Interrupted();
            }

            if (currArray == 0) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                currVideo++;
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                SetTimeCountDown(millisToGo);

                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            } else if (currArray == 1) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 6 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            } else if (currArray == 2) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 5 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 3) {
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 4 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 4) {
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 3 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 5) {
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                millisToGo = 0 * 1000 + 2 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);

            } else if (currArray == 6) {
                VideoHandle.getInstance().playVideo(_video.get_arrList().get(currVideo), mVideoView);
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();

                }
                millisToGo = 0 * 1000 + 1 * 1000 * 60;
                SetTimeCountDown(millisToGo);
                isProgressVideo = false;

                int duration = TimeSetup.getInstance().progresBar(timeOption, statusOption, mMillisInFuture);
                circleProgress1.clearAnimation();
                setProgressWithAnimation(duration*100);
            }

        } else {
            currVideo = 0;
        }
    }

    public void Interrupted() {
        if (threadTimerProgres != null)
            threadTimerProgres.currentThread().interrupt();
        threadTimerProgres = new Thread();
        threadTimerProgres.start();
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

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }
}
