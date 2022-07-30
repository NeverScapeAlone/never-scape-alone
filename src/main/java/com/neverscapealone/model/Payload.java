package com.neverscapealone.model;

import com.google.gson.annotations.SerializedName;
import com.neverscapealone.enums.*;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Payload {
    @SerializedName("detail") // server detail, or subject line. What is the message about?
    ServerStatusCode status;
    @SerializedName("server_message") // server message, is there any flavor text the server is sending as well?
    ServerMessage serverMessage;
    @SerializedName("join") // the group ID to join on a create_match request
    String group_id;
    @SerializedName("passcode") // the passcode that is sent on a create_match request
    String passcode;
    @SerializedName("search_match_data") // limited data to be sent over to the client, this is mainly for selecting a match
    SearchMatches search;
    @SerializedName("match_data") // data regarding the match itself
    MatchData matchData;
    @SerializedName("ping_data") // incoming ping data
    PingData pingData;
}

