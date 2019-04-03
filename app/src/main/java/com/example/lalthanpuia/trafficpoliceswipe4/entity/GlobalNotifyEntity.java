package com.example.lalthanpuia.trafficpoliceswipe4.entity;

public class GlobalNotifyEntity {
    String message;

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    String timeStamp;

    public GlobalNotifyEntity(String message, String timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
    public GlobalNotifyEntity() {

    }


}
