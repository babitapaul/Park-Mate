package com.example.park_mate;

public class Block_List {

    String time,blockto,blockpersonemailid,blockby,blockbyemailid,blocktoimage,reason;
    public Block_List()
    {

    }
    public Block_List(String time, String blockto, String blockpersonemailid, String blockby, String blockbyemailid, String blocktoimage, String reason)
    {
          this.time=time;
          this.blockto=blockto;
          this.blockpersonemailid=blockpersonemailid;
          this.blockby=blockby;
          this.blockbyemailid=blockbyemailid;
          this.blocktoimage=blocktoimage;
          this.reason=reason;
    }

    public String getBlockbyemailid() {
        return blockbyemailid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBlockbyemailid(String blockbyemailid) {
        this.blockbyemailid = blockbyemailid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBlockto() {
        return blockto;
    }

    public void setBlockto(String blockto) {
        this.blockto = blockto;
    }

    public String getBlockpersonemailid() {
        return blockpersonemailid;
    }

    public void setBlockpersonemailid(String blockpersonemailid) {
        this.blockpersonemailid = blockpersonemailid;
    }

    public String getBlockby() {
        return blockby;
    }

    public void setBlockby(String blockby) {
        this.blockby = blockby;
    }



    public String getBlocktoimage() {
        return blocktoimage;
    }

    public void setBlocktoimage(String blocktoimage) {
        this.blocktoimage = blocktoimage;
    }
}
