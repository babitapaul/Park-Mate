package com.example.park_mate;

import java.util.List;

public class registration {

    String emailid, username, password, gender, mobileno, address, imageurl, status, aboutme, city;
    List<String> firstsurvey, secondsurvey, thirdsurvey;

    public registration() {

    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public registration(String city, String emailid, String username, String password, String gender, String mobileno, String address, List<String> firstsurvey, List<String> secondsurvey, List<String> thirdsurvey, String imageurl, String status, String aboutme) {
        this.emailid = emailid;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.mobileno = mobileno;
        this.address = address;
        this.firstsurvey = firstsurvey;
        this.secondsurvey = secondsurvey;
        this.thirdsurvey = thirdsurvey;
        this.imageurl = imageurl;
        this.status = status;
        this.aboutme = aboutme;
        this.city = city;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public List<String> getFirstsurvey() {
        return firstsurvey;
    }

    public void setFirstsurvey(List<String> firstsurvey) {
        this.firstsurvey = firstsurvey;
    }

    public List<String> getSecondsurvey() {
        return secondsurvey;
    }

    public void setSecondsurvey(List<String> secondsurvey) {
        this.secondsurvey = secondsurvey;
    }

    public List<String> getThirdsurvey() {
        return thirdsurvey;
    }

    public void setThirdsurvey(List<String> thirdsurvey) {
        this.thirdsurvey = thirdsurvey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailid() {
        return emailid;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }



}