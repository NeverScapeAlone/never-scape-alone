package com.neverscapealone.model;

import com.google.gson.annotations.SerializedName;
import com.neverscapealone.enums.ServerStatusCode;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Payload {
    @SerializedName("detail") // server detail, or subject line. What is the message about?
    ServerStatusCode status;
    @SerializedName("server_message") // server message, is there any flavor text the server is sending as well?
    String message;
    @SerializedName("join") // the group ID to join on a create_match request
    String group_id;
    @SerializedName("passcode") // the passcode that is sent on a create_match request
    String passcode;
}

