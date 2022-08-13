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

public class MatchData {

    @SerializedName("discord_invite")
    @Expose
    private String discordInvite;
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("party_members")
    @Expose
    private String partyMembers;
    @SerializedName("group_passcode")
    @Expose
    private String groupPasscode;
    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("ban_list")
    @Expose
    private List<Integer> banList;
    @SerializedName("requirement")
    @Expose
    private Requirement requirement;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;

    public String getDiscordInvite() {
        return discordInvite;
    }

    public void setDiscordInvite(String discordInvite) {
        this.discordInvite = discordInvite;
    }

    public MatchData withDiscordInvite(String discordInvite) {
        this.discordInvite = discordInvite;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MatchData withId(String id) {
        this.id = id;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public MatchData withActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public String getPartyMembers() {
        return partyMembers;
    }

    public void setPartyMembers(String partyMembers) {
        this.partyMembers = partyMembers;
    }

    public MatchData withPartyMembers(String partyMembers) {
        this.partyMembers = partyMembers;
        return this;
    }

    public String getGroupPasscode() {
        return groupPasscode;
    }

    public void setGroupPasscode(String groupPasscode) {
        this.groupPasscode = groupPasscode;
    }

    public MatchData withGroupPasscode(String groupPasscode) {
        this.groupPasscode = groupPasscode;
        return this;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public MatchData withIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public MatchData withRequirement(Requirement requirement) {
        this.requirement = requirement;
        return this;
    }

    public List<Integer> getBanList() {
        return banList;
    }

    public void setBanList(List<Integer> banList) {
        this.banList = banList;
    }

    public MatchData withBanList(List<Integer> banList) {
        this.banList = banList;
        return this;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public MatchData withPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public MatchData withNotes(String notes) {
        this.notes = notes;
        return this;
    }

}