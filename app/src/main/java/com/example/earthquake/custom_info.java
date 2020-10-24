package com.example.earthquake;

public class custom_info {
    double mag;
    String place;
    long time;
    String url;


    public custom_info(double m , String p , long t,String uri){
        mag = m;
        place = p;
        time =t;
        url =uri;
    }
    public String getPlace(){
        return place;
    }
    public long getTime(){
        return  time;
    }
    public double getMag(){
        return  mag;
    }
    public String getUrl(){
        return url;
    }

}


