package com.neverscapealone;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;

@Slf4j
@PluginDescriptor(name = "NeverScapeAlone", description = "This plugin lets you partner up with other players to complete bosses, minigames, skills, and other miscellaneous activities.", tags = {"Matchmaking", "Party", "Skill", "PVP", "Boss", "Minigame"}, enabledByDefault = true)
public class NeverScapeAlonePlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private ConfigManager configManager;
    @Inject
    private ClientToolbar clientToolbar;
    @Inject
    private NeverScapeAloneConfig config;
    @Inject
    private NeverScapeAloneWebsocket websocket;
    public static NeverScapeAlonePanel panel;
    private NavigationButton navButton;
    public String username = "Ferrariic";
    public Integer timer = 0;
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

        if (StringUtils.isBlank(config.authToken())) {
            String USER_GENERATED_TOKEN = generateNewToken();
            configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.AUTH_TOKEN_KEY, USER_GENERATED_TOKEN);
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
        log.info("NeverScapeAlone stopped!");
    }

    @Subscribe
    private void onPlayerSpawned(PlayerSpawned event) {
        if (username != "") {
            return;
        }

        Player player = event.getPlayer();

        if (player == null) {
            return;
        }

        if (player == client.getLocalPlayer()) {
            username = player.getName();
            return;
        }
    }

    @Schedule(period=1, unit=ChronoUnit.SECONDS, asynchronous=true)
    public void timer(){
        if (!panel.isConnecting) {
            timer = 0;
            return;
        }
        String timer_string = "Queue Time: " + formatSeconds(timer);
        panel.setConnectingPanelQueueTime(timer_string);
        timer += 1;
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

    public void quickMatchQueueStart(ActionEvent actionEvent){
        ArrayList<String> queue_list = panel.queue_list;
        if (queue_list.size() == 0){
            return;
        }
        panel.connectingPanelManager();
    }

    public void createMatchStart(ActionEvent actionEvent){
        // creates a match with the current panel configuration
        // transitions to connecting... panel and sends websocket request with payload.

        Integer max_party_members = (Integer) panel.max_party_member_count.getValue();
        Integer min_party_members = (Integer) panel.min_party_member_count.getValue();
        String experience = panel.experience_level.getSelectedItem().toString();
        String split_type =  panel.party_loot.getSelectedItem().toString();
        String accounts =  panel.account_type.getSelectedItem().toString();
        String regions = panel.region.getSelectedItem().toString();
        String group_passcode = panel.passcode.toString();

        System.out.println(max_party_members);
        System.out.println(accounts);
    }

    public void searchActiveMatches(ActionEvent actionEvent){
        websocket.connect(username, config.discordUsername(), config.authToken(), "default");
        String target = actionEvent.getActionCommand();
        if (target.length() <= 0)
        {
            return;
        }
        JsonObject search_request = new JsonObject();
        search_request.addProperty("detail","search match");
        search_request.addProperty("search", target);
        websocket.send(search_request);
    }


    @Provides
    NeverScapeAloneConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NeverScapeAloneConfig.class);
    }
}
