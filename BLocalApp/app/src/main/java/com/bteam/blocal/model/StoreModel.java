package com.bteam.blocal.model;

public class StoreModel {
    private String uid;
    private String name;
    private String owner;
    private double lat;
    private double lon;

    public StoreModel(String uid, String name, String owner, double lat, double lon) {
        this.uid = uid;
        this.name = name;
        this.owner = owner;
        this.lat = lat;
        this.lon = lon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
