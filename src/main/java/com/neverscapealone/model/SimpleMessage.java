package com.neverscapealone.model;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimpleMessage {
    @SerializedName("detail")
    String message;
}
