package com.neverscapealone.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.enums.ServerStatusCode;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServerStatus {
    @SerializedName("detail")
    ServerStatusCode status;
    @SerializedName("active_matches")
    JsonArray array;
}

