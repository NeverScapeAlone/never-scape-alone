package com.neverscapealone.model;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.enums.MatchInformation;
import com.neverscapealone.enums.ServerStatusCode;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;

@Value
@Builder
public class ServerPayload {
    @SerializedName("detail")
    ServerStatusCode status;
    @SerializedName("active_matches")
    JsonArray array;
    @SerializedName("match_information")
    ArrayList<MatchInformation> match_information;
    @SerializedName("server_message")
    String message;
}

