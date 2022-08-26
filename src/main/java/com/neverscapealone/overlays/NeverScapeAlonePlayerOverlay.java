/*
 * Copyright (c) 2018, Morgan Lewis <https://github.com/MESLewis>
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

package com.neverscapealone.overlays;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.MatchData;
import com.neverscapealone.ui.Icons;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

public class NeverScapeAlonePlayerOverlay extends Overlay
{
    private final Client client;
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneWebsocket websocket;

    @Inject
    private NeverScapeAlonePlayerOverlay(Client client, NeverScapeAlonePlugin plugin, NeverScapeAloneConfig config, NeverScapeAloneWebsocket websocket)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.websocket = websocket;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.showPlayerOverlayBool()){
            return null;
        }
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

                BufferedImage playerIcon = iconToBuffered(Icons.NSA_ICON, 16, 16);
                if (playerPartyleader.get(player.getName())){
                    playerIcon = iconToBuffered(Icons.CROWN_ICON, 16, 16);
                }

                renderPlayerName(graphics, player, playerIcon);
            }
        }
        return null;
    }

    private void renderPlayerName(final Graphics2D graphics, Player player, BufferedImage bufferedImage) {
        int height = player.getLogicalHeight();
        if (config.showPlayerIconBool()){
            OverlayUtil.renderActorOverlayImage(graphics, player, bufferedImage, null, height/2);
        }
        if (config.showPlayerNameBool()){
            OverlayUtil.renderActorOverlay(graphics, player, player.getName(), config.overlayColor());
        }
    }

    private BufferedImage iconToBuffered(ImageIcon icon, Integer width, Integer height){
        // resize
        Image image = icon.getImage();
        Image tempImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
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
}