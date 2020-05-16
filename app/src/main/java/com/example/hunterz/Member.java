package com.example.hunterz;

import android.graphics.Bitmap;

public class Member {

    private String id,fullName,phoneNo,email,nicNo,gender,address,dateOfBirth,sportType,password,status,image;
   // private int image;


    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(String id, String fullName, String phoneNo, String email, String nicNo, String gender, String address, String dateOfBirth, String sportType, String password, String status, String image) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.nicNo = nicNo;
        this.gender = gender;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.sportType = sportType;
        this.password = password;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
