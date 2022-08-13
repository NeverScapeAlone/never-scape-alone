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