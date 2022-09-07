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

package com.neverscapealone;

import com.google.gson.*;
import com.google.inject.Provides;
import com.neverscapealone.enums.*;
import com.neverscapealone.models.panelstate.PanelState;
import com.neverscapealone.models.payload.chatdata.ChatData;
import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.models.payload.pingdata.PingData;
import com.neverscapealone.models.soundping.SoundPing;
import com.neverscapealone.overlays.NeverScapeAloneMinimapOverlay;
import com.neverscapealone.overlays.NeverScapeAlonePingOverlay;
import com.neverscapealone.overlays.NeverScapeAlonePlayerOverlay;
import com.neverscapealone.overlays.NeverScapeAloneWorldMapOverlay;
import com.neverscapealone.socket.NeverScapeAloneWebsocket;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.connecting.ConnectingPanelClass;
import com.neverscapealone.ui.utils.Icons;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.discord.DiscordService;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;
import net.runelite.discord.DiscordUser;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(name = "NeverScapeAlone", description = "This plugin lets you partner up with other players to complete bosses, minigames, skills, and other miscellaneous activities.", tags = {"Matchmaking", "Party", "Skill", "PVP", "Boss", "Minigame","LFG"}, enabledByDefault = true)
public class NeverScapeAlonePlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private PluginManager pluginManager;
    @Inject
    private ClientThread clientThread;
    @Inject
    private ConfigManager configManager;
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private NeverScapeAloneConfig config;
    @Inject
    private NeverScapeAloneWebsocket websocket;
    @Inject
    private NeverScapeAlonePingOverlay overlay;
    @Inject
    private NeverScapeAloneMinimapOverlay minimapOverlay;
    @Inject
    private NeverScapeAloneWorldMapOverlay worldMapOverlay;
    @Inject
    private NeverScapeAlonePlayerOverlay playerOverlay;
    @Inject
    private NeverScapeAloneHotkeyListener hotkeyListener;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private MouseManager mouseManager;
    @Inject
    private Notifier notifier;
    @Inject
    private KeyManager keyManager;
    @Inject
    private EventBus eventBus;
    @Inject
    private ChatMessageManager chatMessageManager;
    @Inject
    SpriteManager spriteManager;
    @Inject
    ItemManager itemManager;
    @Inject
    DiscordService discordService;
    public static NeverScapeAlonePanel panel;
    private NavigationButton navButton;
    public String username = "";
    @Getter
    @Setter
    public static String discordUsername = null;
    @Getter
    @Setter
    public static String discord_id = null;
    public Integer timer = 0;
    public Integer tileTimer = 0;
    public static Integer matchSize = 0;
    private static long last_ping = 0;
    private static Integer old_ping_x = 0;
    private static Integer old_ping_y = 0;
    private static Integer old_loc_x = 0;
    private static Integer old_loc_y = 0;
    public static boolean cycleQueue = false;
    public static JsonObject queuePayload = new JsonObject();
    private String old_username = "";
    private Integer old_special_attack = -1;
    private Integer old_health = 0;
    private Integer old_base_health = 0;
    private Integer old_prayer = 0;
    private Integer old_base_prayer = 0;
    private Integer old_run_energy = 0;
    public static ArrayList<PingData> pingDataArrayList = new ArrayList<>();
    public static MatchData matchData = new MatchData();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    protected void startUp() throws Exception {

        log.info("NeverScapeAlone started!");

        try
        {
            final Properties props = new Properties();
            props.load(NeverScapeAlonePlugin.class.getResourceAsStream("/version.txt"));
            websocket.setPluginVersion(props.getProperty("version"));
        }
        catch (Exception e)
        {
            log.error("NeverScapeAlone plugin version not found", e);
            pluginManager.setPluginEnabled(this, false);
            return;
        }

        overlayManager.add(overlay);
        overlayManager.add(minimapOverlay);
        overlayManager.add(worldMapOverlay);
        overlayManager.add(playerOverlay);
        keyManager.registerKeyListener(hotkeyListener);

        if (StringUtils.isBlank(config.authToken())) {
            String USER_GENERATED_TOKEN = generateNewToken();
            configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.AUTH_TOKEN_KEY, USER_GENERATED_TOKEN);
        }

        panel = injector.getInstance(NeverScapeAlonePanel.class);
        final BufferedImage icon = ImageUtil.loadImageResource(NeverScapeAlonePlugin.class, "/com/neverscapealone/other/tri-icon.png");
        navButton = NavigationButton.builder()
                .panel(panel)
                .tooltip("NeverScapeAlone")
                .icon(icon)
                .priority(90)
                .build();
        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        overlayManager.remove(minimapOverlay);
        overlayManager.remove(worldMapOverlay);
        overlayManager.remove(playerOverlay);
        keyManager.unregisterKeyListener(hotkeyListener);
        clientToolbar.removeNavigation(navButton);
        client.clearHintArrow();
        websocket.logoff("Client closed");
        log.info("NeverScapeAlone stopped!");
    }

    public void updateDiscordInformation(){
        if ((NeverScapeAlonePlugin.discord_id != null) & (NeverScapeAlonePlugin.discordUsername != null)){
            return;
        }

        DiscordUser discordUser = discordService.getCurrentUser();
        if (discordUser == null){
            setDiscordUsername(null);
            setDiscord_id(null);
        } else {
            discordUsername = "@"+discordUser.username+"#"+discordUser.discriminator;
            String encodedUsername = sanitizeDiscordUsername(discordUsername);
            setDiscordUsername(encodedUsername);
            setDiscord_id(discordUser.userId);
        }
    }

    public String sanitizeDiscordUsername(String username){
        String encodedString = Base64.getEncoder().encodeToString(username.getBytes());
        return encodedString;
    }

    @Subscribe
    public void onPingData(PingData pingdata){
        NeverScapeAlonePlugin.pingDataArrayList.add(pingdata);
        for(int x = NeverScapeAlonePlugin.pingDataArrayList.size(); x > config.maxPingCount(); x--)
        {
            client.clearHintArrow();
            NeverScapeAlonePlugin.pingDataArrayList.remove(0);
        }

        if(pingdata.getIsAlert()){
            pingTile(pingdata);
        } else {
            this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.NORMAL_PING));
        }
    }

    @Subscribe
    public void onMatchData(MatchData matchData) {
        NeverScapeAlonePlugin.matchData = matchData;
    }

    private void pingTile(PingData pingData){
        WorldPoint point = WorldPoint.fromRegion(pingData.getRegionID(), pingData.getRegionX(), pingData.getRegionY(), pingData.getPlane());
        client.setHintArrow(point);
        this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.ALERT_PING));
    }

    @Subscribe
    public void onSoundPing(SoundPing soundPing) {
        switch(soundPing.getSound()){
            case NORMAL_PING:
                if (config.soundEffectPingBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectPing().getID()));
                }
                break;
            case ALERT_PING:
                if (config.soundEffectAlertBool()){
                clientThread.invoke(() -> client.playSoundEffect(config.soundEffectAlertPing().getID()));
                }
                break;
            case MATCH_JOIN:
                if (config.soundEffectMatchJoinBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectMatchJoin().getID()));
                }
                break;
            case MATCH_LEAVE:
                if (config.soundEffectMatchLeaveBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectMatchLeave().getID()));
                    client.clearHintArrow();
                }
                break;
            case PLAYER_JOIN:
                if (config.soundEffectTeamJoinBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectPlayerJoin().getID()));
                }
                break;
            case PLAYER_LEAVE:
                if (config.soundEffectTeamLeaveBool()){
                clientThread.invoke(() -> client.playSoundEffect(config.soundEffectPlayerLeave().getID()));
                }
                break;
            case CHAT:
                if (config.soundEffectChatBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectChat().getID()));
                }
                break;
            case ERROR:
                if (config.soundEffectErrorBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectError().getID()));
                }
                break;
            case BUTTON_PRESS:
                if (config.soundEffectButtonBool()){
                    clientThread.invoke(() -> client.playSoundEffect(SoundEffectSelectionEnum.UI_BOOP.getID()));
                }
                break;
        }
    }


    @Subscribe
    public void onPanelState(PanelState panelState) {
        switch(panelState.getPanelStateEnum()){
            case MATCH:
                playerGlobalRefresh();
                NeverScapeAlonePanel.setView(PanelStateEnum.MATCH);
                NeverScapeAlonePanel.refreshView();
                break;
        }
    }

    @Subscribe
    public void onChatData(ChatData chatData) {
        if (!config.chatShowBoolean()){
            return;
        }
        String displayName = chatData.getUsername();
        String message = chatData.getMessage();
        String msg = displayName+": "+message;
        sendChatStatusMessage(msg);
    }

    /**
     * Sends a message to the in-game chatbox.
     * @param msg The message to send.
     * Made by @Cyborger1
     */
    public void sendChatStatusMessage(String msg)
    {
        String CHAT_MESSAGE_HEADER = "[NeverScapeAlone] ";
        final String message = new ChatMessageBuilder()
                .append(ChatColorType.HIGHLIGHT)
                .append(CHAT_MESSAGE_HEADER + msg)
                .build();

        chatMessageManager.queue(
                QueuedMessage.builder()
                        .type(config.chatMessageType())
                        .runeLiteFormattedMessage(message)
                        .build());
    }

    public void playerOptionAction(ActionEvent actionEvent, PlayerButtonOptionEnum playerButtonOptionEnum){
        if (!NeverScapeAloneWebsocket.isSocketConnected){
            return;
        }
        this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.BUTTON_PRESS));
        JsonObject payload = new JsonObject();
        payload.addProperty("detail",playerButtonOptionEnum.getDetail());
        payload.addProperty(playerButtonOptionEnum.getDetail(), actionEvent.getActionCommand());
        websocket.send(payload);
    }

    @Schedule(period = 1, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void timer() {
        if (!panel.isConnecting) {
            timer = 0;
            return;
        }
        String timer_string = "Queue Time: " + formatSeconds(timer);
        ConnectingPanelClass.setConnectingPanelQueueTime(timer_string);
        timer += 1;
    }

    @Schedule(period = 1, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void decayTiles(){
        if (NeverScapeAlonePlugin.pingDataArrayList.size() == 0){
            return;
        }
        tileTimer +=1;

        if (tileTimer>= config.pingDecay()){
            if (NeverScapeAlonePlugin.pingDataArrayList.size() == 1){
                client.clearHintArrow();
            }
            NeverScapeAlonePlugin.pingDataArrayList.remove(0);
            tileTimer = 0;
        }
    }

    public boolean pingSpeedLimit(){
        // ~10 pings every second, to complement server-side rate-limiter
        long currentTimeMillis = System.currentTimeMillis();
        long gap = currentTimeMillis - NeverScapeAlonePlugin.last_ping;
        if (gap >= 100){
            NeverScapeAlonePlugin.last_ping = currentTimeMillis;
            return true;
        }
        return false;
    }

    public void Ping(boolean isAlert){
        if (!pingSpeedLimit()){
            return;
        }
        if (websocket == null){
            return; // return if no websocket
        }
        Tile ping_tile = client.getSelectedSceneTile();
        if (ping_tile == null){
            return; // return if no tile selected
        }
        WorldPoint wp = ping_tile.getWorldLocation();
        Integer x = wp.getX();
        Integer y = wp.getY();
        Integer regionX = wp.getRegionX();
        Integer regionY = wp.getRegionY();
        Integer regionID = wp.getRegionID();
        Integer plane = wp.getPlane();

        if ((Objects.equals(NeverScapeAlonePlugin.old_ping_x, x)) & (Objects.equals(NeverScapeAlonePlugin.old_ping_y, y))){
            return;
        }
        NeverScapeAlonePlugin.old_ping_x = x;
        NeverScapeAlonePlugin.old_ping_y = y;

        JsonObject create_request = new JsonObject();
        JsonObject ping_payload = new JsonObject();
        ping_payload.addProperty("username",username);
        ping_payload.addProperty("x", x);
        ping_payload.addProperty("y", y);
        ping_payload.addProperty("regionX", regionX);
        ping_payload.addProperty("regionY", regionY);
        ping_payload.addProperty("regionID", regionID);
        ping_payload.addProperty("plane", plane);
        ping_payload.addProperty("color_r", config.pingColor().getRed());
        ping_payload.addProperty("color_g", config.pingColor().getGreen());
        ping_payload.addProperty("color_b", config.pingColor().getBlue());
        ping_payload.addProperty("color_alpha", config.pingColor().getAlpha());
        ping_payload.addProperty("isAlert", isAlert);

        create_request.addProperty("detail","ping");
        create_request.add("ping_payload",ping_payload);
        websocket.send(create_request);
    }

    public static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10) formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10) formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10) formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    public void quickMatchQueueStart(ActionEvent actionEvent) {
        updateDiscordInformation();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id, config.authToken(), "0", null);
        ArrayList<String> queue_list = NeverScapeAlonePanel.queue_list;
        if (queue_list.size() == 0) {
            queue_list.add("RANDOM");
        } else {
            queue_list.remove("RANDOM");
        }
        String queues = new Gson().toJson(queue_list);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(queues);
        JsonArray jsonArray = (JsonArray) jsonElement;

        JsonObject create_request = new JsonObject();
        create_request.addProperty("detail", "quick_match");
        create_request.add("match_list", jsonArray);
        NeverScapeAlonePanel.setView(PanelStateEnum.CONNECTING);
        NeverScapeAlonePanel.refreshView();
        NeverScapeAlonePlugin.queuePayload = create_request;
        NeverScapeAlonePlugin.cycleQueue = true;
    }

    public void addImageToLabel(JLabel jLabel, com.neverscapealone.models.payload.matchdata.player.inventory.Item item){
        boolean stackable = true;
        if (item.getQuantity() == 1){
            stackable = false;
        }
        itemManager.getImage(item.getItemID(), item.getQuantity(),stackable).addTo(jLabel);
    }

    public BufferedImage getSprite(int spriteID, int file){
        // personally, I prefer diet coke
        return spriteManager.getSprite(spriteID, file);
    }

    @Schedule(period = 3, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void sendQueueRequest() {
        if (!NeverScapeAloneWebsocket.isSocketConnected){
            return;
        }
        if (!Objects.equals(websocket.getGroupID(), "0")){
            return;
        }
        if (!NeverScapeAlonePlugin.cycleQueue){
            return;
        }
        if (NeverScapeAlonePlugin.queuePayload == null){
            return;
        }
        websocket.send(NeverScapeAlonePlugin.queuePayload);
    }

    @Schedule(period = 60, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void playerStatsUpdate(){
        if (client.getGameState() != GameState.LOGGED_IN){
            return;
        }
        if (websocket == null){
            return;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return;
        }


        JsonObject sub_payload = new JsonObject();
        for (Skill skill : Skill.values()){
            JsonObject skill_attributes = new JsonObject();
            skill_attributes.addProperty("boosted", client.getBoostedSkillLevel(skill));
            skill_attributes.addProperty("real", client.getRealSkillLevel(skill));
            skill_attributes.addProperty("experience", client.getSkillExperience(skill));
            sub_payload.add(skill.getName(),skill_attributes);
        }

        JsonObject payload = new JsonObject();
        payload.addProperty("detail","stats_update");
        payload.add("stats", sub_payload);
        websocket.send(payload);
    }

    @Schedule(period = 6, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void playerEquipmentUpdate(){
        // sends equipment update every 5 seconds - not on inventory change
        if (client.getGameState() != GameState.LOGGED_IN){
            return;
        }
        if (websocket == null){
            return;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return;
        }
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipment == null){
            return;
        }

        JsonObject sub_payload = new JsonObject();
        for (EquipmentSlotEnum equipmentSlotEnum : EquipmentSlotEnum.values()){
            Item item = equipment.getItem(equipmentSlotEnum.getId());

            if (item == null){
                continue;
            }

            JsonObject slot_attributes = new JsonObject();
            slot_attributes.addProperty("item_id", item.getId());
            slot_attributes.addProperty("item_amount", item.getQuantity());

            sub_payload.add(equipmentSlotEnum.getName(), slot_attributes);
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("detail","equipment_update");
        payload.add("equipment", sub_payload);
        websocket.send(payload);
    }

    @Schedule(period = 5, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void playerInventoryUpdate(){
        // sends inventory update every 5 seconds - not on inventory change bc the server would perish.
        if (client.getGameState() != GameState.LOGGED_IN){
            return;
        }
        if (websocket == null){
            return;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return;
        }

        ItemContainer itemContainer = client.getItemContainer(InventoryID.INVENTORY);
        if (itemContainer == null){
            return;
        }

        JsonArray itemList = new JsonArray();
        int containerSize = itemContainer.size();
        for (int itemSlot = 0; itemSlot < containerSize; itemSlot++) {
            Item item = itemContainer.getItem(itemSlot);
            JsonObject i = new JsonObject();
            int itemID = -1;
            int itemAmount = 0;
            if (item != null){
                itemID = item.getId();
                itemAmount = item.getQuantity();
            }
            i.addProperty("item_id", itemID);
            i.addProperty("item_amount", itemAmount);
            itemList.add(i);
        }
        JsonObject inventory_payload = new JsonObject();
        inventory_payload.addProperty("detail","inventory_update");
        inventory_payload.add("inventory", itemList);
        websocket.send(inventory_payload);
    }


    @Schedule(period = 10, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void playerLocationUpdate(){
        if (client.getGameState() != GameState.LOGGED_IN){
            return;
        }
        if (websocket == null){
            return;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return;
        }

        Player player = client.getLocalPlayer();
        int world = client.getWorld();

        WorldPoint wp = player.getWorldLocation();
        int y = wp.getY();
        int x = wp.getX();
        int regionX = wp.getRegionX();
        int regionY = wp.getRegionY();
        int regionID = wp.getRegionID();
        int plane = wp.getPlane();

        if ((NeverScapeAlonePlugin.old_loc_y == y) & (NeverScapeAlonePlugin.old_loc_x == x)){
            return;
        }
        NeverScapeAlonePlugin.old_loc_x = x;
        NeverScapeAlonePlugin.old_loc_y = y;

        JsonObject sub_payload = new JsonObject();
        sub_payload.addProperty("y", y);
        sub_payload.addProperty("x", x);
        sub_payload.addProperty("regionX", regionX);
        sub_payload.addProperty("regionY", regionY);
        sub_payload.addProperty("regionID", regionID);
        sub_payload.addProperty("plane", plane);
        sub_payload.addProperty("world", world);

        JsonObject location_payload = new JsonObject();
        location_payload.addProperty("detail","player_location");
        location_payload.add("location", sub_payload);

        websocket.send(location_payload);
    }

    public void playerStatusUpdate(){
        if (client.getGameState() != GameState.LOGGED_IN){
            return;
        }
        if (websocket == null){
            return;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return;
        }

    }

    public void playerGlobalRefresh(){
        playerLocationUpdate();
        playerInventoryUpdate();
        playerEquipmentUpdate();
        playerStatsUpdate();
    }

    @Subscribe
    public void onGameTick(GameTick gameTick) {

        if (client.getGameState() == GameState.LOGGED_IN) {
            username = client.getLocalPlayer().getName();
            Integer health = client.getBoostedSkillLevel(Skill.HITPOINTS);
            Integer base_health = client.getRealSkillLevel(Skill.HITPOINTS);
            Integer prayer = client.getBoostedSkillLevel(Skill.PRAYER);
            Integer base_prayer = client.getRealSkillLevel(Skill.PRAYER);
            Integer run_energy = client.getEnergy();
            Integer special_attack = client.getVarpValue(VarPlayer.SPECIAL_ATTACK_PERCENT.getId());

            if (username.equals(old_username) &
                    health.equals(old_health) &
                    base_health.equals(old_base_health) &
                    prayer.equals(old_prayer) &
                    base_prayer.equals(old_base_prayer) &
                    run_energy.equals(old_run_energy) &
                    special_attack.equals(old_special_attack)) {
                return;
            }

            old_username = username;
            old_health = health;
            old_base_health = base_health;
            old_prayer = prayer;
            old_base_prayer = base_prayer;
            old_run_energy = run_energy;
            old_special_attack = special_attack;

            if (websocket.getGroupID().equals("0")) {
                return;
            }

            JsonObject status_payload = new JsonObject();
            status_payload.addProperty("username", username);
            status_payload.addProperty("hp", health);
            status_payload.addProperty("base_hp", base_health);
            status_payload.addProperty("prayer", prayer);
            status_payload.addProperty("base_prayer", base_prayer);
            status_payload.addProperty("run_energy", run_energy);
            status_payload.addProperty("special_attack", special_attack);

            JsonObject create_request = new JsonObject();
            create_request.addProperty("detail", "set_status");
            create_request.add("status", status_payload);

            websocket.send(create_request);
        }
    }

    public void privateMatchRuneGuard(String matchID, boolean RuneGuard) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        String rGuardString = "disabled.";
        if (RuneGuard){
            rGuardString = "enabled.";
        }
        String message = "ID: " + matchID + "\n" +
                "This match has RuneGuard " + rGuardString + "\n" +
                "Enter passcode for Private Match:";
        String passcode = JOptionPane.showInputDialog(frame, message);
        if (passcode.length() > 0) {
            privateMatchJoin(matchID, passcode);
        }
    }

    public void publicMatchRuneGuard(String matchID, boolean RuneGuard) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        String rGuardString = "disabled.";
        if (RuneGuard){
            rGuardString = "enabled.";
        }

        String message = "ID: " + matchID + "\n" +
                         "This match has RuneGuard " + rGuardString;
        if (JOptionPane.showOptionDialog(null,
                                        message,
                                        null,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        Icons.PUBLIC_ICON,
                                        new String[]{"JOIN","CANCEL"},
                                        "JOIN") == JOptionPane.YES_OPTION)
        {
            publicMatchJoin(matchID);
        };
    }

    public void privateMatchJoin(String matchID, String passcode) {
        updateDiscordInformation();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), matchID, passcode);
        NeverScapeAlonePanel.setView(PanelStateEnum.CONNECTING);
        NeverScapeAlonePanel.refreshView();
    }

    public void publicMatchJoin(String matchID) {
        updateDiscordInformation();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), matchID, null);
        NeverScapeAlonePanel.setView(PanelStateEnum.CONNECTING);
        NeverScapeAlonePanel.refreshView();
    }

    public void sendChatMessage(ActionEvent actionEvent, String message){
        if (message.length() == 0){
            return;
        }

        NeverScapeAlonePanel.chatBar.setText("");
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("message", message);
        JsonObject payload = new JsonObject();
        payload.addProperty("detail","chat");
        payload.add("chat_message", messageJson);
        websocket.send(payload);
    }

    public void createMatchStart(ActionEvent actionEvent) {
        String activity = NeverScapeAlonePanel.step1_activity;
        String party_members = String.valueOf(NeverScapeAlonePanel.party_member_count.getValue());
        String experience = NeverScapeAlonePanel.experience_level.getSelectedItem().toString();
        String split_type = NeverScapeAlonePanel.party_loot.getSelectedItem().toString();
        String accounts = NeverScapeAlonePanel.account_type.getSelectedItem().toString();
        String regions = NeverScapeAlonePanel.region.getSelectedItem().toString();
        Boolean runeGuard = NeverScapeAlonePanel.RuneGuard.isSelected();
        String group_passcode = NeverScapeAlonePanel.passcode.getText();
        String group_notes = NeverScapeAlonePanel.notes.getText();

        if (checkPasscode(group_passcode)) {
            NeverScapeAlonePanel.passcode.setBackground(NeverScapeAlonePanel.HIGHLIGHT_COLOR);
            NeverScapeAlonePanel.passcode.setToolTipText("Input your group passcode here.");
        } else {
            NeverScapeAlonePanel.passcode.setBackground(NeverScapeAlonePanel.WARNING_COLOR);
            NeverScapeAlonePanel.passcode.setToolTipText("Your passcode contains invalid characters. Try the help button to the right!");
            return;
        }

        NeverScapeAlonePanel.setView(PanelStateEnum.CONNECTING);
        NeverScapeAlonePanel.refreshView();
        updateDiscordInformation();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), "0", null);

        JsonObject sub_request = new JsonObject();
        sub_request.addProperty("activity", activity);
        sub_request.addProperty("party_members", party_members);
        sub_request.addProperty("experience", experience);
        sub_request.addProperty("split_type", split_type);
        sub_request.addProperty("accounts", accounts);
        sub_request.addProperty("regions", regions);
        sub_request.addProperty("RuneGuard", runeGuard);
        sub_request.addProperty("group_passcode", group_passcode);
        sub_request.addProperty("notes", group_notes);

        JsonObject create_request = new JsonObject();
        create_request.addProperty("detail", "create_match");
        create_request.add("create_match", sub_request);

        websocket.send(create_request);
    }

    public Boolean checkPasscode(String group_passcode) {
        Pattern p = Pattern.compile("^[A-Za-z0-9-_ ]{0,64}$");
        Matcher m = p.matcher(group_passcode);
        return m.matches();
    }

    public void searchActiveMatches(ActionEvent actionEvent) {
        NeverScapeAlonePanel.searchBar.setEditable(false);
        NeverScapeAlonePanel.searchBar.setIcon(IconTextField.Icon.LOADING_DARKER);
        updateDiscordInformation();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), "0", null);
        String target = actionEvent.getActionCommand();
        if (target.length() <= 0) {
            NeverScapeAlonePanel.searchBar.setEditable(true);
            NeverScapeAlonePanel.searchBar.setIcon(IconTextField.Icon.SEARCH);
            return;
        }
        JsonObject search_request = new JsonObject();
        search_request.addProperty("detail", "search_match");
        search_request.addProperty("search", target);
        websocket.send(search_request);
    }

    public static void switchToUserProfile(ActionEvent actionEvent) {
        // do something in the future
    }

    public static void switchToHomePanel(ActionEvent actionEvent) {
        NeverScapeAlonePanel.setRefreshView(actionEvent, PanelStateEnum.HOME);
    }

    @Provides
    NeverScapeAloneConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NeverScapeAloneConfig.class);
    }
}
