package com.neverscapealone;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.Payload;
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void privateMatchPasscode(String matchID){
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "ID: "+matchID+"\n"+"Enter passcode for Private Match:";
        String passcode = JOptionPane.showInputDialog(frame, message);
        if (passcode.length() > 0){
            privateMatchJoin(matchID, passcode);
        }
    }

    public void privateMatchJoin(String matchID, String passcode){
        websocket.connect(username, config.discordUsername(), config.authToken(), matchID, passcode);
        panel.connectingPanelManager();
    }

    public void publicMatchJoin(String matchID){
        websocket.connect(username, config.discordUsername(), config.authToken(), matchID, null);
        panel.connectingPanelManager();
    }

    public void createMatchStart(ActionEvent actionEvent){
        String activity = panel.step1_activity;
        String party_members = panel.party_member_count.getText();
        String experience = panel.experience_level.getSelectedItem().toString();
        String split_type =  panel.party_loot.getSelectedItem().toString();
        String accounts =  panel.account_type.getSelectedItem().toString();
        String regions = panel.region.getSelectedItem().toString();
        String group_passcode = panel.passcode.getText();

        String converted_party_size = convertInput(party_members);
        Pattern p = Pattern.compile("[0-9<>=&|]*");
        Matcher m = p.matcher(converted_party_size);
        if (m.matches()){
            panel.party_member_count.setBackground(Color.green.darker().darker().darker());
            panel.party_member_count.setToolTipText("Examples: '2-3' 2 to 3 members, '2+' more than 2 members, '[1,8]' 1 to 8 members inclusive.");
        } else {
            panel.party_member_count.setBackground(Color.RED.darker().darker().darker());
            panel.party_member_count.setToolTipText("Try the help button to the right! Your input was invalid.");
            return;
        }

        if (checkPasscode(group_passcode)){
            panel.passcode.setBackground(Color.green.darker().darker().darker());
            panel.passcode.setToolTipText("Input your group passcode here.");
        } else {
            panel.passcode.setBackground(Color.RED.darker().darker().darker());
            panel.passcode.setToolTipText("Your passcode contains invalid characters. Try the help button to the right!");
            return;
        }
        panel.connectingPanelManager();
        websocket.connect(username, config.discordUsername(), config.authToken(), "0", null);

        JsonObject sub_request = new JsonObject();
        sub_request.addProperty("activity", activity);
        sub_request.addProperty("party_members", party_members);
        sub_request.addProperty("experience", experience);
        sub_request.addProperty("split_type", split_type);
        sub_request.addProperty("accounts",accounts);
        sub_request.addProperty("regions",regions);
        sub_request.addProperty("group_passcode", group_passcode);

        JsonObject create_request = new JsonObject();
        create_request.addProperty("detail","create_match");
        create_request.add("create_match", sub_request);

        websocket.send(create_request);
    }

    public Boolean checkPasscode(String group_passcode){
        Pattern p = Pattern.compile("^[A-Za-z0-9-_ ]{0,64}$");
        Matcher m = p.matcher(group_passcode);
        return m.matches();
    }

    public String convertInput(String text){
        text = text.replaceAll("\\s", "");
        text = text.toLowerCase();
        // ex. [2,5] -> >=2&<=5
        text = text.replaceAll("(\\[)([0-9]{1,3})(,)([0-9]{1,3})(])",">=$2&<=$4");
        // ex. (2,5] -> >2&<=5
        text = text.replaceAll("(\\(){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\])",">$2&<=$4");
        // ex. [2,5) -> >=2&<5
        text = text.replaceAll("(\\[){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\))",">=$2&<$4");
        // ex. (2,5) -> >2&<5
        text = text.replaceAll("(\\(){1}([0-9]){1,3}(,){1}([0-9]{1,3})(\\))",">$2&<$4");
        // ex. (between*) 100 and 145 -> >=100&<=145
        text = text.replaceAll("(between){0,1}([0-9]{1,3})(to|-|&|and)([0-9]{1,3})",">=$2&<=$4");
        // ex. lt4 -> <4
        text = text.replaceAll("(lessthan|lt|-lt|<){1}([0-9]{1,3})","<$2");
        // ex. max5 -> <=5
        text = text.replaceAll("(max|maximum|le|-le|<=){1}([0-9]{1,3})","<=$2");
        // ex. greaterthan20 -> >20
        text = text.replaceAll("(greaterthan|gt|-gt|>){1}([0-9]{1,3})",">$2");
        // ex. min50 -> >=50
        text = text.replaceAll("(min|minimum|ge|-ge|>=){1}([0-9]{1,3})",">=$2");
        // ex. 5+ -> >=5
        text = text.replaceAll("([0-9]{1,3})([+]{1})",">=$1");
        // ex. 'and' -> &
        text = text.replaceAll("(and){1}","&");
        // ex. 'or' -> ||
        text = text.replaceAll("(or){1}","||");

        return text;
    }

    public void searchActiveMatches(ActionEvent actionEvent){
        websocket.connect(username, config.discordUsername(), config.authToken(), "0", null);
        String target = actionEvent.getActionCommand();
        if (target.length() <= 0)
        {
            return;
        }
        JsonObject search_request = new JsonObject();
        search_request.addProperty("detail","search_match");
        search_request.addProperty("search", target);
        websocket.send(search_request);
    }

    @Provides
    NeverScapeAloneConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NeverScapeAloneConfig.class);
    }
}
