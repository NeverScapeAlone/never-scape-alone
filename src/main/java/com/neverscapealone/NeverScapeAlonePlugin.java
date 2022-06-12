package com.neverscapealone;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.enums.worldTypeSelection;
import com.neverscapealone.http.NeverScapeAloneClient;
import com.neverscapealone.model.ServerStatus;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.GameStateChanged;
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
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@PluginDescriptor(
	name = "NeverScapeAlone",
	description="This plugin lets you partner up with other players to complete bosses, minigames, skills, and other miscellaneous activities.",
	tags={"Matchmaking", "Skill", "PVP", "Boss", "Minigame"},
	enabledByDefault = true
)
public class NeverScapeAlonePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private NeverScapeAloneConfig config;

	@Inject
	private NeverScapeAloneClient clientConnection;

	private NeverScapeAlonePanel panel;
	private NavigationButton navButton;

	// basic user information for panel
	private String username = "";
	private String queue_progress = "---";
	private String queue_time = "---";
	private String world_types = "---";
	private String regions = "---";
	private String partner_usernames = "---";
	private String activity = "---";
	private String world_number = "---";
	private String location = "---";

	// tick count
	private int ticker = 0;

	// server state
	public ServerStatus serverStatusState;

	public QueueButtonStatus queueButtonStatus;


	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	public static String generateNewToken() {
		byte[] randomBytes = new byte[24];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}

	@Override
	protected void startUp() throws Exception
	{

		log.info("NeverScapeAlone started!");

		// create and set a new auth token
		if(StringUtils.isBlank(config.authToken())){
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

		switch (client.getGameState()){
			case LOGGED_IN:
				panel.checkServerStatus(username);
			case LOGIN_SCREEN:
				panel.checkServerStatus("");
		}
	}

	public void parser(ServerStatus status, String login, String token) {
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
				break;
			// if server is unreachable shut down plugin panel
			case UNREACHABLE:
				serverStatusState = status;
				panel.setServerPanel("SERVER UNREACHABLE", "Server is Unreachable. No connection could be made.", panel.COLOR_DISABLED);
				panel.matchButtonManager(QueueButtonStatus.OFFLINE);
				break;
			// if there is an auth failure, shut down panel (proceed with authing the user)
			case AUTH_FAILURE:
				serverStatusState = status;
				panel.setServerPanel("AUTH FAILURE", "Authentication failed. Please set a new token in the Plugin config.", panel.CLIENT_SIDE_ERROR);
				panel.matchButtonManager(QueueButtonStatus.OFFLINE);
				break;
			// badly formatted token
			case BAD_TOKEN:
				serverStatusState = status;
				panel.setServerPanel("BAD TOKEN", "The token (auth token) you have entered in the config is malformed.<br> Please delete this token entirely, and turn the plugin on and off.<br>If you need further assistance, please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
				panel.matchButtonManager(QueueButtonStatus.OFFLINE);
				break;
			// bad header
			case BAD_HEADER:
				serverStatusState = status;
				panel.setServerPanel("BAD HEADER", "The incoming header value is incorrect. Please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
				panel.matchButtonManager(QueueButtonStatus.OFFLINE);
				break;
			// bad rsn
			case BAD_RSN:
				serverStatusState = status;
				panel.setServerPanel("BAD RSN", "The incoming RSN does not match Jagex Standards. please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
				panel.matchButtonManager(QueueButtonStatus.OFFLINE);
				break;
			// if queue was started
			case QUEUE_STARTED:
				serverStatusState = status;
				panel.setServerPanel("IN QUEUE", "You are currently in queue. Please standby for a partner!", panel.CLIENT_SIDE_ERROR);
				panel.matchButtonManager(queueButtonStatus.CANCEL_QUEUE);
				break;
			// if queue was canceled
			case QUEUE_CANCELED:
				serverStatusState = status;
				panel.setServerPanel("QUEUE CANCELED","Your queue has been canceled.",panel.COLOR_INFO);
				panel.matchButtonManager(queueButtonStatus.START_QUEUE);
				break;
			// if the active matches were scanned, and there are currently no active matches
			case NO_ACTIVE_MATCHES:
				serverStatusState = status;
				panel.setServerPanel("Looking for Partners","There are currently no active matches, please standby while we find you a match.", panel.COLOR_INFO);
				panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
			// if the match was accepted
			case MATCH_ACCEPTED:
				serverStatusState = status;
				panel.setServerPanel("Match Accepted","You have accepted the match. We are now sending your match information to you.", panel.COLOR_COMPLETED);
				panel.matchButtonManager(QueueButtonStatus.END_SESSION);
			case REGISTERING:
				serverStatusState = status;
				panel.setServerPanel("REGISTERING ACCOUNT", "Your account is being registered for the plugin.<br>If this process does not complete quickly, please visit Plugin Support.", panel.COLOR_INFO);
				clientConnection.registerUser(login, token).whenCompleteAsync((status_2, ex_2) ->
						SwingUtilities.invokeLater(() ->
						{
							{
								if (status_2 == null || ex_2 != null) {
									serverStatusState = status_2;
									panel.setServerPanel("SERVER REGISTRATION ERROR", "There was a server registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
									panel.matchButtonManager(QueueButtonStatus.OFFLINE);
									return;
								}
								switch (status_2.getStatus()) {
									case REGISTRATION_FAILURE:
										serverStatusState = status_2;
										panel.setServerPanel("REGISTRATION ERROR", "There was a registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
										panel.matchButtonManager(QueueButtonStatus.OFFLINE);
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

	private void startQueueCaseHandler() {

		if (username == "") {
			panel.checkServerStatus(username);
			return;
		}

		panel.setServerPanel("SIGNING UP FOR QUEUE", "We're signing you up for queue! Please standby.", panel.COLOR_INPROGRESS);

		String login = username;
		String token = config.authToken();

		JsonObject wrapper = new JsonObject();
		wrapper.add("Payload", panel.player_selections);

		clientConnection.startUserQueue(login, token, wrapper).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
					{
						if (status == null || ex != null) {
							serverStatusState = status;
							panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
							panel.matchButtonManager(queueButtonStatus.CANCEL_QUEUE);
							return;
						}
						parser(status, login, token);
					}
				}));
	}

	private void cancelQueueCaseHandler(){

		if (username == "") {
			panel.checkServerStatus(username);
			return;
		}

		panel.setServerPanel("CANCELING YOUR QUEUE", "We're canceling your queue.", panel.COLOR_INPROGRESS);

		String login = username;
		String token = config.authToken();

		clientConnection.cancelUserQueue(login, token).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
					{
						if (status == null || ex != null) {
							serverStatusState = status;
							panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
							panel.matchButtonManager(queueButtonStatus.CANCEL_QUEUE);
							return;
						}
						parser(status, login, token);
					}
				}));
	}
	private void acceptMatchCaseHandler(){

	}

	private void denyMatchCaseHandler(){

	}

	private void endSessionCaseHandler(){

	}

	public void matchClickManager(ActionEvent e){
		switch (e.getActionCommand()){
			case "Start Queue":
				startQueueCaseHandler();
				break;
			case "Cancel Queue":
				cancelQueueCaseHandler();
				break;
			case "Accept Match":
				acceptMatchCaseHandler();
				break;
			case "Deny Match":
				denyMatchCaseHandler();
				break;
			case "End Session":
				endSessionCaseHandler();
				break;
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("NeverScapeAlone stopped!");
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	private void onPlayerSpawned(PlayerSpawned event)
	{
		if (username != "") {return;}
		getPlayerName(event.getPlayer());
	}

	private void getPlayerName(Player player)
	{
		if (player == null) {return;} // if there are no players in the field, return
		if (player == client.getLocalPlayer()) {username = player.getName();} //loads the player name into the plugin
		panel.checkServerStatus(username); //check server with player name
	}

	@Provides
	NeverScapeAloneConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NeverScapeAloneConfig.class);
	}
}
