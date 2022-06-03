package com.neverscapealone.http;

import com.google.gson.*;
import com.neverscapealone.enums.ServerStatusCode;
import com.neverscapealone.model.ServerStatus;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Singleton
public class NeverScapeAloneClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final HttpUrl BASE_HTTP_URL = HttpUrl.parse(
            System.getProperty("NeverScapeAloneAPIPath", "http://touchgrass.online:5000/V1"));
    private static final Supplier<String> CURRENT_EPOCH_SUPPLIER = () -> String.valueOf(Instant.now().getEpochSecond());

    @Getter
    @AllArgsConstructor
    private enum ApiPath
    {
        SERVER_STATUS("server-status/"),
        USER_REGISTRATION("user-token/register"),
        OPTIONS_SKILL("options-skill/"),
        OPTIONS_MISC("options-misc/"),
        OPTIONS_MINIGAME("options-minigame/"),
        OPTIONS_BOSS("options-boss/"),
        USER_POINTS("user-points/"),
        USER_RATING_HISTORY("user-rating-history/"),
        USERS("users/"),
        REQUEST_HISTORY("request-history/")
        ;

        final String path;
    }

    public OkHttpClient okHttpClient;

    @Inject
    private Gson gson;

    @Getter
    @Setter
    private String pluginVersion;

    /**
     * Constructs a base URL for the given {@code path}.
     * @param path The path to get the base URL for
     * @return The base URL for the given {@code path}.
     */
    private HttpUrl getUrl(ApiPath path)
    {

        return BASE_HTTP_URL.newBuilder()
                .addPathSegments(path.getPath())
                .build();
    }
    @Inject
    public NeverScapeAloneClient(OkHttpClient rlClient)
    {
        okHttpClient = rlClient.newBuilder()
                .pingInterval(0, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(chain ->
                {
                    Request headerRequest = chain.request()
                            .newBuilder()
                            .header("Request-Epoch", CURRENT_EPOCH_SUPPLIER.get())
                            .build();
                    return chain.proceed(headerRequest);
                })
                .build();
    }

    public CompletableFuture<ServerStatus> requestServerStatus(String login, String token)
    {
        Request request = new Request.Builder()
                .url(getUrl(ApiPath.SERVER_STATUS).newBuilder()
                        .addQueryParameter("login", login)
                        .addQueryParameter("token", token)
                        .build())
                .build();

        CompletableFuture<ServerStatus> future = new CompletableFuture<>();
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.warn("Error obtaining Server Status data", e);
                if (e instanceof SocketTimeoutException || e instanceof ConnectException){
                    future.complete(ServerStatus.builder().status(ServerStatusCode.UNREACHABLE).build());
                    return;
                }
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {
                    future.complete(processResponse(gson, response, ServerStatus.class));
                }
                catch (UnauthorizedTokenException ute)
                {
                    future.complete(ServerStatus.builder().status(ServerStatusCode.AUTH_FAILURE).build());
                }
                catch (IOException e)
                {
                    log.warn("Error obtaining Server Status response", e);
                    future.completeExceptionally(e);
                }
                finally
                {
                    response.close();
                }
            }
        });

        return future;
    }

    public CompletableFuture<ServerStatus> registerUser(String login, String token)
    {
        Gson bdGson = gson.newBuilder().create();

        Request request = new Request.Builder()
                .url(getUrl(ApiPath.USER_REGISTRATION).newBuilder().build())
                .post(RequestBody.create(JSON, bdGson.toJson(new UserRegistration(login, token))))
                .build();

        CompletableFuture<ServerStatus> future = new CompletableFuture<>();
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.warn("Error obtaining Server Status data", e);
                if (e instanceof SocketTimeoutException || e instanceof ConnectException){
                    future.complete(ServerStatus.builder().status(ServerStatusCode.UNREACHABLE).build());
                    return;
                }
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {
                    future.complete(processResponse(gson, response, ServerStatus.class));
                }
                catch (UnauthorizedTokenException ute)
                {
                    future.complete(ServerStatus.builder().status(ServerStatusCode.AUTH_FAILURE).build());
                }
                catch (IOException e)
                {
                    log.warn("Error obtaining Server Status response", e);
                    future.completeExceptionally(e);
                }
                finally
                {
                    response.close();
                }
            }
        });

        return future;
    }


    /**
     * Processes the body of the given response and parses out the contained JSON object.
     * @param gson The {@link Gson} instance to use for parsing the JSON object in the {@code response}.
     * @param response The response containing the object to parse in {@link Response#body()}.
     * @param type The type of the JSON object to parse.
     * @param <T> The type of the JSON object to parse, inferred from {@code type}.
     * @return The parsed object, or {@code null} if the API returned a 404.
     * @throws IOException If the response is unsuccessful or the {@link Response#body()} contains malformed data.
     */
    private <T> T processResponse(Gson gson, Response response, Type type) throws IOException
    {
        if (!response.isSuccessful())
        {
            if (response.code() == 404)
            {
                return null;
            }
            else if (response.code() == 401)
            {
                throw new UnauthorizedTokenException("Auth Failure");
            }

            throw getIOException(response);
        }

        try
        {
            return gson.fromJson(response.body().string(), type);
        }
        catch (IOException | JsonSyntaxException ex)
        {
            throw new IOException("Error parsing API response body", ex);
        }
    }

    /**
     * Gets the {@link IOException} to return for when {@link Response#isSuccessful()} returns false.
     * @param response The response object to get the {@link IOException} for.
     * @return The {@link IOException} with the appropriate message for the given {@code response}.
     */
    private IOException getIOException(Response response)
    {
        int code = response.code();
        if (code >= 400 && code < 500)
        {
            try
            {
                Map<String, String> map = gson.fromJson(response.body().string(),
                        new TypeToken<Map<String, String>>()
                        {
                        }.getType());

                // "error" has priority if it exists, else use "detail" (FastAPI)
                String error = map.get("error");
                if (Strings.isNullOrEmpty(error))
                {
                    error = map.getOrDefault("detail", "Unknown " + code + " error from API");
                }
                return new IOException(error);
            }
            catch (IOException | JsonSyntaxException ex)
            {
                return new IOException("Error " + code + " with no error info", ex);
            }
        }

        return new IOException("Error " + code + " from API");
    }

    @Value
    private static class ServerStatusLoad
    {
        @SerializedName("login")
        String login;
        @SerializedName("token")
        String token;
    }

    /**
     * Serializes a {@link Boolean} as the integers {@code 0} or {@code 1}.
     */
    private static class BooleanToZeroOneSerializer implements JsonSerializer<Boolean>
    {
        @Override
        public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context)
        {
            return context.serialize(src ? 1 : 0);
        }
    }

    /**
     * Serializes/Unserializes {@link Instant} using {@link Instant#getEpochSecond()}/{@link Instant#ofEpochSecond(long)}
     */
    private static class InstantSecondsConverter implements JsonSerializer<Instant>, JsonDeserializer<Instant>
    {
        @Override
        public JsonElement serialize(Instant src, Type srcType, JsonSerializationContext context)
        {
            return new JsonPrimitive(src.getEpochSecond());
        }

        @Override
        public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException
        {
            return Instant.ofEpochSecond(json.getAsLong());
        }
    }
    @Value
    private static class UserRegistration
    {
        @SerializedName("login")
        String login;
        @SerializedName("token")
        String token;
    }
}
