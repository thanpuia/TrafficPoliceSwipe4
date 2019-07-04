package com.example.lalthanpuia.trafficpoliceswipe4.signing;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

public class UserDetails {

    String email;
    String name;
    String password;
    String phone;
    ArrayList<Map<String,String>> posts;
    String role;

    public UserDetails() { }

    public UserDetails(String email, String name, String password, String phone, ArrayList<Map<String, String>> posts, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.posts = posts;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Map<String, String>> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Map<String, String>> posts) {
        this.posts = posts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
