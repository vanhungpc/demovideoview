package com.videoview.demoprojectvideoview;

import java.util.ArrayList;

/**
 * Created by maxmalla006 on 3/1/2016.
 */
public class VideoModel {
    private static VideoModel instance;
    public static VideoModel getInstance(){
        if(instance == null)
            instance = new VideoModel();
        return  instance;
    }

    public VideoDTO setDefaultVideo(String resourceName){
        VideoDTO dto = new VideoDTO();
        ArrayList<String> arrData = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[0];
            arrData.add(url_video);
        }
        dto.set_arrList(arrData);
        ArrayList<String> arrData1 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[1];
            arrData1.add(url_video);
        }
        dto.set_arrList1(arrData1);
        ArrayList<String> arrData2 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[2];
            arrData2.add(url_video);
        }
        dto.set_arrList2(arrData2);
        ArrayList<String> arrData3 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[3];
            arrData3.add(url_video);
        }
        dto.set_arrList3(arrData3);
        ArrayList<String> arrData4 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[4];
            arrData4.add(url_video);
        }
        dto.set_arrList4(arrData4);
        ArrayList<String> arrData5 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[5];
            arrData5.add(url_video);
        }
        dto.set_arrList5(arrData5);
        ArrayList<String> arrData6 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            String url_video = resourceName  + Constact.arrVideo[6];
            arrData6.add(url_video);
        }
        dto.set_arrList6(arrData6);
        return dto;
    }
}
