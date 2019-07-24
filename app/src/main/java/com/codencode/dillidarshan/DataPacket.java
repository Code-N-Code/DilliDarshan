package com.codencode.dillidarshan;

public class DataPacket {
    String busStop;
    String imgRef;
    String metro;
    String name;
    double rating;
    String uid;

    public DataPacket()
    {

    }

    public DataPacket(String busStop , String imgRef , String metro , String name , double rating , String uid)
    {
        this.busStop = busStop;
        this.imgRef = imgRef;
        this.metro = metro;
        this.name = name;
        this.rating = rating;
        this.uid = uid;
    }

    public String getBusStop() {
        return busStop;
    }

    public String getImgRef() {
        return imgRef;
    }

    public String getMetro() {
        return metro;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getUid() {
        return uid;
    }
}
