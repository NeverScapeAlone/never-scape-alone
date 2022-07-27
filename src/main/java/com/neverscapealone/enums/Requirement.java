package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Requirement {

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Requirement withExperience(String experience) {
        this.experience = experience;
        return this;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public Requirement withSplitType(String splitType) {
        this.splitType = splitType;
        return this;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public Requirement withAccounts(String accounts) {
        this.accounts = accounts;
        return this;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public Requirement withRegions(String regions) {
        this.regions = regions;
        return this;
    }

}