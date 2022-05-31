package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;

public enum ServerStatusCode {
    @SerializedName("alive")
    ALIVE,
    @SerializedName("maintenance")
    MAINTENANCE,
    UNREACHABLE,
    AUTH_FAILURE,
}

