package com.neverscapealone;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(NeverScapeAloneConfig.CONFIG_GROUP)
public interface NeverScapeAloneConfig extends Config {
    String CONFIG_GROUP = "NeverScapeAlone";
    String AUTH_TOKEN_KEY = "authToken";
    // SECTIONS
    @ConfigSection(
            position = 1,
            name = "Authentication",
            description = "Settings for user authentication."
    )
    String authSection = "authSection";

    @ConfigSection(
            position = 2,
            name = "Match Settings",
            description = "Select match up settings here."
    )
    String matchSection = "matchSection";


	/*
	Configuration items for each section
	 */

    @ConfigItem(
            position = 1,
            keyName = "discordUsername",
            name = "Discord Username",
            description = "Set your discord username here, this will allow you to be verified through the system.<br>You don't need to verify your discord,<br> however by doing so you will prevent scammers from using your ID.",
            section = authSection
    )
    default String discordUsername() {
        return "@UserName#0000";
    }

    @ConfigItem(
            position = 2,
            keyName = AUTH_TOKEN_KEY,
            name = "Authentication Token",
            description = "Your authentication token for the plugin. Length 32 characters - automatically generated if cleared and the plugin is restarted.",
            warning = "There are rare circumstances where you will need to change this field. If you are unsure about what you are doing, please click 'No'.",
            secret = true,
            section = authSection
    )
    default String authToken() {
        return "";
    }
//	@ConfigItem(
//			position = 1,
//			keyName = "verifiedUsers",
//			name = "Verified Users",
//			description = "Allow strict matching with Verified users of the plugin.",
//			section = matchSection
//	)
//	default boolean verifiedPartners()
//	{
//		return false;
//	}

}

