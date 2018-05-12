package com.fimiyaad.mobile.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public class GalleryModel implements Serializable{
    private static final long serialVersionUID = 1236472295622778867L;
    private final int id;
    private final String url;
    private final String img;
    private final String country;
    private final String title;
    private final String date;
    private final int year;

    public GalleryModel(int id, String url, String img, String country, String title, String date, int year) {
        this.id     = id;
        this.url    = url;
        this.img    = img;
        this.country= country;
        this.title  = title;
        this.date   = date;
        this.year   = year;
    }

    public int getId() { return id; }
    public String getUrl() {return url; }
    public String getImage() { return img; }
    public String getCountry() { return country; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public int getYear() { return year; }


    public class Album implements Serializable {
        private static final long serialVersionUID = 3236472295622778867L;
        private String image;
        public String getImage() {
            return image;
        }
    }
}