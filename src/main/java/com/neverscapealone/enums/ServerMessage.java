package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerMessage {
    @SerializedName("message")
    @Expose
    private String serverMessage = null;

    public String getServerMessage() {
        return serverMessage;
    }
}