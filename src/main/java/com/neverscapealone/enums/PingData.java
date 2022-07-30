package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PingData {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("x")
    @Expose
    private Integer x;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("regionX")
    @Expose
    private Integer regionX;
    @SerializedName("regionY")
    @Expose
    private Integer regionY;
    @SerializedName("regionID")
    @Expose
    private Integer regionID;
    @SerializedName("plane")
    @Expose
    private Integer plane;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PingData withUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public PingData withX(Integer x) {
        this.x = x;
        return this;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public PingData withY(Integer y) {
        this.y = y;
        return this;
    }

    public Integer getRegionX() {
        return regionX;
    }

    public void setRegionX(Integer regionX) {
        this.regionX = regionX;
    }

    public PingData withRegionX(Integer regionX) {
        this.regionX = regionX;
        return this;
    }

    public Integer getRegionY() {
        return regionY;
    }

    public void setRegionY(Integer regionY) {
        this.regionY = regionY;
    }

    public PingData withRegionY(Integer regionY) {
        this.regionY = regionY;
        return this;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public PingData withRegionID(Integer regionID) {
        this.regionID = regionID;
        return this;
    }

    public Integer getPlane() {
        return plane;
    }

    public void setPlane(Integer plane) {
        this.plane = plane;
    }

    public PingData withPlane(Integer plane) {
        this.plane = plane;
        return this;
    }

}