package com.neverscapealone.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueueButtonStatus
{
    OFFLINE("offline"),
    START_QUEUE("start_queue"),
    CANCEL_QUEUE("end_queue"),
    REQUEST("request"),
    SELECT_ACTIVITY_MATCH("select_activity_match"),
    END_SESSION("end_session");

    private String name;
}