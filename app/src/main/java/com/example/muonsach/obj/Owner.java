package com.example.muonsach.obj;

public class Owner {
    private String imgOwner;
    private String nameOwner;
    private String addressOwner;
    private String phoneOwner;
    private String emailOwner;

    public Owner(String imgOwner, String nameOwner, String addressOwner, String phoneOwner, String emailOwner) {
        this.imgOwner = imgOwner;
        this.nameOwner = nameOwner;
        this.addressOwner = addressOwner;
        this.phoneOwner = phoneOwner;
        this.emailOwner = emailOwner;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public Owner() {
    }

    public String getImgOwner() {
        return imgOwner;
    }

    public void setImgOwner(String imgOwner) {
        this.imgOwner = imgOwner;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getAddressOwner() {
        return addressOwner;
    }

    public void setAddressOwner(String addressOwner) {
        this.addressOwner = addressOwner;
    }

    public String getPhoneOwner() {
        return phoneOwner;
    }

    public void setPhoneOwner(String phoneOwner) {
        this.phoneOwner = phoneOwner;
    }
}
