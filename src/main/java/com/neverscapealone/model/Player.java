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

import java.util.List;

public class Player {

    @SerializedName("discord")
    @Expose
    private String discord;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("runewatch")
    @Expose
    private String runewatch;
    @SerializedName("wdr")
    @Expose
    private String wdr;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("kick_list")
    @Expose
    private List<Integer> kickList;
    @SerializedName("promote_list")
    @Expose
    private List<Integer> promoteList;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("isPartyLeader")
    @Expose
    private Boolean isPartyLeader;

    public String getDiscord() {
        return discord;
    }
    public void setDiscord(String discord) {
        this.discord = discord;
    }
    public Player withDiscord(String discord) {
        this.discord = discord;
        return this;
    }

    public Stats getStats() {
        return stats;
    }
    public void setStats(Stats stats) {
        this.stats = stats;
    }
    public Player withStats(Stats stats) {
        this.stats = stats;
        return this;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Player withLocation(Location location) {
        this.location = location;
        return this;
    }


    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Player withStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getRunewatch() {
        return runewatch;
    }
    public void setRunewatch(String runewatch) {
        this.runewatch = runewatch;
    }
    public Player withRunewatch(String runewatch) {
        this.runewatch = runewatch;
        return this;
    }

    public String getWdr() {
        return wdr;
    }
    public void setWdr(String wdr) {
        this.wdr = wdr;
    }
    public Player withWdr(String wdr) {
        this.wdr = wdr;
        return this;
    }

    public Boolean getVerified() {
        return verified;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    public Player withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }


    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Player withRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Player withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public List<Integer> getKickList() {
        return kickList;
    }
    public void setKickList(List<Integer> kickList) {
        this.kickList = kickList;
    }
    public Player withKickList(List<Integer> kickList) {
        this.kickList = kickList;
        return this;
    }

    public List<Integer> getPromoteList() {
        return promoteList;
    }
    public void setPromoteList(List<Integer> promoteList) {
        this.promoteList = promoteList;
    }
    public Player withPromoteList(List<Integer> promoteList) {
        this.promoteList = promoteList;
        return this;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public Player withLogin(String login) {
        this.login = login;
        return this;
    }

    public Boolean getIsPartyLeader() {
        return isPartyLeader;
    }
    public void setIsPartyLeader(Boolean isPartyLeader) {
        this.isPartyLeader = isPartyLeader;
    }
    public Player withIsPartyLeader(Boolean isPartyLeader) {
        this.isPartyLeader = isPartyLeader;
        return this;
    }

}