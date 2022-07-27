package com.neverscapealone.enums;

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

}