package com.example.ko.payload.request;


import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class SignupRequest {
    private String userName;
    private String nameCustomer;
    private String userPassWord;
    private String userEmail;
    private String phoneNumber;
    private String birthDay;
    private  String userImg;
    private String address;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdTime = new Date();
    private Boolean flagOnline = true;
    private Set<String> listRoles;

    public SignupRequest() {
    }
    public SignupRequest(String userName, String userPassWord, String userEmail, String phoneNumber, String birthDay, String userImg, String address, Set<String> listRoles) {
        this.userName = userName;
        this.userPassWord = userPassWord;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.userImg = userImg;
        this.address = address;
        this.listRoles = listRoles;
        this.flagOnline = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String dateNow = sdf.format(now);
        try{
            this.createdTime = sdf.parse(dateNow);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setFlagOnline(Boolean flagOnline) {
        this.flagOnline = flagOnline;
    }

    public void setListRoles(Set<String> listRoles) {
        this.listRoles = listRoles;
    }



    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Boolean getFlagOnline() {
        return flagOnline;
    }

    public Set<String> getListRoles() {
        return listRoles;
    }
}
