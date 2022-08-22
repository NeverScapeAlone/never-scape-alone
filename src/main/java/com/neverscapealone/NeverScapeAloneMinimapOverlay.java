/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.PingData;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class NeverScapeAloneMinimapOverlay extends Overlay
{
    private final Client client;
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneWebsocket websocket;

    @Inject
    private NeverScapeAloneMinimapOverlay(Client client, NeverScapeAlonePlugin plugin, NeverScapeAloneConfig config, NeverScapeAloneWebsocket websocket)
    {
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.websocket = websocket;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!NeverScapeAloneWebsocket.isSocketConnected){
            NeverScapeAlonePlugin.groupMemberNames = new ArrayList<>();
            // if the socket isn't connected, don't draw anything
            return null;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            // if we're only connected to main lobby, don't draw anything
            return null;
        }
        ArrayList<String> groupMemberNames = NeverScapeAlonePlugin.groupMemberNames;
        if (groupMemberNames == null || groupMemberNames.size() == 0){
            return null;
        }

        for (Player player : client.getPlayers()){
            if (groupMemberNames.contains(player.getName())) {
                renderPlayerOverlay(graphics, player, config.minimapGroupMemberColor());
            }
        }
        return null;
    }

    private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color)
    {
        final String name = actor.getName().replace('\u00A0', ' ');

        if (config.showOnMinimapBool())
        {
            final net.runelite.api.Point minimapLocation = actor.getMinimapLocation();

            if (minimapLocation != null)
            {
                OverlayUtil.renderTextLocation(graphics, minimapLocation, name, color);
            }
        }
    }
}