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
    private  int[] arrVideo = {R.raw.video1, R.raw.video2, R.raw.video3, R.raw.video4, R.raw.video5, R.raw.video6, R.raw.video7};
    public void setDefaultVideo(){
        VideoDTO dto = new VideoDTO();
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[0];
            dto.get_arrList().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[1];
            dto.get_arrList1().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[2];
            dto.get_arrList2().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[3];
            dto.get_arrList3().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[4];
            dto.get_arrList4().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[5];
            dto.get_arrList5().add(url_video);
        }
        for (int i = 0; i < 2; i++) {
            dto = new VideoDTO();
            String url_video = dto.get_fileName()+ arrVideo[6];
            dto.get_arrList6().add(url_video);
        }
    }
}
