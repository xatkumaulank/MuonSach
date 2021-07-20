package com.example.muonsach.obj;

public class GATMoney {
    String serialNumber;

    int value;


    public GATMoney(String serialNumber, int value) {
        this.serialNumber = serialNumber;
        this.value = value;
    }

    public GATMoney() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
