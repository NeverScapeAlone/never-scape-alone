package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;

public enum MatchData {
    @SerializedName("ID")
    ID,
    @SerializedName("user_id")
    USER_ID,
    @SerializedName("party_identifier")
    PARTY_IDENTIFIER,
    @SerializedName("user_queue_ID")
    USER_QUEUE_ID,
    @SerializedName("activity")
    ACTIVITY,
    @SerializedName("party_member_count")
    PARTY_MEMBER_COUNT,
    @SerializedName("has_accepted")
    HAS_ACCEPTED,
    @SerializedName("timestamp")
    TIMESTAMP;
}


