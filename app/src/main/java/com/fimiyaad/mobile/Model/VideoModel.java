package com.fimiyaad.mobile.Model;

/**
 * Created by rohan
 * on 9/4/17.
 */

public class VideoModel {
    private String video;
    private String title;

    public VideoModel(String video, String title) {
        this.video  = video;
        this.title  = title;
    }

    public String getVideoId() {
        return video;
    }
    public String getTitle() {
        return title;
    }
}