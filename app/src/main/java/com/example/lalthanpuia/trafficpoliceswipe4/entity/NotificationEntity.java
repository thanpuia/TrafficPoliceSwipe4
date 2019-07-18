package com.example.lalthanpuia.trafficpoliceswipe4.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationEntity implements Parcelable {

    String date;
    String downloadURL;
    String message;
    String title;
    String sender_name;
    String sender_phone;
    String sender_role;
    long sortkey;

    String latitude;
    String longitude;
    String police_incharge;
    String status;

    public NotificationEntity() {
    }

    public NotificationEntity(String date, String downloadURL, String message, String title, String sender_name, String sender_phone, String sender_role, long sortkey, String latitude, String longitude, String police_incharge, String status) {
        this.date = date;
        this.downloadURL = downloadURL;
        this.message = message;
        this.title = title;
        this.sender_name = sender_name;
        this.sender_phone = sender_phone;
        this.sender_role = sender_role;
        this.sortkey = sortkey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.police_incharge = police_incharge;
        this.status = status;
    }

    protected NotificationEntity(Parcel in) {
        date = in.readString();
        downloadURL = in.readString();
        message = in.readString();
        title = in.readString();
        sender_name = in.readString();
        sender_phone = in.readString();
        sender_role = in.readString();
        sortkey = in.readLong();
        latitude = in.readString();
        longitude = in.readString();
        police_incharge = in.readString();
        status = in.readString();
    }

    public static final Creator<NotificationEntity> CREATOR = new Creator<NotificationEntity>() {
        @Override
        public NotificationEntity createFromParcel(Parcel in) {
            return new NotificationEntity(in);
        }

        @Override
        public NotificationEntity[] newArray(int size) {
            return new NotificationEntity[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getSender_role() {
        return sender_role;
    }

    public void setSender_role(String sender_role) {
        this.sender_role = sender_role;
    }

    public long getSortkey() {
        return sortkey;
    }

    public void setSortkey(long sortkey) {
        this.sortkey = sortkey;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPolice_incharge() {
        return police_incharge;
    }

    public void setPolice_incharge(String police_incharge) {
        this.police_incharge = police_incharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(downloadURL);
        dest.writeString(message);
        dest.writeString(title);
        dest.writeString(sender_name);
        dest.writeString(sender_phone);
        dest.writeString(sender_role);
        dest.writeLong(sortkey);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(police_incharge);
        dest.writeString(status);
    }
}
