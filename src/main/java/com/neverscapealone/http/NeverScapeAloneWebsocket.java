package com.neverscapealone.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.enums.SearchMatches;
import com.neverscapealone.model.Payload;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import okhttp3.*;
import okio.ByteString;

import javax.annotation.Nullable;
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
    private NeverScapeAloneConfig config;
    private static String username;
    private static String discord;
    private static String token;
    private static String groupID;
    private static String passcode;
    private WebSocket socket;
    @Inject
    private NeverScapeAloneWebsocket()
    {
        this.gson = new Gson();
        this.okHttpClient = new OkHttpClient();
    }

    public void connect(String username, String discord, String token, String groupID, String passcode) {
        if (username.equals("")){
            log.debug("Cannot connect without a username!");
            return;
        }

        if (token == null){
            log.debug("Auth token is required to connect.");
            return;
        }

        NeverScapeAloneWebsocket.username = username;
        NeverScapeAloneWebsocket.discord = discord;
        NeverScapeAloneWebsocket.token = token;
        NeverScapeAloneWebsocket.groupID = groupID;

        System.out.println(username);
        System.out.println(discord);
        System.out.println(token);
        System.out.println(groupID);
        System.out.println(passcode);

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

        System.out.println(request);
        NeverScapeAloneWebsocket listener = new NeverScapeAloneWebsocket();
        socket = this.okHttpClient.newWebSocket(request, listener);
    }

    public void send(JsonObject jsonObject){
        socket.send(jsonObject.toString());
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
                connect(username, discord, token, groupID, passcode);
                JsonObject request_initial_match_data = new JsonObject();
                request_initial_match_data.addProperty("detail","request_match_data");
                send(request_initial_match_data);
                break;
            case DISCONNECTED:
                System.out.println("You have been disconnected by the host.");
                break;
            case BAD_PASSCODE:
                System.out.println("Bad passcode entered");
                break;
            case SUCCESSFUL_CONNECTION:
                System.out.println("Successful connection!");
                break;
            case SEARCH_MATCH_DATA:
                System.out.println(payload);
                SearchMatches searchMatches = payload.getSearchMatches();
                System.out.println(searchMatches.toString());
                // TODO fix this
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
        t.printStackTrace();
    }

}
