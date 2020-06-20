package com.example.park_mate;

public class ProfileReview {
    String time,personwhoreview,useremailidaboutreview,reviewmsg,imagereviewperson,postreviewpersonname;
    public ProfileReview()
    {

    }
    public ProfileReview(String time, String personwhoreview, String useremailidaboutreview, String reviewmsg, String imagereviewperson,String postreviewpersonname)
    {
        this.time=time;
        this.personwhoreview=personwhoreview;
        this.useremailidaboutreview=useremailidaboutreview;
        this.reviewmsg=reviewmsg;
        this.imagereviewperson=imagereviewperson;
        this.postreviewpersonname=postreviewpersonname;
    }

    public String getPostreviewpersonname() {
        return postreviewpersonname;
    }

    public void setPostreviewpersonname(String postreviewpersonname) {
        this.postreviewpersonname = postreviewpersonname;
    }

    public String getImagereviewperson() {
        return imagereviewperson;
    }

    public void setImagereviewperson(String imagereviewperson) {
        this.imagereviewperson = imagereviewperson;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPersonwhoreview() {
        return personwhoreview;
    }

    public void setPersonwhoreview(String personwhoreview) {
        this.personwhoreview = personwhoreview;
    }

    public String getUseremailidaboutreview() {
        return useremailidaboutreview;
    }

    public void setUseremailidaboutreview(String useremailidaboutreview) {
        this.useremailidaboutreview = useremailidaboutreview;
    }

    public String getReviewmsg() {
        return reviewmsg;
    }

    public void setReviewmsg(String reviewmsg) {
        this.reviewmsg = reviewmsg;
    }
}



