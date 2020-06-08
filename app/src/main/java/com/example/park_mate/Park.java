package com.example.park_mate;

public class Park {

    String parkname,address,imageurl,state,city,country,type;
    public Park()
    {

    }
    public Park(String parkname, String address, String state, String city, String country, String type, String imageurl)
    {
         this.parkname=parkname;
         this.address=address;
         this.city=city;
         this.country=country;
         this.imageurl=imageurl;
         this.type=type;
         this.state=state;


    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
