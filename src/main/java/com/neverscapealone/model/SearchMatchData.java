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
    @SerializedName("match_version")
    @Expose
    private String matchVersion;
    @SerializedName("notes")
    @Expose
    private String notes;

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

    public String getMatchVersion() {
        return matchVersion;
    }

    public void setMatchVersion(String matchVersion) {
        this.matchVersion = matchVersion;
    }

    public SearchMatchData withMatchVersion(String matchVersion) {
        this.matchVersion = matchVersion;
        return this;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public SearchMatchData withNotes(String notes) {
        this.notes = notes;
        return this;
    }

}