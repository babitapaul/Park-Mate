package com.example.park_mate;

public class ChatHistory {

     String chatid,chatpersoname,chatemailid,message,time,chatpersonimage;
    public ChatHistory()
    {


    }
    public ChatHistory(String chatpersonimage, String chatid, String chatpersoname, String chatemailid, String message, String time)
    {
        this.chatid=chatid;
        this.chatpersoname=chatpersoname;
        this.chatemailid=chatemailid;
        this.time=time;
        this.message=message;
        this.chatpersonimage=chatpersonimage;
    }

    public String getChatid() {
        return chatid;
    }

    public String getChatpersonimage() {
        return chatpersonimage;
    }

    public void setChatpersonimage(String chatpersonimage) {
        this.chatpersonimage = chatpersonimage;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getChatpersoname() {
        return chatpersoname;
    }

    public void setChatpersoname(String chatpersoname) {
        this.chatpersoname = chatpersoname;
    }

    public String getChatemailid() {
        return chatemailid;
    }

    public void setChatemailid(String chatemailid) {
        this.chatemailid = chatemailid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
