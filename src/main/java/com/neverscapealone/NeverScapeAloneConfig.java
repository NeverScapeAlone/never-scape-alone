package com.neverscapealone;

import com.neverscapealone.enums.AccountTypeSelection;
import com.neverscapealone.enums.WorldTypeSelection;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(NeverScapeAloneConfig.CONFIG_GROUP)
public interface NeverScapeAloneConfig extends Config {
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

//	@ConfigSection(
//			position = 4,
//			name = "Other Settings",
//			description = "Select misc. settings for the plugin."
//	)
//	String otherSection = "otherSection";


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

    @ConfigItem(
			position = 1,
			keyName = "ignoreIgnores",
			name = "Ignore Ignores",
			description = "Ignore match-ups with players that are on your ignore list.",
			section = matchSection
	)
	default boolean ignoreIgnores()
	{
		return true;
	}
	@ConfigItem(
			position = 2,
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
			position = 3,
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
            position = 4,
            keyName = "WorldTypeSelection",
            name = "World Type",
            description = "Select if you would like to match on free-to-play, members or both",
            section = matchSection
    )
    default WorldTypeSelection worldTypeSelection() {
        return WorldTypeSelection.BOTH;
    }

    @ConfigItem(
			position = 5,
			keyName = "AccountTypeSelection",
			name = "Account Type",
			description = "Select the types of accounts you would like to match with.",
			section = matchSection
	)
	default AccountTypeSelection accountTypeSelection()
	{return AccountTypeSelection.ALL;}
    @ConfigItem(
            position = 1,
            keyName = "US",
            name = "United States",
            description = "Allow for United States matches.",
            section = regionSection
    )
    default boolean us() {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "euWest",
            name = "EU West",
            description = "Allow for West Europe matches.",
            section = regionSection
    )
    default boolean euWest() {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "euCentral",
            name = "EU Central",
            description = "Allow for Central Europe matches.",
            section = regionSection
    )
    default boolean euCentral() {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "oceania",
            name = "Oceania",
            description = "Allow for Oceania matches.",
            section = regionSection
    )
    default boolean oceania() {
        return true;
    }
}

