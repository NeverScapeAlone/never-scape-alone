package com.neverscapealone.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.model.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import okhttp3.*;

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
    private WebSocket socket;

    public void connect(String username, String discord, String token, String groupID) {
        if (username.equals("")){
            log.debug("Cannot connect without a username!");
            return;
        }

        if (token == null){
            log.debug("Auth token is required to connect.");
            return;
        }

        Request request = new Request.Builder()
                .url(BASE_HTTP+"/"+groupID)
                .addHeader("User-Agent", RuneLite.USER_AGENT)
                .addHeader("Login", username)
                .addHeader("Discord", discord)
                .addHeader("Token", token)
                .addHeader("Time", Instant.now().toString())
                .build();

        NeverScapeAloneWebsocket listener = new NeverScapeAloneWebsocket();;
        socket = okHttpClient.newWebSocket(request, listener);

        JsonObject greetings = new JsonObject();
        greetings.addProperty("detail","Hello!");
        socket.send(greetings.toString());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving: " + text);
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
