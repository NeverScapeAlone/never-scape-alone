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
    @SerializedName("search match data")
    SEARCH_MATCH_DATA;
}

