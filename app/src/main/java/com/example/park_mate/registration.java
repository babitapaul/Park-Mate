package com.example.park_mate;

public class registration {
    String emailid,username,password,gender,mobileno,address;
    public registration(String emailid,String username,String password,String gender,String mobileno,String address)
    {
        this.emailid=emailid;
        this.username=username;
        this.password=password;
        this.gender=gender;
        this.mobileno=mobileno;
        this.address=address;
    }
    public String getEmailid() {
        return emailid;
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
