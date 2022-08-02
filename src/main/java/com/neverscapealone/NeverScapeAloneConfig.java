/*
 * Copyright (c) 2022, Ferrariic <ferrariictweet@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neverscapealone;

import com.neverscapealone.enums.SoundEffectSelection;
import net.runelite.client.config.*;
import java.awt.Color;

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
            position = 3,
            name = "Sound Settings",
            description = "Plugin Sound Settings"
    )
    String soundSelection = "soundSelection";

    @ConfigSection(
            position = 4,
            name = "Color Settings",
            description = "Plugin Color Settings"
    )
    String colorSelection = "colorSelection";

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
            position = 2,
            keyName = "alertDelay",
            name = "Alert Delay",
            description = "How long you should have to hold down the hot-key before the Ping is converted to an Alert.",
            section = interactiveSettings
    )
    @Range(max = 2000)
    @Units(Units.MILLISECONDS)
    default int alertDelay()
    {
        return 200;
    }

    @ConfigItem(
            position = 3,
            keyName = "maxPingCount",
            name = "Max Ping Count",
            description = "Max number of active pings and alerts displayed on the screen.",
            section = interactiveSettings
    )
    @Range(max = 100)
    default int maxPingCount()
    {
        return 5;
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
            name = "Alert",
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
            name = "Team Join",
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
            name = "Team Leave",
            description = "The sound of a player leaving the match",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectPlayerLeave()
    {
        return SoundEffectSelection.GE_DECREMENT;
    }

    @ConfigItem(
            position = 7,
            keyName = "errorSound",
            name = "Error",
            description = "The sound when there's an error",
            section = soundSelection
    )
    default SoundEffectSelection soundEffectError()
    {
        return SoundEffectSelection.UI_BOOP;
    }

    @ConfigItem(
            position = 8,
            keyName = "pingBool",
            name = "Ping Sound",
            description = "Enable or disable the Ping sound effect",
            section = soundSelection
    )
    default boolean soundEffectPingBool()
    {
        return true;
    }

    @ConfigItem(
            position = 9,
            keyName = "alertBool",
            name = "Alert Sound",
            description = "Enable or disable the Alert sound effect",
            section = soundSelection
    )
    default boolean soundEffectAlertBool()
    {
        return true;
    }

    @ConfigItem(
            position = 10,
            keyName = "matchJoinBool",
            name = "Match Join Sound",
            description = "Enable or disable the match join sound effect",
            section = soundSelection
    )
    default boolean soundEffectMatchJoinBool()
    {
        return true;
    }

    @ConfigItem(
            position = 11,
            keyName = "matchLeaveBool",
            name = "Match Leave Sound",
            description = "Enable or disable the match leave sound effect",
            section = soundSelection
    )
    default boolean soundEffectMatchLeaveBool()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "TeamJoinBool",
            name = "Team Join Sound",
            description = "Enable or disable the Teammate join sound effect",
            section = soundSelection
    )
    default boolean soundEffectTeamJoinBool()
    {
        return true;
    }

    @ConfigItem(
            position = 13,
            keyName = "TeamLeaveBool",
            name = "Team Leave Sound",
            description = "Enable or disable the Teammate leave sound effect",
            section = soundSelection
    )
    default boolean soundEffectTeamLeaveBool()
    {
        return true;
    }

    @ConfigItem(
            position = 14,
            keyName = "ErrorBool",
            name = "Error Sound",
            description = "Enable or disable the Error sound effect",
            section = soundSelection
    )
    default boolean soundEffectErrorBool()
    {
        return true;
    }

    @ConfigItem(
            position = 15,
            keyName = "ButtonBool",
            name = "Button Sound",
            description = "Enable or disable the Button sound effect",
            section = soundSelection
    )
    default boolean soundEffectButtonBool()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "pingColor",
            name = "Ping Color",
            description = "This is the color that other players see when you ping a tile.",
            section = colorSelection
    )
    default Color pingColor(){return new Color(247, 241, 49);};
}

