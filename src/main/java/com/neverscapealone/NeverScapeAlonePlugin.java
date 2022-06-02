package com.neverscapealone;

import com.google.inject.Provides;
import javax.inject.Inject;

import com.neverscapealone.ui.NeverScapeAlonePanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Base64;

import net.runelite.client.util.ImageUtil;
import net.runelite.api.Client;
import com.neverscapealone.events.NeverScapeAlonePanelActivated;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.apache.commons.lang3.StringUtils;

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
		log.info("Never Scape Alone started!");

		// create and set a new auth token
		if(StringUtils.isBlank(config.authToken())){
			String USER_GENERATED_TOKEN = generateNewToken();
			configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.AUTH_TOKEN_KEY, USER_GENERATED_TOKEN);
		}

		panel = injector.getInstance(NeverScapeAlonePanel.class);
		final BufferedImage icon = ImageUtil.loadImageResource(NeverScapeAlonePlugin.class, "/tri-icon.png");
		navButton = NavigationButton.builder()
				.panel(panel)
				.tooltip("Never Scape Alone")
				.icon(icon)
				.priority(90)
				.build();
		clientToolbar.addNavigation(navButton);

	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Never Scape Alone stopped!");
		clientToolbar.removeNavigation(navButton);
	}

	@Provides
	NeverScapeAloneConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NeverScapeAloneConfig.class);
	}
}
