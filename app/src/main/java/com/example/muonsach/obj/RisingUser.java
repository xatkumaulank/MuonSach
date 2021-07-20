package com.example.muonsach.obj;

public class RisingUser {
    private String imgRisingUser;
    private String nameRisingUser;
    private String emailRisingUser;

    public RisingUser(String imgRisingUser, String nameRisingUser, String emailRisingUser) {
        this.imgRisingUser = imgRisingUser;
        this.nameRisingUser = nameRisingUser;
        this.emailRisingUser = emailRisingUser;
    }

    public RisingUser() {
    }

    public String getImgRisingUser() {
        return imgRisingUser;
    }

    public void setImgRisingUser(String imgRisingUser) {
        this.imgRisingUser = imgRisingUser;
    }

    public String getNameRisingUser() {
        return nameRisingUser;
    }

    public void setNameRisingUser(String nameRisingUser) {
        this.nameRisingUser = nameRisingUser;
    }

    public String getEmailRisingUser() {
        return emailRisingUser;
    }

    public void setEmailRisingUser(String emailRisingUser) {
        this.emailRisingUser = emailRisingUser;
    }
}
