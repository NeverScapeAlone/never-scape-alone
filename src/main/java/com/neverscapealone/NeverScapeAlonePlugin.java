package com.neverscapealone;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import com.neverscapealone.enums.MatchInformation;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.http.NeverScapeAloneClient;
import com.neverscapealone.model.ServerStatus;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.plugins.party.PartyPlugin;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.swing.*;
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
    public NeverScapeAloneClient clientConnection;

    public static NeverScapeAlonePanel panel;
    private NavigationButton navButton;

    // Party

    public static PartyPlugin partyPlugin;
    public static PluginManager pluginManager;

    // basic user information for panel
    private String username = "";

    // tick count
    public int queueTime = 0;
    public int offlineCheckSecondsCounter = 0;
    public int matchInformationTicker = 0;
    private ArrayList<MatchInformation> oldMatchInformation;

    // server state
    public static ServerStatus serverStatusState;
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

        // create and set a new auth token
        if (StringUtils.isBlank(config.authToken())) {
            String USER_GENERATED_TOKEN = generateNewToken();
            configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.AUTH_TOKEN_KEY, USER_GENERATED_TOKEN);
        }

        panel = injector.getInstance(NeverScapeAlonePanel.class);
        final BufferedImage icon = ImageUtil.loadImageResource(NeverScapeAlonePlugin.class, "/tri-icon.png");
        navButton = NavigationButton.builder().panel(panel).tooltip("NeverScapeAlone").icon(icon).priority(90).build();
        clientToolbar.addNavigation(navButton);

        switch (client.getGameState()) {
            case LOGGED_IN:
                checkServerStatus(username);
            case LOGIN_SCREEN:
                checkServerStatus(username);
        }
    }

    public void checkServerStatus(String login) {
        // if no login, for some reason, shut everything down.
        if ((login.isEmpty())) {
            panel.setServerPanel("LOGIN TO RUNESCAPE", "To start the plugin, please login to Old School RuneScape.", panel.COLOR_INFO);
            panel.matchButtonManager(QueueButtonStatus.OFFLINE);
            return;
        }

        // if server is being checked, state in status bar and put queue button manager offline + deactivate buttons
        panel.setServerPanel("CHECKING SERVER", "Checking server for connectivity...", panel.COLOR_INPROGRESS);
        panel.matchButtonManager(QueueButtonStatus.OFFLINE);

        String token = config.authToken();
        String discord = config.discordUsername();
        if (discord == null || discord == "") {
            discord = "NULL";
        }
        clientConnection.requestServerStatus(login, discord, token).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
            // in the case of a server error - shut down plugin's systems.
            if (status == null || ex != null) {
                NeverScapeAlonePlugin.serverStatusState = status;
                panel.setServerPanel("SERVER ERROR", "There was a server error. Please contact support.", panel.SERVER_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                return;
            }
            processServerResponse(status, login, token);
        }));
    }

    private void startQueueCaseHandler() {

        if (username == "") {
            checkServerStatus(username);
            return;
        }

        panel.setServerPanel("SIGNING UP FOR QUEUE", "We're signing you up for queue! Please standby.", panel.COLOR_INPROGRESS);

        String login = username;
        String token = config.authToken();
        String discord = config.discordUsername();
        if (discord == null || discord.equals("")) {
            discord = "NULL";
        }

        JsonObject wrapper = new JsonObject();
        wrapper.add("Payload", panel.player_selections);

        clientConnection.startUserQueue(login, discord, token, wrapper).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
            {
                if (status == null || ex != null) {
                    serverStatusState = status;
                    panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                    panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                    return;
                }
                processServerResponse(status, login, token);
            }
        }));
    }

    private void cancelQueueCaseHandler() {
        queueTime = 0;
        // handles cancel queue and deny match requests.

        if (username == "") {
            checkServerStatus(username);
            return;
        }

        panel.setPartnerPanelVisible(false);
        panel.setMatchPanelVisible(false);
        panel.setActivityPanelVisible(true);
        panel.setButtonPanelVisible(true);
        panel.setServerPanel("CANCELING YOUR QUEUE", "We're canceling your queue.", panel.COLOR_INPROGRESS);

        String login = username;
        String token = config.authToken();
        String discord = config.discordUsername();
        if (discord == null || discord == "") {
            discord = "NULL";
        }

        clientConnection.cancelUserQueue(login, discord, token).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
            {
                if (status == null || ex != null) {
                    serverStatusState = status;
                    panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                    panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                    return;
                }
                processServerResponse(status, login, token);
            }
        }));
    }

    private void acceptMatchCaseHandler() {
        if (username == "") {
            checkServerStatus(username);
            return;
        }

        panel.setServerPanel("ACCEPTING MATCH", "We're accepting your match!", panel.COLOR_INPROGRESS);

        String login = username;
        String token = config.authToken();
        String discord = config.discordUsername();
        if (discord == null || discord == "") {
            discord = "NULL";
        }

        clientConnection.acceptMatch(login, discord, token).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
            {
                if (status == null || ex != null) {
                    serverStatusState = status;
                    panel.setServerPanel("SERVER MATCH ACCEPT FAILURE", "There was a server match accept failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                    panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                    return;
                }
                processServerResponse(status, login, token);
            }
        }));
    }

    private void endSessionCaseHandler() {
        queueTime = 0;
        if (username == "") {
            checkServerStatus(username);
            return;
        }
        panel.setPartnerPanelVisible(false);
        panel.setMatchPanelVisible(false);
        panel.setActivityPanelVisible(true);
        panel.setButtonPanelVisible(true);
        panel.setServerPanel("ENDING MATCH", "We're in the process of ending your match.", panel.COLOR_INPROGRESS);

        String login = username;
        String token = config.authToken();
        String discord = config.discordUsername();
        if (discord == null) {
            discord = "NULL";
        }

        clientConnection.endMatch(login, discord, token).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
            {
                if (status == null || ex != null) {
                    serverStatusState = status;
                    panel.setServerPanel("SERVER MATCH END FAILURE", "There was a server match end failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                    panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                    return;
                }
                processServerResponse(status, login, token);
            }
        }));
    }

    public void matchClickManager(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start Queue":
                startQueueCaseHandler();
                break;
            case "Cancel Queue":
            case "Deny Match":
                cancelQueueCaseHandler();
                break;
            case "Accept Match":
                acceptMatchCaseHandler();
                break;
            case "End Session":
                endSessionCaseHandler();
                break;
        }
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("NeverScapeAlone stopped!");
        clientToolbar.removeNavigation(navButton);
    }

    @Subscribe
    private void onPlayerSpawned(PlayerSpawned event) {
        if (username != "") {
            return;
        }
        getPlayerName(event.getPlayer());
    }

    private void getPlayerName(Player player) {
        if (player == null) {
            return;
        } // if there are no players in the field, return
        if (player == client.getLocalPlayer()) {
            username = player.getName();
        } //loads the player name into the plugin
        checkServerStatus(username); //check server with player name
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

    @Schedule(period = 1, unit = ChronoUnit.SECONDS, asynchronous = true)
    public void matchStatusScheduler() {
        if ((username == null) || (username == "")) {
            return;
        }
        switch (serverStatusState.getStatus()) {
            case QUEUE_CANCELED_FAILURE:
            case QUEUE_STARTED_FAILURE:
            case REGISTRATION_FAILURE:
            case REGISTERING:
            case MAINTENANCE:
            case RATE_LIMIT:
            case AUTH_FAILURE:
            case BAD_HEADER:
            case BAD_TOKEN:
            case ALIVE:
            case BAD_RSN:
            case BAD_DISCORD:
            case MATCH_ENDED:
            case REGISTERED:
            case QUEUE_CANCELED:
                queueTime = 0;
                break;
            case MATCH_ACCEPTED:
                matchInformationTicker += 1;
                if (matchInformationTicker % 5 == 0) {
                    String login = username;
                    String token = config.authToken();
                    String discord = config.discordUsername();
                    if (discord == null) {
                        discord = "NULL";
                    }

                    clientConnection.getMatchInformation(login, discord, token).whenCompleteAsync((matchInformationList, ex) -> SwingUtilities.invokeLater(() -> {
                        {
                            if (matchInformationList == null || ex != null) {
                                panel.setServerPanel("SERVER MATCH INFORMATION FAILURE", "There was a server match information failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                                return;
                            }

                            if (matchInformationList.get(0).party_identifier.equals("NO PARTY")) {
                                panel.setServerPanel("PARTY DISBANDED", "Your party has disbanded. Please exit queue and try again!", panel.COLOR_INFO);
                                panel.setPartnerPanelVisible(false);
                                panel.setMatchPanelVisible(false);
                                panel.setActivityPanelVisible(false);
                                panel.setButtonPanelVisible(false);
                                return;
                            }

                            panel.setServerPanel("MATCH STARTED", "We've paired you with some other players. Enjoy!", panel.COLOR_COMPLETED);
                            if (oldMatchInformation != matchInformationList) {
                                // reduce panel draws
                                panel.setPartnerPanel(matchInformationList);
                                oldMatchInformation = matchInformationList;
                                panel.setPartnerPanelVisible(true);
                                panel.setMatchPanelVisible(false);
                                panel.setActivityPanelVisible(false);
                                panel.setButtonPanelVisible(false);
                            }

                        }
                    }));

                    matchInformationTicker = 0;
                }
                break;
            // Rechecks status if failed
            case UNREACHABLE:
                if (offlineCheckSecondsCounter % 5 == 0) {
                    offlineCheckSecondsCounter = 0;
                    checkServerStatus(username);
                }
                break;
            // Only consider cases where the queue state should be checked, when you start queue, when you're currently in a pending match, and when you have no active matches.
            case QUEUE_STARTED:
            case PENDING_MATCHES:
            case NO_ACTIVE_MATCHES:
                panel.setPartnerPanelVisible(false);
                panel.setButtonPanelVisible(false);

                String string = "Queue Time: " + formatSeconds(queueTime);
                panel.setMatchPanelQueueTime(string);
                queueTime += 1;

                if (queueTime >= 3600) {
                    cancelQueueCaseHandler();
                    break;
                }

                if (queueTime % 5 == 0) {
                    String login = username;
                    String token = config.authToken();
                    String discord = config.discordUsername();
                    if (discord == null) {
                        discord = "NULL";
                    }
                    clientConnection.checkMatchStatus(login, discord, token).whenCompleteAsync((status, ex) -> SwingUtilities.invokeLater(() -> {
                        {
                            if (status == null || ex != null) {
                                serverStatusState = status;
                                panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
                                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                                return;
                            }
                            processServerResponse(status, login, token);
                        }
                    }));
                    break;
                }
        }
    }

    public void processServerResponse(ServerStatus status, String login, String token) {

        switch (status.getStatus()) {
            // if server is alive
            case ALIVE:
                serverStatusState = status;
                panel.setServerPanel("SERVER ONLINE", "Server is Online. Authentication was successful.", panel.COLOR_COMPLETED);
                panel.matchButtonManager(QueueButtonStatus.ONLINE);
                break;
            // if server is under maintenance shut down plugin panel
            case MAINTENANCE:
                serverStatusState = status;
                panel.setServerPanel("SERVER MAINTENANCE", "Server is undergoing Maintenance. Authentication was successful.", panel.COLOR_WARNING);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // if server is unreachable shut down plugin panel
            case UNREACHABLE:
                serverStatusState = status;
                panel.setServerPanel("SERVER UNREACHABLE", "Server is Unreachable. No connection could be made.", panel.COLOR_DISABLED);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // if there is an auth failure, shut down panel (proceed with authing the user)
            case AUTH_FAILURE:
                serverStatusState = status;
                panel.setServerPanel("AUTH FAILURE", "Authentication failed. Please set a new token in the Plugin config.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // badly formatted token
            case BAD_TOKEN:
                serverStatusState = status;
                panel.setServerPanel("BAD TOKEN", "The token (auth token) you have entered in the config is malformed.<br> Please delete this token entirely, and turn the plugin on and off.<br>If you need further assistance, please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // bad header
            case BAD_HEADER:
                serverStatusState = status;
                panel.setServerPanel("BAD HEADER", "The incoming header value is incorrect. Please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // bad rsn
            case BAD_RSN:
                serverStatusState = status;
                panel.setServerPanel("BAD RSN", "The incoming RSN does not match Jagex Standards. please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            case BAD_DISCORD:
                serverStatusState = status;
                panel.setServerPanel("BAD DISCORD", "The incoming Discord Username does not match known patterns, or ^@[A-Za-z]{1,32}#[0-9]{4}. please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // if queue was started
            case QUEUE_STARTED:
                serverStatusState = status;
                panel.setServerPanel("IN QUEUE", "You are currently in queue. Please standby for a partner!", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                panel.setMatchPanelVisible(true);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(false);
                panel.setButtonPanelVisible(false);
                break;
            // if queue was canceled
            case QUEUE_CANCELED:
                serverStatusState = status;
                panel.setServerPanel("QUEUE CANCELED", "Your queue has been canceled.", panel.COLOR_INFO);
                panel.matchButtonManager(QueueButtonStatus.START_QUEUE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(true);
                panel.setButtonPanelVisible(true);
                break;
            // if the active matches were scanned, and there are currently no active matches
            case NO_ACTIVE_MATCHES:
                serverStatusState = status;
                panel.setServerPanel("LOOKING FOR PARTNERS", "There are currently no active matches, please standby while we find you a match.", panel.COLOR_INFO);
                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                panel.setMatchPanelVisible(true);
                panel.setButtonPanelVisible(false);
                break;
            case PENDING_MATCHES:
                serverStatusState = status;
                panel.setServerPanel("MATCH FOUND", "We have found you a match!", panel.COLOR_INPROGRESS);
                panel.matchButtonManager(QueueButtonStatus.ACCEPT_OR_DECLINE);
                panel.setMatchPanelVisible(true);
                break;
            // if the match was accepted
            case MATCH_ACCEPTED:
                serverStatusState = status;
                panel.setServerPanel("MATCH ACCEPTED", "You have accepted the match. We are now waiting for other players to accept the match invite.", panel.COLOR_COMPLETED);
                panel.matchButtonManager(QueueButtonStatus.END_SESSION);
                panel.setMatchPanelVisible(true);
                panel.setButtonPanelVisible(false);
                break;
            // if the match was ended
            case MATCH_ENDED:
                serverStatusState = status;
                panel.setServerPanel("MATCH ENDED", "You have ended your match. You will be sent a form to complete on the players you have played with!", panel.COLOR_INFO);
                panel.matchButtonManager(QueueButtonStatus.START_QUEUE);
                panel.setMatchPanelVisible(false);
                panel.setPartnerPanelVisible(false);
                panel.setActivityPanelVisible(true);
                panel.setButtonPanelVisible(true);
                break;
            case RATE_LIMIT:
                serverStatusState = status;
                panel.setServerPanel("RATE LIMIT", "You've sent too many requests too quickly. Slow down!", panel.CLIENT_SIDE_ERROR);
                break;
            case REGISTERING:
                serverStatusState = status;
                panel.setServerPanel("REGISTERING ACCOUNT", "Your account is being registered for the plugin.<br>If this process does not complete quickly, please visit Plugin Support.", panel.COLOR_INFO);
                String discord = config.discordUsername();
                if (discord == null || discord == "") {
                    discord = "NULL";
                }
                clientConnection.registerUser(login, discord, token).whenCompleteAsync((status_2, ex_2) -> SwingUtilities.invokeLater(() -> {
                    {
                        if (status_2 == null || ex_2 != null) {
                            serverStatusState = status_2;
                            panel.setServerPanel("SERVER REGISTRATION ERROR", "There was a server registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
                            panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                            panel.setMatchPanelVisible(false);
                            panel.setButtonPanelVisible(false);
                            return;
                        }
                        switch (status_2.getStatus()) {
                            case REGISTRATION_FAILURE:
                                serverStatusState = status_2;
                                panel.setServerPanel("REGISTRATION ERROR", "There was a registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
                                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                                panel.setMatchPanelVisible(false);
                                panel.setButtonPanelVisible(false);
                                break;
                            case REGISTERED:
                                serverStatusState = status_2;
                                panel.setServerPanel("SUCCESSFULLY REGISTERED", "You were successfully registered for the plugin. Welcome to NeverScapeAlone!", panel.COLOR_COMPLETED);
                                panel.matchButtonManager(QueueButtonStatus.ONLINE);
                                break;
                        }
                    }
                }));
        }
    }

    @Provides
    NeverScapeAloneConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NeverScapeAloneConfig.class);
    }
}
