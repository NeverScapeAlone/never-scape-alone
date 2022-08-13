package com.neverscapealone.enums;

import com.neverscapealone.ui.Icons;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;

@Getter
@AllArgsConstructor
public enum WebLink {
    DISCORD(Icons.DISCORD_ICON, "Join our Discord", "https://discord.gg/rs2AH3vnmf"),
    TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter", "https://www.twitter.com/NeverScapeAlone"),
    GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
    PATREON(Icons.PATREON_ICON, "Support us through Patreon", "https://www.patreon.com/bot_detector"),
    PAYPAL(Icons.PAYPAL_ICON, "Support us through PayPal", "https://www.paypal.com/paypalme/osrsbotdetector"),
    BUG_REPORT_ICON(Icons.BUG_REPORT, "Submit a bug report here", "https://github.com/NeverScapeAlone/never-scape-alone/issues");

    private final ImageIcon image;
    private final String tooltip;
    private final String link;
}