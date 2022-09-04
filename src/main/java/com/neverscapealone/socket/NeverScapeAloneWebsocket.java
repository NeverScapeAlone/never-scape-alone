/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neverscapealone.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.enums.SoundPingEnum;
import com.neverscapealone.models.panelstate.PanelState;
import com.neverscapealone.models.payload.Payload;
import com.neverscapealone.models.payload.servermessage.ServerMessage;
import com.neverscapealone.models.soundping.SoundPing;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.EventBus;
import okhttp3.*;

import java.io.EOFException;
import java.net.SocketException;
import java.time.Instant;
import java.util.function.Supplier;

@Slf4j
@Singleton
public class NeverScapeAloneWebsocket extends WebSocketListener {
    private static final int NORMAL_CLOSURE = 1000;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final HttpUrl BASE_HTTP = HttpUrl.parse(System.getProperty("NeverScapeAloneAPIPath", "http://neverscapealone.com:5500/V2/lobby"));
    private static final Supplier<String> CURRENT_EPOCH_SUPPLIER = () -> String.valueOf(Instant.now().getEpochSecond());
    @Inject
    private OkHttpClient okHttpClient;
    @Inject
    private Gson gson;

    private final NeverScapeAloneConfig config;
    private static String username;
    private static String discord;
    private static String discord_id;
    private static String token;
    private static String groupID = "0";
    private static String passcode;
    private static int retryAttempts = 0;
    public static boolean isSocketConnected = false;
    private WebSocket socket;
    private final EventBus eventBus;

    @Getter
    @Setter
    private String pluginVersion;

    private final Supplier<String> pluginVersionSupplier = () ->
            (pluginVersion != null && !pluginVersion.isEmpty()) ? pluginVersion : "INVALID-VERSION";

    @Inject
    private NeverScapeAloneWebsocket(EventBus eventbus, NeverScapeAloneConfig config)
    {
        this.eventBus = eventbus;
        this.gson = new Gson();
        this.config = config;
        this.okHttpClient = new OkHttpClient();
    }
    public void connect(String username, String discord, String discord_id, String token, String groupID, String passcode) {
        if (username.equals("")){
            this.eventBus.post(new ServerMessage().buildServerMessage("Please Login"));
            return;
        }

        if (token == null){
            this.eventBus.post(new ServerMessage().buildServerMessage("Refresh Plugin in Config"));
            return;
        }

        if (discord == null){
            discord = "NULL";
        }

        if (discord_id == null){
            discord_id = "NULL";
        }

        NeverScapeAloneWebsocket.username = username;
        NeverScapeAloneWebsocket.discord = discord;
        NeverScapeAloneWebsocket.discord_id = discord_id;
        NeverScapeAloneWebsocket.token = token;
        NeverScapeAloneWebsocket.groupID = groupID;

        String url = BASE_HTTP+"/"+groupID+"/"+"0";
        NeverScapeAloneWebsocket.passcode = "0";
        if (passcode != null){
            if (passcode.length() != 0){
                url = BASE_HTTP+"/"+groupID+"/"+passcode;
                NeverScapeAloneWebsocket.passcode = passcode;
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", RuneLite.USER_AGENT)
                .addHeader("Login", username)
                .addHeader("Discord", discord)
                .addHeader("Discord_ID", discord_id)
                .addHeader("Token", token)
                .addHeader("Time", Instant.now().toString())
                .addHeader("Version", pluginVersionSupplier.get())
                .build();

        socket = this.okHttpClient.newWebSocket(request, this);

        JsonObject check_connection = new JsonObject();
        check_connection.addProperty("detail","check_connection");
        send(check_connection);
    }

    public void send(JsonObject jsonObject){
        if (socket == null){
            return;
        }
        socket.send(jsonObject.toString());
    }

    public String getGroupID(){
        return NeverScapeAloneWebsocket.groupID;
    }
    public void logoff(String reason){socket.close(1000, reason);}

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        isSocketConnected = true;
        NeverScapeAloneWebsocket.retryAttempts = 0;
    }

    @Override
    public void onMessage(WebSocket socket, String text) {
        Payload payload = this.gson.fromJson(text, Payload.class);
        switch(payload.getStatus()){
            case JOIN_NEW_MATCH:
                groupID = payload.getGroup_id();
                if (payload.getPasscode() == null){
                    passcode = "0";
                } else {
                    passcode = payload.getPasscode();
                }
                socket.close(1000, "Ending connection to join a new match");
                connect(username, discord, discord_id, token, groupID, passcode);
                break;
            case INCOMING_PING:
                this.eventBus.post(payload.getPingData());
                break;
            case DISCONNECTED:
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.MATCH_LEAVE));
                this.eventBus.post(new ServerMessage().buildServerMessage("You Were Disconnected"));
                break;
            case BAD_PASSCODE:
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.ERROR));
                this.eventBus.post(new ServerMessage().buildServerMessage("Bad Match Passcode"));
                break;
            case SUCCESSFUL_CONNECTION:
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.MATCH_JOIN));
                this.eventBus.post(new PanelState().buildPanelState(PanelStateEnum.MATCH));
            case MATCH_UPDATE:
                this.eventBus.post(payload.getMatchData());
                break;
            case SEARCH_MATCH_DATA:
                this.eventBus.post(payload.getSearch());
                break;
            case GLOBAL_MESSAGE:
                this.eventBus.post(payload.getServerMessage());
                break;
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        isSocketConnected = false;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (t instanceof SocketException || t instanceof EOFException) {
            NeverScapeAloneWebsocket.retryAttempts += 1;
            if (NeverScapeAloneWebsocket.retryAttempts < config.numberOfRetries()){
                this.eventBus.post(new ServerMessage().buildServerMessage("Reconnecting: "+String.valueOf(retryAttempts)+"/"+String.valueOf(config.numberOfRetries())));
                connect(NeverScapeAloneWebsocket.username, NeverScapeAloneWebsocket.discord, NeverScapeAloneWebsocket.discord_id, NeverScapeAloneWebsocket.token, NeverScapeAloneWebsocket.groupID, NeverScapeAloneWebsocket.passcode);
            } else {
                this.eventBus.post(new ServerMessage().buildServerMessage("No Server Connection"));
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.ERROR));
            }
        } else {
            this.eventBus.post(new ServerMessage().buildServerMessage("Unknown Error"));
            this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.ERROR));
            t.printStackTrace();
        }
    }

}
