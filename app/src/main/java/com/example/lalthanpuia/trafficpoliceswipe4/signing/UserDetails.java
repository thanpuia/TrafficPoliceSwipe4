package com.example.lalthanpuia.trafficpoliceswipe4.signing;

public class UserDetails {

    String name;
    String phone;
    String address;
    String dob;
    String password;
    String role;
    String email;

    public UserDetails() {

    }

    public UserDetails(String name, String phone, String address, String dob, String password, String role, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
