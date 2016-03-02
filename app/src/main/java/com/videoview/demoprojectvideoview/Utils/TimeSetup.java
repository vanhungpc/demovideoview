package com.videoview.demoprojectvideoview.Utils;

/**
 * Created by Vanhungpc on 3/3/2016.
 */
public class TimeSetup {
    private static TimeSetup instance;
    public static TimeSetup getInstance(){
        if(instance == null)
            instance = new TimeSetup();
        return instance;
    }

    public int progresBar(int timeOption, int statusOption, int mMillisInFuture) {
        if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 1/*Currentperiod < 95 && Currentperiod >= 0*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 2/*Currentperiod >= 95 && Currentperiod < 200*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }

        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 3/*Currentperiod >= 200 && Currentperiod < 295*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 4/*Currentperiod >= 295 && Currentperiod < 395*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 5/*Currentperiod >= 395 && Currentperiod < 495*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 6/*Currentperiod >= 495 && Currentperiod < 595*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 7) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 8/*Currentperiod >= 695 && Currentperiod < 795*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 9/*Currentperiod >= 795 && Currentperiod < 895*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 10/*Currentperiod >= 895 && Currentperiod < 993*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 11/*Currentperiod >= 993 && Currentperiod < 1090*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 450;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 12/*Currentperiod >= 1090 && Currentperiod < 1195*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 13/*Currentperiod >= 1195 && Currentperiod < 1300*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else if (checkAuToNextDownTime(mMillisInFuture, timeOption) == 14/*Currentperiod >= 1195 && Currentperiod < 1300*/) {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        } else {
            if (statusOption == 1) {
                timeOption = 300;
            } else {
                timeOption = 150;
            }
        }
        return timeOption;
    }


    public int checkAuToNextDownTime(int mMillisInFuture, int timeOption) {
        DataApp.getInstance().setValueTimerAutoVideo(timeOption);
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

    public int checkCountTimeChange(int timeOption, int mMillisInFuture) {
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

    public int checkCurrCountDownTime(int timeOption, int mMillisInFuture) {
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
}
