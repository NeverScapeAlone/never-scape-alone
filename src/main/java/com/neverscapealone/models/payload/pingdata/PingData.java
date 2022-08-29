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

package com.neverscapealone.models.payload.pingdata;

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
    @SerializedName("color_r")
    @Expose
    private Integer colorR;
    @SerializedName("color_g")
    @Expose
    private Integer colorG;
    @SerializedName("color_b")
    @Expose
    private Integer colorB;
    @SerializedName("color_alpha")
    @Expose
    private Integer colorAlpha;
    @SerializedName("isAlert")
    @Expose
    private Boolean isAlert;

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

    public Integer getColorR() {
        return colorR;
    }

    public void setColorR(Integer colorR) {
        this.colorR = colorR;
    }

    public PingData withColorR(Integer colorR) {
        this.colorR = colorR;
        return this;
    }

    public Integer getColorG() {
        return colorG;
    }

    public void setColorG(Integer colorG) {
        this.colorG = colorG;
    }

    public PingData withColorG(Integer colorG) {
        this.colorG = colorG;
        return this;
    }

    public Integer getColorB() {
        return colorB;
    }

    public void setColorB(Integer colorB) {
        this.colorB = colorB;
    }

    public PingData withColorB(Integer colorB) {
        this.colorB = colorB;
        return this;
    }

    public Integer getColorAlpha() {
        return colorAlpha;
    }

    public void setColorAlpha(Integer colorAlpha) {
        this.colorAlpha = colorAlpha;
    }

    public PingData withColorAlpha(Integer colorAlpha) {
        this.colorAlpha = colorAlpha;
        return this;
    }

    public Boolean getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(Boolean isAlert) {
        this.isAlert = isAlert;
    }

    public PingData withIsAlert(Boolean isAlert) {
        this.isAlert = isAlert;
        return this;
    }

}