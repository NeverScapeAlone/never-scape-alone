package com.neverscapealone;

import com.neverscapealone.enums.SoundEffectSelection;
import net.runelite.client.config.*;

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
            name = "Interactive Settings",
            description = "Select the interactive settings for the plugin."
    )
    String interactiveSettings = "interactiveSettings";

    @ConfigSection(
            position = 2,
            name = "Sound Settings",
            description = "Plugin Sound Settings"
    )
    String soundSelection = "soundSelection";

    @ConfigItem(
            position = 1,
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
            keyName = "hotkey",
            name = "Ping Hotkey",
            description = "Configures the Ping selection Hotkey",
            section = interactiveSettings
    )
    default Keybind hotkey()
    {
        return Keybind.CTRL;
    }

    @ConfigItem(
            position = 1,
            keyName = "pingSoundEffect",
            name = "Ping",
            description = "The normal 'ping' sound effect.",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectPing()
    {
        return SoundEffectSelection.BELL_DING;
    }

    @ConfigItem(
            position = 2,
            keyName = "alertPingSoundEffect",
            name = "Ping Alert",
            description = "The alert 'ping' sound effect.",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectAlertPing()
    {
        return SoundEffectSelection.BELL_DONG;
    }

    @ConfigItem(
            position = 3,
            keyName = "matchJoinSound",
            name = "Match Join",
            description = "The sound when you join a match",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectMatchJoin()
    {
        return SoundEffectSelection.OPEN_DOOR;
    }

    @ConfigItem(
            position = 4,
            keyName = "matchCloseSound",
            name = "Match Leave",
            description = "The sound when you leave a match",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectMatchLeave()
    {
        return SoundEffectSelection.CLOSE_DOOR;
    }

    @ConfigItem(
            position = 5,
            keyName = "playerJoinSound",
            name = "Player Join",
            description = "The sound of a player joining the match",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectPlayerJoin()
    {
        return SoundEffectSelection.GE_INCREMENT;
    }

    @ConfigItem(
            position = 6,
            keyName = "playerLeaveSound",
            name = "Player Leave",
            description = "The sound of a player leaving the match",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectPlayerLeave()
    {
        return SoundEffectSelection.GE_DECREMENT;
    }

}

