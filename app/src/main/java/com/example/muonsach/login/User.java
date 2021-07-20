package com.example.muonsach.login;

import android.text.TextUtils;
import android.util.Patterns;

public class User {
    private String userImg;
    private String email;
    private String password;
    private String address;
    private String fullname;

    public User(String email, String password, String address, String fullname) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.fullname = fullname;
    }

    public User() {
    }

    public static String formatEmail(String email){
        int indexOfDot = 0;
        for (int i = 0; i<email.length(); i++){
            if (email.charAt(i) == '.'){
                indexOfDot = i;
            }
        }
        return email.substring(0,indexOfDot);
    }
    public static String formatCart(String nameBook, String nameUser){
        return nameBook + "-" + nameUser;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isValidEmail(){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isValidPassword(){
        return !TextUtils.isEmpty(password) && password.length() > 6;
    }
}
