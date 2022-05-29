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
		return experienceLevel.NOVICE;
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
		return experienceLevel.NOVICE;
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
			name = "Us East",
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
			name = "Us West",
			description = "Allow for US West matches.",
			section = regionSection
	)
	default boolean usWest()
	{
		return true;
	}
	@ConfigItem(
			position = 3,
			keyName = "uk",
			name = "United Kingdom",
			description = "Allow for United Kingdom matches.",
			section = regionSection
	)
	default boolean uk()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "ger",
			name = "Germany",
			description = "Allow for Germany matches.",
			section = regionSection
	)
	default boolean ger()
	{
		return true;
	}
	@ConfigItem(
			position = 5,
			keyName = "aus",
			name = "Australia",
			description = "Allow for Australia matches.",
			section = regionSection
	)
	default boolean aus()
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

