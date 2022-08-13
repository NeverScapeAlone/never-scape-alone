/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neverscapealone.model;

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