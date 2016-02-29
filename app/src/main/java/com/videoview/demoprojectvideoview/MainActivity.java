package com.videoview.demoprojectvideoview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.natasa.progressviews.CircleProgressBar;
import com.natasa.progressviews.utils.OnProgressViewListener;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.util.ArrayList;

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
    @InjectView(R.id.lnRate)
    LinearLayout lnRate;
    @InjectView(R.id.mThumbnail)
    ImageView mThumbnail;
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
    int timeOption = 300;
    int timeOption2 = 150;
    long millisCurr = 0;
    int currPossition = 0;
    int currArray = 0;

    int Currentperiod = 0;
    boolean isStartThead = false;

    private Object mPauseLock;
    private boolean mPaused;
    private boolean mFinished;
    Bitmap bmThumbnail;
    Handler updateUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            Currentperiod = (Integer.parseInt(msg.obj.toString()));
            progress = msg.what;
            if (Currentperiod == 200 || Currentperiod == 400 || Currentperiod == 600 || Currentperiod == 800 || Currentperiod == 1000 || Currentperiod == 1200) {
                progress = 0;
                circleProgress1.resetProgressBar();
                Interrupted();
                if (timeOption == 450) {
                    timeOption = 150;
                } else if (timeOption == 150) {
                    timeOption = 450;
                } else if (timeOption == 300) {
                    timeOption = 300;
                }
            } else {
                if (Currentperiod == 100 || Currentperiod == 300 || Currentperiod == 500 || Currentperiod == 700 || Currentperiod == 900 || Currentperiod == 1100) {
                    progress = 0;
                    circleProgress1.resetProgressBar();
                    if (timeOption == 450) {
                        timeOption = 150;
                    } else if (timeOption == 150) {
                        timeOption = 450;
                    } else if (timeOption == 300) {
                        timeOption = 300;
                    }
                } else {

                    circleProgress1.setProgress(progress);
                }

//                circleProgress1.setText("time: "+Currentperiod);

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
        imgNext.setOnClickListener(this);
        imgPreview.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgPause.setOnClickListener(this);
        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption1.setBackgroundResource(R.drawable.background_button_selected);
        btnOption2.setBackgroundResource(R.drawable.background_button_none);
        btnOption1.setTextColor(Color.BLACK);
        btnOption2.setTextColor(Color.WHITE);

        circleProgress1.setStartPositionInDegrees(ProgressStartPoint.DEFAULT);
        circleProgress1.setOnClickListener(this);
        circleProgress1.setTextSize(100);

        circleProgress1.setOnProgressViewListener(new OnProgressViewListener() {
            @Override
            public void onFinish() {
//                handleTime.cancel();
                if (mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                if (currArray == 6 && Currentperiod == 1299) {

                } else {
                    isProgressVideo = true;
                    circleProgress1.setText("00:00");
                    circleProgress1.resetProgressBar();
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
        mPlayerStart = MediaPlayer.create(this, R.raw.start);
        mPlayerFinish = MediaPlayer.create(this, R.raw.finish);
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
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(arrList.get(currVideo), MediaStore.Video.Thumbnails.MICRO_KIND);
        mThumbnail.setImageBitmap(bmThumbnail);

        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;


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
//        mHandler.removeCallbacks(mTimer);
//        mHandler.removeCallbacksAndMessages(mTimer);
    }

    private int mMillisInFuture;

    private void SetTimeCountDown(int millisToGos) {

//        handleTime = new CountdownTimerHandle(MainActivity.this, 1000,millisToGos, isPlayVideo,circleProgress1);
//        handleTime.start();
        //setTimer();
        countDownTimer = new CountDownTimer(millisToGos, 1000) {

            @Override
            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                if (isPausedVideo) {
                    mMillisInFuture = seconds * 1000 + minutes * 1000 * 60;
                    ;
                    cancel();
                } else {
                    mMillisInFuture = seconds * 1000 + minutes * 1000 * 60;
                    ;
                    String text = String.format("%02d:%02d", minutes, seconds);
                    if (timeOption == 300) {
                        if (seconds >= 31 && seconds <= 60) {
                            circleProgress1.setText(text, Color.WHITE);
                            isProgressVideo = false;
                        } else if (seconds >= 0 && seconds <= 31) {
                            circleProgress1.setText("NEXT", Color.WHITE);
                            isProgressVideo = true;
                        }
                    } else if (timeOption == 450) {
                        if (seconds >= 16 && seconds <= 60) {
                            circleProgress1.setText(text, Color.WHITE);
                            isProgressVideo = false;
                        } else if (seconds >= 0 && seconds <= 16) {
                            circleProgress1.setText("NEXT", Color.WHITE);
                            isProgressVideo = true;
                        }
                    } else if (timeOption == 150) {
                        if (seconds >= 16 && seconds <= 60) {
                            circleProgress1.setText(text, Color.WHITE);
                            isProgressVideo = false;
                        } else if (seconds >= 0 && seconds <= 16) {
                            circleProgress1.setText("NEXT", Color.WHITE);
                            isProgressVideo = true;
                        }
                    }

//
                }
            }

            @Override
            public void onFinish() {
                circleProgress1.setText("00:00", Color.WHITE);

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

                } else {
                    mPlayerStart.start();
                }

                if (isPausedVideo) {
                    mVideoView.seekTo(currPossition + 2);
                    mVideoView.start();

                    SetTimeCountDown(mMillisInFuture);
                    isPausedVideo = false;
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

//                mHandler.removeCallbacks(mTimer);
//                mHandler.removeCallbacksAndMessages(mTimer);
//                setTimerProgres(timeOption);
                if (!isStartThead) {
                    isStartThead = true;
                    threadTimerProgres.start();
                } else {
                    Interrupted();
                }

                break;
            case R.id.imgPause:
                isPausedVideo = true;
                isPlayVideo = false;
                mPlayer.pause();
                if (mVideoView.isPlaying()) {
                    currPossition = mVideoView.getCurrentPosition();
                    mVideoView.pause();
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
                if (isStartThead) {
                    nextAction();
                }

                break;

            case R.id.imgPreview:
                if (isStartThead) {
                    previewAction();
                }


                break;
            case R.id.btnOption1:
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                Currentperiod = 0;
                currArray = 0;
                timeOption = 300;
                currVideo = 0;
                progress = 0;

                if (mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                    circleProgress1.resetProgressBar();
                }

//                handleTime.cancel();
//                SetTimeCountDown(millisToGo);

//                setTimerProgres(timeOption);

                btnOption1.setBackgroundResource(R.drawable.background_button_selected);
                btnOption2.setBackgroundResource(R.drawable.background_button_none);
                btnOption1.setTextColor(Color.BLACK);
                btnOption2.setTextColor(Color.WHITE);
                playVideo(arrList.get(currVideo));
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


                break;

            case R.id.btnOption2:
                millisToGo = 0 * 1000 + 7 * 1000 * 60;
                Currentperiod = 0;
                currArray = 0;
                timeOption = 450;
                currVideo = 0;
                progress = 0;

                if (mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                    circleProgress1.resetProgressBar();
                }
                if (isStartThead) {
                    countDownTimer.cancel();
                }
//                handleTime.cancel();
//                SetTimeCountDown(millisToGo);

//                setTimerProgres(timeOption);

                btnOption2.setBackgroundResource(R.drawable.background_button_selected);
                btnOption1.setBackgroundResource(R.drawable.background_button_none);
                btnOption2.setTextColor(Color.BLACK);
                btnOption1.setTextColor(Color.WHITE);
                playVideo(arrList.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);

                playVideo(arrList.get(currVideo));

                isPlayVideo = true;
                isPausedVideo = false;
                SetTimeCountDown(millisToGo);
                if (!isStartThead) {
                    isStartThead = true;
                    threadTimerProgres.start();
                } else {

                    Interrupted();
                }
                break;
            case R.id.circle_progress1:
                if (isProgressVideo) {
                    nextAction();
                }

                break;
        }

    }

    Thread threadTimerProgres = new Thread(new Runnable() {


        @Override
        public void run() {
            while (progress <= timeOption) {
                if (!isPausedVideo) {
                    progress++;
                    Currentperiod++;
                    Message msg = updateUIHandler.obtainMessage(1, Currentperiod);
                    msg.what = progress;
                    updateUIHandler.sendMessage(msg);

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

    //play video by url
    private void playVideo(String url) {
        mVideoView.setVisibility(View.VISIBLE);
        mThumbnail.setVisibility(View.GONE);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        mVideoView.requestFocus();
    }


    //play all video in disk
    @Override
    public void onCompletion(MediaPlayer mp) {
        autoNextVideo();
    }

    public void autoNextVideo() {
        currVideo = 0;
        if (Currentperiod < 95 && Currentperiod >= 0) {
            currArray = 0;
            currVideo++;
            playVideo(arrList.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 95 && Currentperiod < 200) {
            currArray = 1;
            currVideo++;
            playVideo(arrList1.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);

        } else if (Currentperiod >= 200 && Currentperiod < 295) {
            currArray = 1;

            currVideo++;
            playVideo(arrList1.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 295 && Currentperiod < 395) {
            currArray = 2;
            currVideo++;
            playVideo(arrList2.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 395 && Currentperiod < 495) {
            currArray = 2;
            currVideo++;
            playVideo(arrList2.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 495 && Currentperiod < 595) {
            currArray = 3;
            currVideo++;
            playVideo(arrList3.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 595 && Currentperiod < 695) {
            currArray = 3;
            currVideo++;
            playVideo(arrList3.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 695 && Currentperiod < 795) {
            currArray = 4;
            currVideo++;
            playVideo(arrList4.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 795 && Currentperiod < 895) {
            currArray = 4;
            currVideo++;
            playVideo(arrList4.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 895 && Currentperiod < 993) {
            currArray = 5;
            currVideo++;
            playVideo(arrList5.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 993 && Currentperiod < 1090) {
            currArray = 5;
            currVideo++;
            playVideo(arrList5.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 1090 && Currentperiod < 1195) {
            currArray = 6;
            currVideo++;
            playVideo(arrList6.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 1195 && Currentperiod < 1300) {
            currArray = 6;
            currVideo++;
            playVideo(arrList6.get(currVideo));
            mVideoView.requestFocus();
            mVideoView.start();
            setTimeAction(currArray, Currentperiod);
        } else if (Currentperiod >= 1300) {
            Currentperiod = 0;
            threadTimerProgres.currentThread().interrupt();
            updateUIHandler = new Handler();
            isPlayVideo = false;
            millisToGo = 0 * 1000 + 7 * 1000 * 60;
            currArray = 0;
            currVideo = 0;
            isProgressVideo = false;
            circleProgress1.setText("00:00", Color.WHITE);
            mPlayerFinish.start();
            imgPlay.setVisibility(View.VISIBLE);
            imgPause.setVisibility(View.GONE);
            mVideoView.stopPlayback();
            countDownTimer.cancel();


        }
    }

    public void setTimeAction(int _currArray, int _currentperiod) {
        if (_currArray == 0 && _currentperiod == 0) {
            millisToGo = 0 * 1000 + 7 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 1 && _currentperiod == 200) {
            millisToGo = 0 * 1000 + 6 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 2 && _currentperiod == 400) {
            millisToGo = 0 * 1000 + 5 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 3 && _currentperiod == 600) {
            millisToGo = 0 * 1000 + 4 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 4 && _currentperiod == 800) {
            millisToGo = 0 * 1000 + 3 * 1000 * 60;
            SetTimeCountDown(millisToGo);

        } else if (_currArray == 5 && _currentperiod == 1000) {
            millisToGo = 0 * 1000 + 2 * 1000 * 60;
            SetTimeCountDown(millisToGo);
        } else if (_currArray == 6 && _currentperiod == 1200) {
            millisToGo = 0 * 1000 + 1 * 1000 * 60;
            SetTimeCountDown(millisToGo);
        }
    }

    public void nextAction() {
        currVideo = 0;
        if (currArray < 6) {
            currArray++;
            currVideo = 0;
            progress = 0;
            circleProgress1.resetProgressBar();
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
                playVideo(arrList.get(currVideo));
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 0;
                setTimeAction(currArray, 0);
                isProgressVideo = false;
            } else if (currArray == 1) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                currVideo++;
                playVideo(arrList1.get(currVideo));
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 200;
                setTimeAction(currArray, 200);
                isProgressVideo = false;
            } else if (currArray == 2) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                currVideo++;
                playVideo(arrList2.get(currVideo));
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 400;
                setTimeAction(currArray, 400);
                isProgressVideo = false;

            } else if (currArray == 3) {
                currVideo++;
                playVideo(arrList3.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 600;
                setTimeAction(currArray, 600);
                isProgressVideo = false;

            } else if (currArray == 4) {
                currVideo++;
                playVideo(arrList4.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 800;
                setTimeAction(currArray, 800);
                isProgressVideo = false;

            } else if (currArray == 5) {
                currVideo++;
                playVideo(arrList5.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 1000;
                setTimeAction(currArray, 1000);
                isProgressVideo = false;

            } else if (currArray == 6) {
                currVideo++;
                playVideo(arrList6.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 1200;
                setTimeAction(currArray, 1200);
                isProgressVideo = false;
            }

        } else {
            currVideo = 0;
        }
    }

    public void previewAction() {
        currVideo = 0;
        if (currArray <= 6 && currArray > 0) {
            currArray--;
            progress = 0;
            circleProgress1.resetProgressBar();
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
                playVideo(arrList.get(currVideo));
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 0;
                setTimeAction(currArray, 0);
                isProgressVideo = false;


            } else if (currArray == 1) {
                currVideo++;
                playVideo(arrList1.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }

                Currentperiod = 200;
                setTimeAction(currArray, 200);
                isProgressVideo = false;

            } else if (currArray == 2) {
                currVideo++;
                playVideo(arrList2.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 400;
                setTimeAction(currArray, 400);
                isProgressVideo = false;

            } else if (currArray == 3) {
                currVideo++;
                playVideo(arrList3.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }

                Currentperiod = 600;
                setTimeAction(currArray, 600);
                isProgressVideo = false;

            } else if (currArray == 4) {
                currVideo++;
                playVideo(arrList4.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 800;
                setTimeAction(currArray, 800);
                isProgressVideo = false;

            } else if (currArray == 5) {
                currVideo++;
                playVideo(arrList5.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 1000;
                setTimeAction(currArray, 1000);
                isProgressVideo = false;
            } else if (currArray == 6) {
                currVideo++;
                playVideo(arrList6.get(currVideo));
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                isPlayVideo = true;
                isPausedVideo = false;
                if (isStartThead || mVideoView.isPlaying()) {
                    countDownTimer.cancel();
                }
                Currentperiod = 1200;
                setTimeAction(currArray, 1200);
                isProgressVideo = false;
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

}
