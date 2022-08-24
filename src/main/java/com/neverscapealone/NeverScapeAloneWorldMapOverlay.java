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
package com.neverscapealone;

import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.MatchData;
import com.neverscapealone.ui.Icons;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.worldmap.WorldMapOverlay;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class NeverScapeAloneWorldMapOverlay extends Overlay
{
    private final Client client;
    private final WorldMapOverlay worldMapOverlay;
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneWebsocket websocket;

    @Inject
    private NeverScapeAloneWorldMapOverlay(Client client, WorldMapOverlay worldMapOverlay, NeverScapeAlonePlugin plugin, NeverScapeAloneConfig config, NeverScapeAloneWebsocket websocket)
    {
        this.client = client;
        this.worldMapOverlay = worldMapOverlay;
        this.plugin = plugin;
        this.config = config;
        this.websocket = websocket;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGHEST);
        setLayer(OverlayLayer.MANUAL);
        drawAfterInterface(WidgetID.WORLD_MAP_GROUP_ID);
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

        RenderOverview ro = client.getRenderOverview();
        Widget worldMapWidget = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

        if (ro == null || worldMapWidget == null)
        {
            return null;
        }

        Rectangle worldMapRectangle = worldMapWidget.getBounds();

        graphics.setClip(worldMapRectangle);
        graphics.setColor(Color.CYAN);

        renderPlayerIcon(graphics, NeverScapeAlonePlugin.matchData);
        return null;
    }

    private void renderPlayerIcon(final Graphics2D graphics, MatchData matchData) {
        for (com.neverscapealone.model.Player player : matchData.getPlayers()){
            WorldPoint playerLocation = new WorldPoint(player.getLocation().getX(), player.getLocation().getY(), 0);
            Point playerPoint = worldMapOverlay.mapWorldPointToGraphicsPoint(playerLocation);

            graphics.drawString(player.getLogin(),playerPoint.getX(), playerPoint.getY());
            graphics.drawImage(iconToBuffered(Icons.CROWN_ICON),null, playerPoint.getX(), playerPoint.getY());

        }
    }

    private BufferedImage iconToBuffered(ImageIcon icon){
        // resize
        Image image = icon.getImage();
        Image tempImage = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
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