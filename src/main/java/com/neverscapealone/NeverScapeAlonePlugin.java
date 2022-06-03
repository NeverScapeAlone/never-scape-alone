package com.neverscapealone;

import com.google.inject.Provides;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.enums.worldTypeSelection;
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
				// reset username for onPlayerSpawned method.
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

	// update panel with necessary information, every 5 minutes.
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
