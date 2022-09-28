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

import com.neverscapealone.enums.MapStatsOptionsEnum;
import com.neverscapealone.enums.SoundEffectSelectionEnum;
import net.runelite.api.ChatMessageType;
import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(NeverScapeAloneConfig.CONFIG_GROUP)
public interface NeverScapeAloneConfig extends Config {
    String CONFIG_GROUP = "NeverScapeAlone";
    String AUTH_TOKEN_KEY = "authToken";

    @ConfigSection(
            position = 1,
            name = "Interactive Settings",
            description = "Select the interactive settings for the plugin."
    )
    String interactiveSection = "interactiveSettings";

    @ConfigSection(
            position = 2,
            name = "Minimap Settings",
            description = "Minimap settings for the plugin"
    )
    String minimapSection = "minimapSection";

    @ConfigSection(
            position = 3,
            name = "Map Settings",
            description = "Map settings for the plugin"
    )
    String mapSection = "mapSection";

    @ConfigSection(
            position = 4,
            name = "Player Overlay Settings",
            description = "Player overlay settings"
    )
    String playerSection = "playerSection";

    @ConfigSection(
            position = 5,
            name = "Color Settings",
            description = "General color settings"
    )
    String colorSection = "colorSection";

    @ConfigSection(
            position = 6,
            name = "Sound Settings",
            description = "Plugin Sound Settings"
    )
    String soundSection = "soundSection";


    @ConfigSection(
            position = 7,
            name = "Chat Settings",
            description = "Plugin Chat Settings"
    )
    String chatSection = "chatSection";

    @ConfigSection(
            position = 8,
            name = "Connectivity Settings",
            description = "Plugin Connectivity Settings"
    )
    String connectivitySection = "connectivitySection";

    @ConfigItem(
            position = 1,
            keyName = AUTH_TOKEN_KEY,
            name = "Authentication Token",
            description = "Your authentication token for the plugin. Length 32 characters - automatically generated if cleared and the plugin is restarted.",
            warning = "There are rare circumstances where you will need to change this field. If you are unsure about what you are doing, please click 'No'.",
            secret = true,
            hidden = true
    )
    default String authToken() {
        return "";
    }

    @ConfigItem(
            position = 1,
            keyName = "hotkey",
            name = "Ping Hotkey",
            description = "Configures the Ping selection Hotkey",
            section = interactiveSection
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
            section = interactiveSection
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
            section = interactiveSection
    )
    @Range(max = 100)
    default int maxPingCount()
    {
        return 5;
    }
    @ConfigItem(
            position = 4,
            keyName = "pingDecay",
            name = "Ping Decay",
            description = "Maximum number of seconds that a ping can live for",
            section = interactiveSection
    )
    @Range(max = 3600)
    @Units(Units.SECONDS)
    default int pingDecay()
    {
        return 20;
    }

    @ConfigItem(
            position = 5,
            keyName = "pingColor",
            name = "Ping Color",
            description = "This is the color that other players see when you ping a tile.",
            section = interactiveSection
    )
    default Color pingColor(){return new Color(247, 241, 49);};


    @ConfigItem(
            position = 1,
            keyName = "showOnMinimap",
            name = "Minimap Overlay",
            description = "Shows Icons on Minimap Overlay",
            section = minimapSection
    )
    default boolean showOnMinimapBool()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "showPlayerNameMinimap",
            name = "Player Name",
            description = "Shows Player Name on Minimap Overlay",
            section = minimapSection
    )
    default boolean showPlayerNameMinimapBool()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "showPlayerIconMinimap",
            name = "Player Icon",
            description = "Show Player Icon on Minimap",
            section = minimapSection
    )
    default boolean showPlayerIconMinimapBool()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "showPlayerNameMap",
            name = "Player Name",
            description = "Show Player Name on Map",
            section = mapSection
    )
    default boolean showPlayerNameMapBool()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "showPlayerIconMap",
            name = "Player Icon",
            description = "Show Player Icon on Map",
            section = mapSection
    )
    default boolean showPlayerIconMapBool()
    {
        return true;
    }


    @ConfigItem(
            position = 3,
            keyName = "playerStatsMapOptions",
            name = "Map Stats Option",
            description = "What should the visuals of the map stats be?",
            section = mapSection
    )
    default MapStatsOptionsEnum showPlayerStatsMapBool()
    {
        return MapStatsOptionsEnum.Icons;
    }

    @ConfigItem(
            position = 1,
            keyName = "showPlayerOverlay",
            name = "Player Overlay",
            description = "Show Player Overlay",
            section = playerSection
    )
    default boolean showPlayerOverlayBool()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "showPlayerName",
            name = "Player Name",
            description = "Show Player Name",
            section = playerSection
    )
    default boolean showPlayerNameBool()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "showPlayerIcon",
            name = "Player Icon",
            description = "Show Player Icon",
            section = playerSection
    )
    default boolean showPlayerIconBool()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "minimapColor",
            name = "Member Minimap Color",
            description = "Teammate color on the minimap",
            section = colorSection
    )
    default Color minimapColor(){return new Color(0, 255, 174);};

    @ConfigItem(
            position = 2,
            keyName = "mapColor",
            name = "Member Map Color",
            description = "Teammate color on the map",
            section = colorSection
    )
    default Color mapColor(){return new Color(0, 255, 174);}

    @ConfigItem(
            position = 3,
            keyName = "overlayColor",
            name = "Member Color",
            description = "Teammate overlay color",
            section = colorSection
    )
    default Color overlayColor(){return new Color(0, 255, 174);}

    @ConfigItem(
            position = 1,
            keyName = "pingSoundEffect",
            name = "Ping",
            description = "The normal 'ping' sound effect.",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectPing()
    {
        return SoundEffectSelectionEnum.TINDER_STRIKE;
    }

    @ConfigItem(
            position = 2,
            keyName = "alertPingSoundEffect",
            name = "Alert",
            description = "The alert 'ping' sound effect.",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectAlertPing()
    {
        return SoundEffectSelectionEnum.MINING_TINK;
    }

    @ConfigItem(
            position = 3,
            keyName = "matchJoinSound",
            name = "Match Join",
            description = "The sound when you join a match",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectMatchJoin()
    {
        return SoundEffectSelectionEnum.OPEN_DOOR;
    }

    @ConfigItem(
            position = 4,
            keyName = "matchCloseSound",
            name = "Match Leave",
            description = "The sound when you leave a match",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectMatchLeave()
    {
        return SoundEffectSelectionEnum.CLOSE_DOOR;
    }

    @ConfigItem(
            position = 5,
            keyName = "playerJoinSound",
            name = "Team Join",
            description = "The sound of a player joining the match",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectPlayerJoin()
    {
        return SoundEffectSelectionEnum.GE_INCREMENT;
    }

    @ConfigItem(
            position = 6,
            keyName = "playerLeaveSound",
            name = "Team Leave",
            description = "The sound of a player leaving the match",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectPlayerLeave()
    {
        return SoundEffectSelectionEnum.GE_DECREMENT;
    }

    @ConfigItem(
            position = 7,
            keyName = "errorSound",
            name = "Error",
            description = "The sound when there's an error",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectError()
    {
        return SoundEffectSelectionEnum.UI_BOOP;
    }

    @ConfigItem(
            position = 8,
            keyName = "chatSound",
            name = "Chat",
            description = "The sound when there's a new Chat message",
            section = soundSection
    )
    default SoundEffectSelectionEnum soundEffectChat()
    {
        return SoundEffectSelectionEnum.PICK_PLANT;
    }

    @ConfigItem(
            position = 9,
            keyName = "pingBool",
            name = "Ping Sound",
            description = "Enable or disable the Ping sound effect",
            section = soundSection
    )
    default boolean soundEffectPingBool()
    {
        return true;
    }

    @ConfigItem(
            position = 10,
            keyName = "alertBool",
            name = "Alert Sound",
            description = "Enable or disable the Alert sound effect",
            section = soundSection
    )
    default boolean soundEffectAlertBool()
    {
        return true;
    }

    @ConfigItem(
            position = 11,
            keyName = "matchJoinBool",
            name = "Match Join Sound",
            description = "Enable or disable the match join sound effect",
            section = soundSection
    )
    default boolean soundEffectMatchJoinBool()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "matchLeaveBool",
            name = "Match Leave Sound",
            description = "Enable or disable the match leave sound effect",
            section = soundSection
    )
    default boolean soundEffectMatchLeaveBool()
    {
        return true;
    }

    @ConfigItem(
            position = 13,
            keyName = "TeamJoinBool",
            name = "Team Join Sound",
            description = "Enable or disable the Teammate join sound effect",
            section = soundSection
    )
    default boolean soundEffectTeamJoinBool()
    {
        return true;
    }

    @ConfigItem(
            position = 14,
            keyName = "TeamLeaveBool",
            name = "Team Leave Sound",
            description = "Enable or disable the Teammate leave sound effect",
            section = soundSection
    )
    default boolean soundEffectTeamLeaveBool()
    {
        return true;
    }

    @ConfigItem(
            position = 15,
            keyName = "chatBool",
            name = "Chat Sound",
            description = "Enable or disable the Chat sound effect",
            section = soundSection
    )
    default boolean soundEffectChatBool()
    {
        return true;
    }

    @ConfigItem(
            position = 16,
            keyName = "ErrorBool",
            name = "Error Sound",
            description = "Enable or disable the Error sound effect",
            section = soundSection
    )
    default boolean soundEffectErrorBool()
    {
        return true;
    }

    @ConfigItem(
            position = 17,
            keyName = "ButtonBool",
            name = "Button Sound",
            description = "Enable or disable the Button sound effect",
            section = soundSection
    )
    default boolean soundEffectButtonBool()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "chatShowBool",
            name = "Show Chat In-game",
            description = "Show chat in-game, as well as in the plugin 'chat' panel.",
            section = chatSection
    )
    default boolean chatShowBoolean()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "chatType",
            name = "Chat",
            description = "Selects where chat should show up in-game.",
            section = chatSection
    )
    default ChatMessageType chatMessageType()
    {
        return ChatMessageType.CONSOLE;
    }


    @ConfigItem(
            position = 1,
            keyName = "numberOfRetries",
            name = "Max Retries",
            description = "Max number of retries when reconnecting to the server.",
            section = connectivitySection
    )
    @Range(max = 120)
    default int numberOfRetries()
    {
        return 60;
    }

    @ConfigItem(
            position = 2,
            keyName = "sendDiscordRuneguard",
            name = "Send Discord",
            description = "Send your discord to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendDiscordRuneguard()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "sendLocationRuneguard",
            name = "Send Location",
            description = "Send your location to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendLocationRuneguard()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "sendStatusRuneguard",
            name = "Send Status",
            description = "Send your status to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendStatusRuneguard()
    {
        return true;
    }

    @ConfigItem(
            position = 5,
            keyName = "sendStatsRuneguard",
            name = "Send Stats",
            description = "Send your stats to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendStatsRuneguard()
    {
        return true;
    }

    @ConfigItem(
            position = 6,
            keyName = "sendInventoryRuneguard",
            name = "Send Inventory",
            description = "Send your inventory to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendInventoryRuneguard()
    {
        return true;
    }
    @ConfigItem(
            position = 7,
            keyName = "sendEquipmentRuneguard",
            name = "Send Equipment",
            description = "Send your equipment to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendEquipmentRuneguard()
    {
        return true;
    }

    @ConfigItem(
            position = 8,
            keyName = "sendPrayerRuneguard",
            name = "Send Prayer",
            description = "Send your prayer to non-RuneGuard parties",
            section = connectivitySection
    )
    default boolean sendPrayerRuneguard()
    {
        return true;
    }

}

