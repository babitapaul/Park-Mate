package com.example.park_mate;

public class CommentPost {

     String wallpostid,comemtpersonname,comentpersonemailid,commentmessage,commentpersonimage,commenttime,coomentpersonimage;
    public CommentPost()
    {

    }
    public CommentPost(String coomentpersonimage, String wallpostid, String comemtpersonname, String comentpersonemailid, String commentmessage, String commentpersonimage, String commenttime)
    {
         this.coomentpersonimage=coomentpersonimage;
         this.wallpostid=wallpostid;
         this.comemtpersonname=comemtpersonname;
         this.comentpersonemailid=comentpersonemailid;
         this.commentmessage=commentmessage;
         this.commentpersonimage=commentpersonimage;
         this.commenttime=commenttime;
    }

    public String getCoomentpersonimage() {
        return coomentpersonimage;
    }

    public void setCoomentpersonimage(String coomentpersonimage) {
        this.coomentpersonimage = coomentpersonimage;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }

    public String getWallpostid() {
        return wallpostid;
    }

    public void setWallpostid(String wallpostid) {
        this.wallpostid = wallpostid;
    }

    public String getComemtpersonname() {
        return comemtpersonname;
    }

    public void setComemtpersonname(String comemtpersonname) {
        this.comemtpersonname = comemtpersonname;
    }

    public String getComentpersonemailid() {
        return comentpersonemailid;
    }

    public void setComentpersonemailid(String comentpersonemailid) {
        this.comentpersonemailid = comentpersonemailid;
    }

    public String getCommentmessage() {
        return commentmessage;
    }

    public void setCommentmessage(String commentmessage) {
        this.commentmessage = commentmessage;
    }

    public String getCommentpersonimage() {
        return commentpersonimage;
    }

    public void setCommentpersonimage(String commentpersonimage) {
        this.commentpersonimage = commentpersonimage;
    }
}
