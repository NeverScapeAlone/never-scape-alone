package com.neverscapealone.model;
import com.google.gson.annotations.SerializedName;
import com.neverscapealone.enums.MatchData;
import com.neverscapealone.enums.ServerStatusCode;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MatchPayload
{
    @SerializedName("match_information")
    List<MatchData> matchData;
}
