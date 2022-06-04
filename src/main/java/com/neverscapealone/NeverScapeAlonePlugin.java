package com.neverscapealone;

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

	// enums
	private worldTypeSelection wts;
	public QueueButtonStatus queueButtonStatus;

	public ServerStatus serverStatus;


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
			System.out.println(config.authToken());
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

	public Map<String, Boolean> generateUserConfigurations(){
		List<String> config_keys = configManager.getConfigurationKeys(NeverScapeAloneConfig.CONFIG_GROUP+".config_");
		HashMap<String, Boolean> user_configurations = new HashMap<>();
		for (String key : config_keys){
			String true_key = key.substring(NeverScapeAloneConfig.CONFIG_GROUP.length()+1);
			Boolean value = configManager.getConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, true_key, Boolean.class);
			user_configurations.put(true_key.substring("config_".length()).toUpperCase(Locale.ROOT), value);
		}
		return user_configurations;
	}

	private void startQueueCaseHandler() {
		Map<String, Boolean> user_configurations = generateUserConfigurations();

		if (username == "") {
			panel.checkServerStatus(username);
			return;
		}

		panel.setServerPanel("SIGNING UP FOR QUEUE", "We're signing you up for queue! Please standby.", panel.CHECKING_SERVER);
		panel.buttons_Deactivate(panel.activity_buttons);

		String login = username;
		String token = config.authToken();

		clientConnection.startUserQueue(login, token, user_configurations).whenCompleteAsync((status, ex) ->
				SwingUtilities.invokeLater(() ->
				{
					{
						if (status == null || ex != null) {
							panel.setServerPanel("SERVER QUEUE FAILURE", "There was a server queue failure error. Please contact support.", panel.SERVER_ERROR);
							panel.matchButtonManager(queueButtonStatus.CANCEL_QUEUE);
							return;
						}
						switch (status.getStatus()) {
							case QUEUE_STARTED:
								panel.setServerPanel("IN QUEUE", "You are currently in queue. Please standby for a partner!", panel.CHECKING_SERVER);
								panel.matchButtonManager(queueButtonStatus.CANCEL_QUEUE);
								break;
						}
					}
				}));
	}



	private void cancelQueueCaseHandler(){
		panel.checkServerStatus(username);
	}
	private void acceptQueueCaseHandler(){

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
			case "Accept Queue":
				acceptQueueCaseHandler();
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
	private void onGameStateChanged(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case LOGGED_IN:
				username = "";
				break;
			case LOGIN_SCREEN:
				panel.checkServerStatus("");
		}
	}

	@Subscribe
	private void onPlayerSpawned(PlayerSpawned event)
	{
		getPlayerName(event.getPlayer());
	}

	private void getPlayerName(Player player)
	{
		if (username != "") {return;} // pass if the name has been assigned, otherwise return
		if (player == null) {return;} // if there are no players in the field, return
		if (player == client.getLocalPlayer()) {username = player.getName();} //loads the player name into the plugin
		panel.checkServerStatus(username); //check server with player name
	}

	// update panel with necessary information, every 5 seconds.
	@Schedule(period = 5, unit = ChronoUnit.SECONDS, asynchronous = true)
	private void updatePanel(){
		if (username == "") {return;}
			panel.setWorld_types_label(stringifyWorldType());
			panel.setUsername_label(username);
		}

	private String stringifyWorldType(){
		world_types = "---";
		worldTypeSelection worldtype = config.worldTypeSelection();
		String type = worldtype.toString();
		world_types = type;
		return world_types;
	}

	@Provides
	NeverScapeAloneConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NeverScapeAloneConfig.class);
	}
}
