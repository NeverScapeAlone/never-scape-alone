package com.neverscapealone;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;
import com.neverscapealone.enums.worldTypeSelection;

@ConfigGroup(NeverScapeAloneConfig.CONFIG_GROUP)
public interface NeverScapeAloneConfig extends Config
{
	String CONFIG_GROUP = "NeverScapeAlone";
	String AUTH_TOKEN_KEY = "authToken";
	String CONFIG_TRUE = "countConfigTrue";
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

	@ConfigSection(
			position = 3,
			name = "Region Settings",
			description = "Select regions to search for partners here."
	)
	String regionSection = "regionSection";

	@ConfigSection(
			position = 4,
			name = "Other Settings",
			description = "Select misc. settings for the plugin."
	)
	String otherSection = "otherSection";


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
	default String discordUsername()
	{
		return "@UserName#1337";
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
	default String authToken()
	{
		return "";
	}
	@ConfigItem(
			position = 1,
			keyName = "searchTime",
			name = "Maximum Search Time",
			description = "The maximum partner search time before the request is aborted.",
			section = matchSection
	)
	@Range(min = 1, max = 360)
	@Units(Units.MINUTES)
	default int searchTime()
	{
		return 15;
	}
	@ConfigItem(
			position = 2,
			keyName = "ignoreIgnores",
			name = "Ignore Ignores",
			description = "Ignore matchups with playes that are on your ignore list.",
			section = matchSection
	)
	default boolean ignoreIgnores()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "prioritizeFriends",
			name = "Prioritize Friends",
			description = "Prioritize added friends when matching with other players.",
			section = matchSection
	)
	default boolean prioritizeFriends()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "verifiedUsers",
			name = "Verified Users",
			description = "Allow strict matching with Verified users of the plugin.",
			section = matchSection
	)
	default boolean verifiedPartners()
	{
		return false;
	}
	@ConfigItem(
			position = 5,
			keyName = "worldTypeSelection",
			name = "World Type",
			description = "Select if you would like to match on free-to-play, members or both",
			section = matchSection
	)
	default worldTypeSelection worldTypeSelection()
	{
		return worldTypeSelection.BOTH;
	}
	@ConfigItem(
			position = 1,
			keyName = "usEast",
			name = "US East",
			description = "Allow for US East matches.",
			section = regionSection
	)
	default boolean usEast()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
			keyName = "usWest",
			name = "US West",
			description = "Allow for US West matches.",
			section = regionSection
	)
	default boolean usWest()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "euWest",
			name = "EU West",
			description = "Allow for West Europe matches.",
			section = regionSection
	)
	default boolean euWest()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "euCentral",
			name = "EU Central",
			description = "Allow for Central Europe matches.",
			section = regionSection
	)
	default boolean euCentral()
	{
		return true;
	}
	@ConfigItem(
			position = 5,
			keyName = "oceania",
			name = "Oceania",
			description = "Allow for Oceania matches.",
			section = regionSection
	)
	default boolean oceania()
	{
		return true;
	}
	@ConfigItem(
			position = 1,
			keyName = "playFireworks",
			name = "Play Fireworks",
			description = "When a queue has been finished, play fireworks on the player.",
			section = otherSection
	)
	default boolean playFireworks()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
			keyName = "playSound",
			name = "Play Sound",
			description = "When a queue has been finished, play a sound byte.",
			section = otherSection
	)
	default boolean playSound()
	{
		return true;
	}
}

