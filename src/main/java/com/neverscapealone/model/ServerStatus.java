package com.neverscapealone.model;
import com.google.gson.annotations.SerializedName;
import lombok.Value;

@Value
public class ServerStatus
{
    @SerializedName("login")
    String login;
    @SerializedName("token")
    String token;
}

