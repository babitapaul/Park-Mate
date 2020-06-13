package com.example.park_mate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String Emailid) {
        prefs.edit().putString("UserEmailid", Emailid).commit();
    }

    public String getusename() {
        String useremailidvalue = prefs.getString("UserEmailid","");
        return useremailidvalue ;
    }

    public  void setname(String name)
    {
        prefs.edit().putString("Name", name).commit();
    }
    public  String getname()
    {
        String Name = prefs.getString("Name","");
        return Name ;
    }
    public  void setgender(String name)
    {
        prefs.edit().putString("Gender", name).commit();
    }
    public  String getgender()
    {
        String GName = prefs.getString("Gender","");
        return GName ;
    }
    public  void setmobilenb(String mobile)
    {
        prefs.edit().putString("Mobile", mobile).commit();
    }
    public  String getmobile()
    {
        String Gmobile = prefs.getString("Mobile","");
        return Gmobile ;
    }

    public  void setaboutme(String abt)
    {
        prefs.edit().putString("About", abt).commit();
    }
    public  String getAboutme()
    {
        String GAboutme = prefs.getString("About","");
        return GAboutme ;
    }
    public  void setimageurl(String name)
    {
        prefs.edit().putString("Imageurl", name).commit();
    }
    public  String getimageurl()
    {
        String Gimage= prefs.getString("Imageurl","");
        return Gimage ;
    }
    public  void setStatus(String name)
    {
        prefs.edit().putString("Status", name).commit();
    }
    public  String getStatus()
    {
        String Gst = prefs.getString("Status","");
        return Gst;
    }
    public  void setAddress(String name)
    {
        prefs.edit().putString("Address", name).commit();
    }
    public  String getAddress()
    {
        String Gads = prefs.getString("Address","");
        return Gads ;
    }

    public  void setcity(String name)
    {
        prefs.edit().putString("City", name).commit();
    }
    public  String getcity()
    {
        String Gcty = prefs.getString("City","");
        return Gcty ;
    }
    public  void setsecondsurvey(Set<String> iindservey)
    {

        prefs.edit().putStringSet("secondsurvey", iindservey).commit();
    }
    public  Set<String> getsecondsurvey()
    {
        Set<String> rt = prefs.getStringSet("secondsurvey",null);
        return rt ;
    }
    public  void setparksurvey(Set<String> iindservey)
    {

        prefs.edit().putStringSet("parksurvey", iindservey).commit();
    }
    public  Set<String> getparksurvey()
    {
        Set<String> rt = prefs.getStringSet("parksurvey",null);
        return rt ;
    }
    public  void setvisiblity(String Status)
    {
        prefs.edit().putString("Setvisiblity",Status).commit();
    }
    public  String getvisiblity()
    {
        String sts = prefs.getString("Setvisiblity","");
        return sts ;
    }
}
