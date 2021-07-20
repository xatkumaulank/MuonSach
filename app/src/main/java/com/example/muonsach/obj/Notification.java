package com.example.muonsach.obj;

public class Notification {
    private String imgNotifiAvt;
    private String tvNotifiName;
    private String tvNotifiDetail;
    private String tvNotifiTime;

    public Notification(String imgNotifiAvt, String tvNotifiName, String tvNotifiDetail,
                        String tvNotifiTime) {
        this.imgNotifiAvt = imgNotifiAvt;
        this.tvNotifiName = tvNotifiName;
        this.tvNotifiDetail = tvNotifiDetail;
        this.tvNotifiTime = tvNotifiTime;
    }

    public Notification() {
    }

    public String getImgNotifiAvt() {
        return imgNotifiAvt;
    }

    public void setImgNotifiAvt(String imgNotifiAvt) {
        this.imgNotifiAvt = imgNotifiAvt;
    }

    public String getTvNotifiName() {
        return tvNotifiName;
    }

    public void setTvNotifiName(String tvNotifiName) {
        this.tvNotifiName = tvNotifiName;
    }

    public String getTvNotifiDetail() {
        return tvNotifiDetail;
    }

    public void setTvNotifiDetail(String tvNotifiDetail) {
        this.tvNotifiDetail = tvNotifiDetail;
    }

    public String getTvNotifiTime() {
        return tvNotifiTime;
    }

    public void setTvNotifiTime(String tvNotifiTime) {
        this.tvNotifiTime = tvNotifiTime;
    }
}
