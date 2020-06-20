package com.example.park_mate;

public class Friend_Request {
    String senderemailid,fromemailid,sendername,fromname,senderimageurl,fromimageurl,status,requestid,city,gender;
    public Friend_Request()
    {

    }

    public String getSenderemailid() {
        return senderemailid;
    }

    public void setSenderemailid(String senderemailid) {
        this.senderemailid = senderemailid;
    }

    public String getFromemailid() {
        return fromemailid;
    }

    public void setFromemailid(String fromemailid) {
        this.fromemailid = fromemailid;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getSenderimageurl() {
        return senderimageurl;
    }

    public void setSenderimageurl(String senderimageurl) {
        this.senderimageurl = senderimageurl;
    }

    public String getFromimageurl() {
        return fromimageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFromimageurl(String fromimageurl) {
        this.fromimageurl = fromimageurl;
    }

    public String getRequestid() {
        return requestid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public  Friend_Request(String senderemailid, String fromemailid, String sendername, String fromname, String senderimageurl, String fromimageurl, String status, String requestid,String city,String gender)
    {
        this.senderemailid=senderemailid;
        this.fromemailid=fromemailid;
        this.sendername=sendername;
        this.fromname=fromname;
        this.senderimageurl=senderimageurl;
        this.fromimageurl=fromimageurl;
        this.status=status;
        this.requestid=requestid;
        this.city=city;
        this.gender=gender;
    }
}


