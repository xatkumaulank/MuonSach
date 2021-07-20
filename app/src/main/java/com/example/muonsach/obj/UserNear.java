package com.example.muonsach.obj;

public class UserNear {
    private String fullname;
    private String image;
    private String email;
    public UserNear(String fullname, String image,String email) {
        this.fullname = fullname;
        this.image = image;
        this.email = email;
    }

    public UserNear() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
