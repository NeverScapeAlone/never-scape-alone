package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("user_id")
    @Expose
    private Integer userId;
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