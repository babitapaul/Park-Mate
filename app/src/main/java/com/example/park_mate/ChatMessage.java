package com.example.park_mate;

import java.util.List;

public class ChatMessage {
    String chatid,toemailid,fromemailid,sendername,senderimageurl,datatime,toimage,toname;
    List<String> chat;
    public ChatMessage()
    {

    }


    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    ChatMessage(String chatid, String toemailid, String fromemailid, String sendername, String senderimageurl, List<String> chat, String datetime, String toimage, String toname)
    {
         this.toemailid=toemailid;
         this.fromemailid=fromemailid;
         this.sendername=sendername;
         this.senderimageurl=senderimageurl;
         this.chat=chat;
         this.datatime=datetime;
         this.toimage=toimage;
         this.toname=toname;
         this.chatid=chatid;
    }

    public List<String> getChat() {
        return chat;
    }

    public void setChat(List<String> chat) {
        this.chat = chat;
    }

    public String getToemailid() {
        return toemailid;
    }

    public String getToimage() {
        return toimage;
    }

    public void setToimage(String toimage) {
        this.toimage = toimage;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public void setToemailid(String toemailid) {
        this.toemailid = toemailid;
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

    public String getSenderimageurl() {
        return senderimageurl;
    }

    public void setSenderimageurl(String senderimageurl) {
        this.senderimageurl = senderimageurl;
    }



    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
