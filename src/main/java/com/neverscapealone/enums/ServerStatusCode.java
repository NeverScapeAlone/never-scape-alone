package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;

public enum ServerStatusCode {
    @SerializedName("request join new match")
    JOIN_NEW_MATCH,
    @SerializedName("disconnected")
    DISCONNECTED;
}

