package com.example.muonsach.obj;

public class BookShare {
    private String imgBookShare;
    private int numStars;
    private String imgBookShareName;

    public BookShare() {
    }

    public BookShare(String imgBookShare, int numStars, String imgBookShareName) {
        this.imgBookShare = imgBookShare;
        this.numStars = numStars;
        this.imgBookShareName = imgBookShareName;
    }

    public String getImgBookShare() {
        return imgBookShare;
    }

    public void setImgBookShare(String imgBookShare) {
        this.imgBookShare = imgBookShare;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public String getImgBookShareName() {
        return imgBookShareName;
    }

    public void setImgBookShareName(String imgBookShareName) {
        this.imgBookShareName = imgBookShareName;
    }
}
