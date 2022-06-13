package com.neverscapealone;

import com.google.gson.JsonObject;
import com.google.inject.Provides;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.http.NeverScapeAloneClient;
import com.neverscapealone.http.ServerResponseParser;
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
import java.util.Base64;

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
	public NeverScapeAloneClient clientConnection;

	public static NeverScapeAlonePanel panel;
	private NavigationButton navButton;

	// basic user information for panel
	private String username = "";

	// tick count
	private int ticker = 0;

	// server state
	public static ServerStatus serverStatusState;
	private static final ServerResponseParser serverResponseParser = new ServerResponseParser();
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
				checkServerStatus(username);
			case LOGIN_SCREEN:
				checkServerStatus("");
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
		clientConnection.requestServerStatus(login, token).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
					// in the case of a server error - shut down plugin's systems.
					if (status == null || ex != null) {
						NeverScapeAlonePlugin.serverStatusState = status;
						panel.setServerPanel("SERVER ERROR", "There was a server error. Please contact support.", panel.SERVER_SIDE_ERROR);
						panel.matchButtonManager(QueueButtonStatus.OFFLINE);
						return;
					}
					serverResponseParser.parser(status, login, token);
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

		JsonObject wrapper = new JsonObject();
		wrapper.add("Payload", panel.player_selections);

		clientConnection.startUserQueue(login, token, wrapper).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
					{
						if (status == null || ex != null) {
							serverStatusState = status;
							panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
							panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
							return;
						}
						serverResponseParser.parser(status, login, token);
					}
				}));
	}

	private void cancelQueueCaseHandler(){

		if (username == "") {
			checkServerStatus(username);
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
							panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
							return;
						}
						serverResponseParser.parser(status, login, token);
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
		checkServerStatus(username); //check server with player name
	}

	@Schedule(period=5, unit=ChronoUnit.SECONDS, asynchronous=true)
	public void matchStatusScheduler()
	{
		if ((username == null) || (username == "")){
			return;
		}
		switch(serverStatusState.getStatus()){
			case QUEUE_CANCELED_FAILURE:
			case QUEUE_STARTED_FAILURE:
			case REGISTRATION_FAILURE:
			case UNREACHABLE:
			case REGISTERING:
			case MAINTENANCE:
			case AUTH_FAILURE:
			case BAD_HEADER:
			case BAD_TOKEN:
			case ALIVE:
			case BAD_RSN:
			case REGISTERED:
			case MATCH_ACCEPTED:
			case QUEUE_CANCELED:
				break;

			case QUEUE_STARTED:
			case NO_ACTIVE_MATCHES:
				String login = username;
				String token = config.authToken();
				clientConnection.checkMatchStatus(login, token).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
//					{
//						if (status == null || ex != null) {
//							serverStatusState = status;
//							panel.setServerPanel("SERVER MATCH CHECK FAILURE", "There was a server match check failure error. Please contact support.", panel.SERVER_SIDE_ERROR);
//							panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
//							return;
//						}
//						serverResponseParser.parser(status, login, token);
//					}
				}));
				break;
		}
	}

	@Provides
	NeverScapeAloneConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NeverScapeAloneConfig.class);
	}
}
