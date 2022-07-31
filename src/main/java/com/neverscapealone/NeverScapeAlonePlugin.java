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
import com.neverscapealone.enums.AccountTypeSelection;
import com.neverscapealone.enums.PingData;
import com.neverscapealone.enums.SoundPing;
import com.neverscapealone.enums.SoundPingEnum;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.sun.jna.Structure;
import jdk.vm.ci.meta.Local;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.vars.AccountType;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.discord.DiscordService;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.discord.DiscordPlugin;
import net.runelite.client.plugins.party.data.PartyData;
import net.runelite.client.plugins.party.messages.TilePing;
import net.runelite.client.plugins.worldhopper.ping.Ping;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.discord.DiscordUser;
import net.runelite.http.api.worlds.World;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(name = "NeverScapeAlone", description = "This plugin lets you partner up with other players to complete bosses, minigames, skills, and other miscellaneous activities.", tags = {"Matchmaking", "Party", "Skill", "PVP", "Boss", "Minigame"}, enabledByDefault = true)
public class NeverScapeAlonePlugin extends Plugin {
    @Inject
    private Client client;
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
    private NeverScapeAloneOverlay overlay;
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
    DiscordService discordService;
    public static NeverScapeAlonePanel panel;
    private NavigationButton navButton;
    public String username = "";
    public static String discordUsername = null;
    public static String discord_id = null;
    public Integer timer = 0;
    private static Integer old_x = 0;
    private static Integer old_y = 0;

    // garbage variable dump
    private String old_username = "";
    private Integer old_health = 0;
    private Integer old_base_health = 0;
    private Integer old_prayer = 0;
    private Integer old_base_prayer = 0;
    private Integer old_run_energy = 0;

    //
    public static ArrayList<PingData> pingDataArrayList = new ArrayList<PingData>();
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

        overlayManager.add(overlay);
        keyManager.registerKeyListener(hotkeyListener);

        if (StringUtils.isBlank(config.authToken())) {
            String USER_GENERATED_TOKEN = generateNewToken();
            configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.AUTH_TOKEN_KEY, USER_GENERATED_TOKEN);
        }

        DiscordUser discordUser = discordService.getCurrentUser();
        if (discordUser == null){
            NeverScapeAlonePlugin.discordUsername = "NULL";
            NeverScapeAlonePlugin.discord_id = "NULL";
        } else {
            NeverScapeAlonePlugin.discordUsername = "@"+discordUser.username+"#"+discordUser.discriminator;
            NeverScapeAlonePlugin.discord_id = discordService.getCurrentUser().userId;
        }

        panel = injector.getInstance(NeverScapeAlonePanel.class);
        final BufferedImage icon = ImageUtil.loadImageResource(NeverScapeAlonePlugin.class, "/tri-icon.png");
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
        keyManager.unregisterKeyListener(hotkeyListener);
        clientToolbar.removeNavigation(navButton);

        log.info("NeverScapeAlone stopped!");
    }

    @Subscribe
    public void onPingData(PingData pingdata){
        NeverScapeAlonePlugin.pingDataArrayList.add(pingdata);
        if(pingdata.getIsAlert()){
            pingTile(pingdata);
        } else {
            this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.NORMAL_PING));
        }
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
            case ERROR:
                if (config.soundEffectErrorBool()){
                    clientThread.invoke(() -> client.playSoundEffect(config.soundEffectError().getID()));
                }
                break;
        }
    }

    @Schedule(period = 1, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void timer() {
        if (!panel.isConnecting) {
            timer = 0;
            return;
        }
        String timer_string = "Queue Time: " + formatSeconds(timer);
        panel.setConnectingPanelQueueTime(timer_string);
        timer += 1;
    }

    public void Ping(boolean isAlert){
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

        if ((Objects.equals(NeverScapeAlonePlugin.old_x, x)) & (Objects.equals(NeverScapeAlonePlugin.old_y, y))){
            return;
        }
        NeverScapeAlonePlugin.old_x = x;
        NeverScapeAlonePlugin.old_y = y;

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
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id, config.authToken(), "0", null);
        ArrayList<String> queue_list = panel.queue_list;
        if (queue_list.size() == 0) {
            return;
        }
        String queues = new Gson().toJson(queue_list);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(queues);
        JsonArray jsonArray = (JsonArray) jsonElement;

        JsonObject create_request = new JsonObject();
        create_request.addProperty("detail", "quick_match");
        create_request.add("match_list", jsonArray);
        websocket.send(create_request);
        panel.connectingPanelManager();
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

    @Subscribe
    public void onGameTick(GameTick gameTick) {

        switch (client.getGameState()) {
            case LOGGED_IN:
                username = client.getLocalPlayer().getName();
                Integer health = client.getBoostedSkillLevel(Skill.HITPOINTS);
                Integer base_health = client.getRealSkillLevel(Skill.HITPOINTS);
                Integer prayer = client.getBoostedSkillLevel(Skill.PRAYER);
                Integer base_prayer = client.getRealSkillLevel(Skill.PRAYER);
                Integer run_energy = client.getEnergy();

                if (username.equals(old_username) &
                        health.equals(old_health) &
                        base_health.equals(old_base_health) &
                        prayer.equals(old_prayer) &
                        base_prayer.equals(old_base_prayer) &
                        run_energy.equals(old_run_energy)) {
                    return;
                }

                old_username = username;
                old_health = health;
                old_base_health = base_health;
                old_prayer = prayer;
                old_base_prayer = base_prayer;
                old_run_energy = run_energy;

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

                JsonObject create_request = new JsonObject();
                create_request.addProperty("detail", "set_status");
                create_request.add("status", status_payload);

                websocket.send(create_request);
                break;
        }
    }

    public void privateMatchPasscode(String matchID) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "ID: " + matchID + "\n" + "Enter passcode for Private Match:";
        String passcode = JOptionPane.showInputDialog(frame, message);
        if (passcode.length() > 0) {
            privateMatchJoin(matchID, passcode);
        }
    }

    public void privateMatchJoin(String matchID, String passcode) {
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), matchID, passcode);
        panel.connectingPanelManager();
    }

    public void publicMatchJoin(String matchID) {
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), matchID, null);
        panel.connectingPanelManager();
    }

    public void createMatchStart(ActionEvent actionEvent) {
        String activity = panel.step1_activity;
        String party_members = panel.party_member_count.getText();
        String experience = panel.experience_level.getSelectedItem().toString();
        String split_type = panel.party_loot.getSelectedItem().toString();
        String accounts = panel.account_type.getSelectedItem().toString();
        String regions = panel.region.getSelectedItem().toString();
        String group_passcode = panel.passcode.getText();

        String converted_party_size = convertInput(party_members);
        Pattern p = Pattern.compile("[0-9<>=&|]*");
        Matcher m = p.matcher(converted_party_size);
        if (m.matches()) {
            panel.party_member_count.setBackground(Color.green.darker().darker().darker());
            panel.party_member_count.setToolTipText("Examples: '2-3' 2 to 3 members, '2+' more than 2 members, '[1,8]' 1 to 8 members inclusive.");
        } else {
            panel.party_member_count.setBackground(Color.RED.darker().darker().darker());
            panel.party_member_count.setToolTipText("Try the help button to the right! Your input was invalid.");
            return;
        }

        if (checkPasscode(group_passcode)) {
            panel.passcode.setBackground(Color.green.darker().darker().darker());
            panel.passcode.setToolTipText("Input your group passcode here.");
        } else {
            panel.passcode.setBackground(Color.RED.darker().darker().darker());
            panel.passcode.setToolTipText("Your passcode contains invalid characters. Try the help button to the right!");
            return;
        }
        panel.connectingPanelManager();
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), "0", null);

        JsonObject sub_request = new JsonObject();
        sub_request.addProperty("activity", activity);
        sub_request.addProperty("party_members", party_members);
        sub_request.addProperty("experience", experience);
        sub_request.addProperty("split_type", split_type);
        sub_request.addProperty("accounts", accounts);
        sub_request.addProperty("regions", regions);
        sub_request.addProperty("group_passcode", group_passcode);

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

    public String convertInput(String text) {
        text = text.replaceAll("\\s", "");
        text = text.toLowerCase();
        // ex. [2,5] -> >=2&<=5
        text = text.replaceAll("(\\[)([0-9]{1,3})(,)([0-9]{1,3})(])", ">=$2&<=$4");
        // ex. (2,5] -> >2&<=5
        text = text.replaceAll("(\\(){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\])", ">$2&<=$4");
        // ex. [2,5) -> >=2&<5
        text = text.replaceAll("(\\[){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\))", ">=$2&<$4");
        // ex. (2,5) -> >2&<5
        text = text.replaceAll("(\\(){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\))", ">$2&<$4");
        // ex. (between*) 100 and 145 -> >=100&<=145
        text = text.replaceAll("(between){0,1}([0-9]{1,3})(to|-|&|and)([0-9]{1,3})", ">=$2&<=$4");
        // ex. lt4 -> <4
        text = text.replaceAll("(lessthan|lt|-lt|<){1}([0-9]{1,3})", "<$2");
        // ex. max5 -> <=5
        text = text.replaceAll("(max|maximum|le|-le|<=){1}([0-9]{1,3})", "<=$2");
        // ex. greaterthan20 -> >20
        text = text.replaceAll("(greaterthan|gt|-gt|>){1}([0-9]{1,3})", ">$2");
        // ex. min50 -> >=50
        text = text.replaceAll("(min|minimum|ge|-ge|>=){1}([0-9]{1,3})", ">=$2");
        // ex. 5+ -> >=5
        text = text.replaceAll("([0-9]{1,3})([+]{1})", ">=$1");
        // ex. 'and' -> &
        text = text.replaceAll("(and){1}", "&");
        // ex. 'or' -> ||
        text = text.replaceAll("(or){1}", "||");

        return text;
    }

    public void searchActiveMatches(ActionEvent actionEvent) {
        panel.searchBar.setEditable(false);
        panel.searchBar.setIcon(IconTextField.Icon.LOADING_DARKER);
        websocket.connect(username, NeverScapeAlonePlugin.discordUsername, NeverScapeAlonePlugin.discord_id,  config.authToken(), "0", null);
        String target = actionEvent.getActionCommand();
        if (target.length() <= 0) {
            panel.searchBar.setEditable(true);
            panel.searchBar.setIcon(IconTextField.Icon.SEARCH);
            return;
        }
        JsonObject search_request = new JsonObject();
        search_request.addProperty("detail", "search_match");
        search_request.addProperty("search", target);
        websocket.send(search_request);
    }

    @Provides
    NeverScapeAloneConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NeverScapeAloneConfig.class);
    }
}
