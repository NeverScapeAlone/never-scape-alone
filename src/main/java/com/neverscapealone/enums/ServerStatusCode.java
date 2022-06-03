package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;

public enum ServerStatusCode {
    @SerializedName("alive")
    ALIVE,
    @SerializedName("maintenance")
    MAINTENANCE,
    UNREACHABLE,
    AUTH_FAILURE,
    @SerializedName("registering")
    REGISTERING,
    @SerializedName("registration failure")
    REGISTRATION_FAILURE,
    @SerializedName("registered")
    REGISTERED,
    @SerializedName("bad token")
    BAD_TOKEN,
    @SerializedName("bad header")
    BAD_HEADER,
    @SerializedName("bad rsn")
    BAD_RSN;
}

