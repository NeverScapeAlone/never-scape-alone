package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueButtonStatus
{
    @SerializedName("Offline")
    OFFLINE("Offline"),
    @SerializedName("Online")
    ONLINE("Online"),
    @SerializedName("Start Queue")
    START_QUEUE("Start Queue"),
    @SerializedName("Cancel Queue")
    CANCEL_QUEUE("Cancel Queue"),
    @SerializedName("Accept Queue")
    REQUEST("Accept Queue"),
    @SerializedName("Select Activities")
    SELECT_ACTIVITY_MATCH("Select Activities"),
    @SerializedName("End Session")
    END_SESSION("End Session");

    private String name;
}