package com.example.muonsach.login;

public class LoginPrecenter {
    ILogin iLogin;

    public LoginPrecenter(ILogin iLogin) {
        this.iLogin = iLogin;
    }
    public void login(User user){
        if (user.isValidEmail() && user.isValidPassword()){
            iLogin.onSuccess();
        }else {
            iLogin.onError();
        }
    }
}
