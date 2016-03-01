package com.videoview.demoprojectvideoview;

/**
 * Created by Vanhungpc on 3/1/2016.
 */
public class DataApp {

    private static DataApp instance;
    public static DataApp getInstance(){
        if(instance == null)
            instance = new DataApp();
        return instance;
    }

    public void setValueTimer(int timeOption){
        if(timeOption == 300){
            Constact.time7 = 0 * 1000 + 7 * 1000 * 60;
            Constact.time66 = 30 * 1000 + 6 * 1000 * 60;
            Constact.time6 = 0 * 1000 + 6 * 1000 * 60;
            Constact.time55 = 30 * 1000 + 5 * 1000 * 60;
            Constact.time5 = 0 * 1000 + 5 * 1000 * 60;
            Constact.time44 = 30 * 1000 + 4 * 1000 * 60;
            Constact.time4 = 0 * 1000 + 4 * 1000 * 60;
            Constact.time33 = 30 * 1000 + 3 * 1000 * 60;
            Constact.time3 = 0 * 1000 + 3 * 1000 * 60;
            Constact.time22 = 30 * 1000 + 2 * 1000 * 60;
            Constact.time2 = 0 * 1000 + 2 * 1000 * 60;
            Constact.time11 = 30 * 1000 + 1 * 1000 * 60;
            Constact.time1 = 0 * 1000 + 1 * 1000 * 60;
            Constact.time0 = 30 * 1000 + 0 * 1000 * 60;
        }else if(timeOption == 450 || timeOption == 150){
            Constact.time7 = 0 * 1000 + 7 * 1000 * 60;
            Constact.time66 = 15 * 1000 + 6 * 1000 * 60;
            Constact.time6 = 0 * 1000 + 6 * 1000 * 60;
            Constact.time55 = 15 * 1000 + 5 * 1000 * 60;
            Constact.time5 = 0 * 1000 + 5 * 1000 * 60;
            Constact.time44 = 15 * 1000 + 4 * 1000 * 60;
            Constact.time4 = 0 * 1000 + 4 * 1000 * 60;
            Constact.time33 = 15 * 1000 + 3 * 1000 * 60;
            Constact.time3 = 0 * 1000 + 3 * 1000 * 60;
            Constact.time22 = 15 * 1000 + 2 * 1000 * 60;
            Constact.time2 = 0 * 1000 + 2 * 1000 * 60;
            Constact.time11 = 15 * 1000 + 1 * 1000 * 60;
            Constact.time1 = 0 * 1000 + 1 * 1000 * 60;
            Constact.time0 = 15 * 1000 + 0 * 1000 * 60;
        }
    }

}
