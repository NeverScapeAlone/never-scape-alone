package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchMatchData {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("party_members")
    @Expose
    private String partyMembers;
    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("split_type")
    @Expose
    private String splitType;
    @SerializedName("accounts")
    @Expose
    private String accounts;
    @SerializedName("regions")
    @Expose
    private String regions;
    @SerializedName("player_count")
    @Expose
    private String playerCount;
    @SerializedName("party_leader")
    @Expose
    private String partyLeader;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SearchMatchData withId(String id) {
        this.id = id;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public SearchMatchData withActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public String getPartyMembers() {
        return partyMembers;
    }

    public void setPartyMembers(String partyMembers) {
        this.partyMembers = partyMembers;
    }

    public SearchMatchData withPartyMembers(String partyMembers) {
        this.partyMembers = partyMembers;
        return this;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public SearchMatchData withIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public SearchMatchData withExperience(String experience) {
        this.experience = experience;
        return this;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public SearchMatchData withSplitType(String splitType) {
        this.splitType = splitType;
        return this;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public SearchMatchData withAccounts(String accounts) {
        this.accounts = accounts;
        return this;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public SearchMatchData withRegions(String regions) {
        this.regions = regions;
        return this;
    }

    public String getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(String playerCount) {
        this.playerCount = playerCount;
    }

    public SearchMatchData withPlayerCount(String playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public String getPartyLeader() {
        return partyLeader;
    }

    public void setPartyLeader(String partyLeader) {
        this.partyLeader = partyLeader;
    }

    public SearchMatchData withPartyLeader(String partyLeader) {
        this.partyLeader = partyLeader;
        return this;
    }

}