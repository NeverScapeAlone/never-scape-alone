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

import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.MatchData;
import com.neverscapealone.ui.Icons;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            NeverScapeAlonePlugin.matchData = new MatchData();
            return null;
        }
        if (Objects.equals(websocket.getGroupID(), "0")){
            return null;
        }
        if (NeverScapeAlonePlugin.matchData == null){
            return null;
        }
        if (NeverScapeAlonePlugin.matchData.getPlayers().size() == 0){
            return null;
        }

        HashMap<String, Boolean> playerPartyleader = new HashMap<>();
        for (com.neverscapealone.model.Player player : NeverScapeAlonePlugin.matchData.getPlayers()){
            playerPartyleader.put(player.getLogin(), player.getIsPartyLeader());
        }

        for (Player player : client.getPlayers()){
            if (playerPartyleader.containsKey(player.getName())) {

                BufferedImage playerIcon = iconToBuffered(Icons.NSA_ICON);
                if (playerPartyleader.get(player.getName())){
                    playerIcon = iconToBuffered(Icons.CROWN_ICON);
                }

                renderPlayerOverlay(graphics, player, config.minimapGroupMemberColor(), playerIcon);
            }

        }
        return null;
    }

    private BufferedImage iconToBuffered(ImageIcon icon){
        // resize
        Image image = icon.getImage();
        Image tempImage = image.getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon sizedImageIcon = new ImageIcon(tempImage);

        // write to buffered
        BufferedImage bi = new BufferedImage(
                sizedImageIcon.getIconWidth(),
                sizedImageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        sizedImageIcon.paintIcon(null, g, 0,0);
        g.dispose();
        return bi;
    }

    private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color, BufferedImage playerIcon)
    {
        final String name = actor.getName().replace('\u00A0', ' ');

        if (config.showOnMinimapBool())
        {
            net.runelite.api.Point minimapLocation = actor.getMinimapLocation();

            if (minimapLocation != null)
            {
                if (config.showPlayerNameMinimapBool()){
                    OverlayUtil.renderTextLocation(graphics, minimapLocation, name, color);
                }
                if (config.showPlayerIconMinimapBool()){
                    OverlayUtil.renderImageLocation(graphics, new net.runelite.api.Point(minimapLocation.getX()-(playerIcon.getWidth()/2), minimapLocation.getY()-playerIcon.getHeight()/2), playerIcon);
                }
            }
        }
    }


}