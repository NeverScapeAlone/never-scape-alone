package com.neverscapealone;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;
import com.neverscapealone.enums.experienceLevel;
import com.neverscapealone.enums.worldTypeSelection;

@ConfigGroup("Never Scape Alone")
public interface NeverScapeAloneConfig extends Config
{
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
			keyName = "authToken",
			name = "Authentication Token",
			description = "Set a custom Authentication token to confirm your identity.<br>Disable if you'd like us to create a token for you.",
			section = authSection
	)
	default String authToken()
	{
		return "generated_token_goes_here";
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
			keyName = "minUserRating",
			name = "Minimum Partner Rating",
			description = "[0-5 stars] The minimum partner rating that you will match with.",
			section = matchSection
	)
	@Range(min = 0, max = 5)
	default double minUserRating()
	{
		return 2.5;
	}
	@ConfigItem(
			position = 3,
			keyName = "minUserPoints",
			name = "Partner Match-ups",
			description = "[0-infinity] The minimum number of match-ups that your partner has had.",
			section = matchSection
	)
	@Range(min = 0)
	default int minUserPoints()
	{
		return 0;
	}
	@ConfigItem(
			position = 4,
			keyName = "userExperienceLevel",
			name = "Your Experience",
			description = "Your general, self-determined, RuneScape experience level.",
			section = matchSection
	)
	default experienceLevel userExperienceLevel()
	{
		return experienceLevel.LEARNER;
	}
	@ConfigItem(
			position = 5,
			keyName = "partnerExperienceLevel",
			name = "Partner Experience",
			description = "Your partner's minimum general, self-determined, RuneScape experience level.",
			section = matchSection
	)
	default experienceLevel partnerExperienceLevel()
	{
		return experienceLevel.LEARNER;
	}
	@ConfigItem(
			position = 6,
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
			position = 7,
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
			position = 8,
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
			position = 9,
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
			keyName = "showQueue",
			name = "Show Queue",
			description = "Show number of players in queue when finding a partner.",
			section = otherSection
	)
	default boolean showQueue()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
			keyName = "showPosition",
			name = "Show Position",
			description = "Show position in queue when finding a partner.",
			section = otherSection
	)
	default boolean showPosition()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "showElapsedTime",
			name = "Elapsed Time",
			description = "Show elapsed time in finding a partner.",
			section = otherSection
	)
	default boolean showElapsedTime()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "showEstimatedTime",
			name = "Estimated Time",
			description = "Show estimated time in finding a partner.",
			section = otherSection
	)
	default boolean showEstimatedTime()
	{
		return true;
	}
	@ConfigItem(
			position = 5,
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
			position = 6,
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

