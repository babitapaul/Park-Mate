package com.example.park_mate;

public class Timeline {
    String timelineid,username,emailid,city,address,postmessage,time,parkimageurl,userimageurl,mobilenb,parkname;
    public  Timeline()
    {

    }
    public  Timeline(String timelineid,String parkname,String username,String emailid,String city,String address,String postmessage,String time,String parkimageurl,String userimageurl,String mobilenb)
    {
        this.username=username;
        this.timelineid=timelineid;
        this.emailid=emailid;
        this.city=city;
        this.address=address;
        this.postmessage=postmessage;
        this.time=time;
        this.parkimageurl=parkimageurl;
        this.userimageurl=userimageurl;
        this.mobilenb=mobilenb;
        this.parkname=parkname;
    }

    public String getTimelineid() {
        return timelineid;
    }

    public void setTimelineid(String timelineid) {
        this.timelineid = timelineid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostmessage() {
        return postmessage;
    }

    public void setPostmessage(String postmessage) {
        this.postmessage = postmessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParkimageurl() {
        return parkimageurl;
    }

    public void setParkimageurl(String parkimageurl) {
        this.parkimageurl = parkimageurl;
    }

    public String getUserimageurl() {
        return userimageurl;
    }

    public void setUserimageurl(String userimageurl) {
        this.userimageurl = userimageurl;
    }

    public String getMobilenb() {
        return mobilenb;
    }

    public void setMobilenb(String mobilenb) {
        this.mobilenb = mobilenb;
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname;
    }
}


