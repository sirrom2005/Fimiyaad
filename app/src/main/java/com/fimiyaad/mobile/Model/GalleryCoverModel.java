package com.fimiyaad.mobile.Model;

public class GalleryCoverModel {
    private int image;
    private int year;

    public GalleryCoverModel(int year, int image) {
        this.year  = year;
        this.image = image;
    }

    public int getImage() {
        return image;
    }
    public int getYear() { return year; }
}
