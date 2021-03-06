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
    BAD_RSN,
    @SerializedName("bad discord")
    BAD_DISCORD,
    @SerializedName("queue started")
    QUEUE_STARTED,
    @SerializedName("queue started failure")
    QUEUE_STARTED_FAILURE,
    @SerializedName("queue canceled")
    QUEUE_CANCELED,
    @SerializedName("queue canceled failure")
    QUEUE_CANCELED_FAILURE,
    @SerializedName("no active matches")
    NO_ACTIVE_MATCHES,
    @SerializedName("pending matches")
    PENDING_MATCHES,
    @SerializedName("match accepted")
    MATCH_ACCEPTED,
    @SerializedName("match ended")
    MATCH_ENDED,
    @SerializedName("rate limit")
    RATE_LIMIT;

}

