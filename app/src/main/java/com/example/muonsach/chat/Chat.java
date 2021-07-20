package com.example.muonsach.chat;

public class Chat {
    private String imgsender;
    private String imgreceiver;
    private String sender;
    private String receiver;
    private String message;

    public Chat() {
    }

    public Chat(String imgsender, String sender, String receiver, String message) {
        this.imgsender = imgsender;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getImgsender() {
        return imgsender;
    }

    public void setImgsender(String imgsender) {
        this.imgsender = imgsender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
