package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;

public enum ServerStatusCode {
    @SerializedName("request join new match")
    JOIN_NEW_MATCH,
    @SerializedName("disconnected")
    DISCONNECTED,
    @SerializedName("successful connection")
    SUCCESSFUL_CONNECTION,
    @SerializedName("bad passcode")
    BAD_PASSCODE,
    @SerializedName("match update")
    MATCH_UPDATE,
    @SerializedName("global message")
    GLOBAL_MESSAGE,
    @SerializedName("search match data")
    SEARCH_MATCH_DATA,
    @SerializedName("incoming ping")
    INCOMING_PING;
}

