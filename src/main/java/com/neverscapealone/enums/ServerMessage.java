package com.neverscapealone.enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMessage {
    @SerializedName("message")
    @Expose
    private String serverMessage = null;

    public String getServerMessage() {
        return serverMessage;
    }

    public ServerMessage buildServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
        return this;
    }

}