package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
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
    @SerializedName("world")
    @Expose
    private Integer world;


    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Location withX(Integer x) {
        this.x = x;
        return this;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Location withY(Integer y) {
        this.y = y;
        return this;
    }

    public Integer getRegionX() {
        return regionX;
    }

    public void setRegionX(Integer regionX) {
        this.regionX = regionX;
    }

    public Location withRegionX(Integer regionX) {
        this.regionX = regionX;
        return this;
    }

    public Integer getRegionY() {
        return regionY;
    }

    public void setRegionY(Integer regionY) {
        this.regionY = regionY;
    }

    public Location withRegionY(Integer regionY) {
        this.regionY = regionY;
        return this;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public Location withRegionID(Integer regionID) {
        this.regionID = regionID;
        return this;
    }

    public Integer getPlane() {
        return plane;
    }

    public void setPlane(Integer plane) {
        this.plane = plane;
    }

    public Location withPlane(Integer plane) {
        this.plane = plane;
        return this;
    }

    public Integer getWorld() {
        return world;
    }

    public void setWorld(Integer world) {
        this.world = world;
    }

    public Location withWorld(Integer world) {
        this.world = world;
        return this;
    }

}