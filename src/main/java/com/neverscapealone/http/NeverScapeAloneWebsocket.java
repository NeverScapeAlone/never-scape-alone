package com.neverscapealone.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neverscapealone.enums.ServerMessage;
import com.neverscapealone.model.Payload;
import jogamp.common.util.locks.SingletonInstanceServerSocket;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.EventBus;
import okhttp3.*;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.time.Instant;
import java.util.function.Supplier;

@Slf4j
@Singleton
public class NeverScapeAloneWebsocket extends WebSocketListener {
    private static final int NORMAL_CLOSURE = 1000;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final HttpUrl BASE_HTTP = HttpUrl.parse(System.getProperty("NeverScapeAloneAPIPath", "ws://touchgrass.online:5000/V2/lobby"));
    private static final Supplier<String> CURRENT_EPOCH_SUPPLIER = () -> String.valueOf(Instant.now().getEpochSecond());
    @Inject
    private OkHttpClient okHttpClient;
    @Inject
    private Gson gson;
    private static String username;
    private static String discord;
    private static String token;
    private static String groupID = "0";
    private static String passcode;
    private WebSocket socket;
    private final EventBus eventBus;
    @Inject
    private NeverScapeAloneWebsocket(EventBus eventbus)
    {
        this.eventBus = eventbus;
        this.gson = new Gson();
        this.okHttpClient = new OkHttpClient();
    }
    public void connect(String username, String discord, String token, String groupID, String passcode) {
        if (username.equals("")){
            this.eventBus.post(new ServerMessage().buildServerMessage("Please Login"));
            return;
        }

        if (token == null){
            this.eventBus.post(new ServerMessage().buildServerMessage("Refresh Plugin in Config"));
            return;
        }

        NeverScapeAloneWebsocket.username = username;
        NeverScapeAloneWebsocket.discord = discord;
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
                .addHeader("Token", token)
                .addHeader("Time", Instant.now().toString())
                .build();

        socket = this.okHttpClient.newWebSocket(request, this);

        JsonObject check_connection = new JsonObject();
        check_connection.addProperty("detail","check_connection");
        send(check_connection);
    }

    public void send(JsonObject jsonObject){
        socket.send(jsonObject.toString());
    }

    public String getGroupID(){
        return NeverScapeAloneWebsocket.groupID;
    }
    public void logoff(String reason){socket.close(1000, reason);}

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
                connect(username, discord, token, groupID, passcode);
                break;
            case DISCONNECTED:
                this.eventBus.post(new ServerMessage().buildServerMessage("You Were Disconnected"));
                break;
            case BAD_PASSCODE:
                this.eventBus.post(new ServerMessage().buildServerMessage("Bad Match Passcode"));
                break;
            case SUCCESSFUL_CONNECTION:
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
        System.out.println("Closing: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (t instanceof ConnectException) {
            this.eventBus.post(new ServerMessage().buildServerMessage("No Server Connection"));
        } else if (t instanceof SocketException || t instanceof EOFException) {
            this.eventBus.post(new ServerMessage().buildServerMessage("Connection Reset"));
        } else {
            this.eventBus.post(new ServerMessage().buildServerMessage("Unknown Error"));
            t.printStackTrace();
        }
    }

}
