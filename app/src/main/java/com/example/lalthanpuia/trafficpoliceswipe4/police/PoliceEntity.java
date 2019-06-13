package com.example.lalthanpuia.trafficpoliceswipe4.police;

public class PoliceEntity {

    String message;
    String date;
    String senderName;
    String latitude;
    String longitude;

    public PoliceEntity() {
    }

    public PoliceEntity(String message, String date, String senderName, String latitude, String longitude) {
        this.message = message;
        this.date = date;
        this.senderName = senderName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PoliceEntity{" +
                "message='" + message + '\'' +
                ", date='" + date + '\'' +
                ", senderName='" + senderName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
