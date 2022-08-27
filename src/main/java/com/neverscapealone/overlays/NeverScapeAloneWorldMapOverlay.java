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

        renderPlayerIcon(graphics, NeverScapeAlonePlugin.matchData);
        return null;
    }

    private void renderPlayerIcon(final Graphics2D graphics, MatchData matchData) {
        for (com.neverscapealone.model.Player player : matchData.getPlayers()){
            if (player.getLocation() == null){
                continue;
            }
            WorldPoint playerLocation = new WorldPoint(player.getLocation().getX(), player.getLocation().getY(), 0);
            Point playerPoint = worldMapOverlay.mapWorldPointToGraphicsPoint(playerLocation);

            if (config.showPlayerNameMapBool()){
                if (player.getLogin() == null){
                    continue;
                }
                if (playerPoint == null){
                    continue;
                }

                FontMetrics fm = graphics.getFontMetrics();
                int nameCenterX = playerPoint.getX() - (fm.stringWidth(player.getLogin())/2);
                int nameHeightY = playerPoint.getY() - fm.getAscent()/2;
                graphics.setColor(config.mapColor());
                graphics.drawString(player.getLogin(), nameCenterX, nameHeightY);
            }
            if (config.showPlayerIconMapBool()){
                BufferedImage bi = iconToBuffered(Icons.NSA_ICON, 16, 16);
                if (player.getIsPartyLeader()){
                    bi = iconToBuffered(Icons.CROWN_ICON, 16, 16);
                }
                graphics.drawImage(bi, null, playerPoint.getX()-(bi.getWidth()/2), playerPoint.getY()-(bi.getHeight()/2));
            }

            if (player.getStatus() == null){
                continue;
            }
            switch (config.showPlayerStatsMapBool()){
                case Icons:
                    BufferedImage hitpointsBi = iconToBuffered(Icons.HITPOINTS, 16, 16);
                    BufferedImage prayerBi = iconToBuffered(Icons.PRAYER, 16, 16);
                    BufferedImage runBi = iconToBuffered(Icons.AGILITY, 16, 16);

                    // draw hitpoints
                    int hitpointsCenterX = playerPoint.getX()-(hitpointsBi.getWidth()*2);
                    int hitpointsCenterY = playerPoint.getY()+(hitpointsBi.getHeight()/2);
                    graphics.drawImage(hitpointsBi, null, hitpointsCenterX, hitpointsCenterY);
                    graphics.setColor(Color.BLACK);
                    drawTextShadow(graphics, hitpointsCenterX, hitpointsCenterY, String.valueOf(player.getStatus().getHp()));
                    graphics.setColor(Color.YELLOW);
                    centerStringOnImage(graphics, hitpointsCenterX, hitpointsCenterY, String.valueOf(player.getStatus().getHp()));

                    // draw prayer
                    int prayerCenterX = playerPoint.getX()-(prayerBi.getWidth()/2);
                    int prayerCenterY = playerPoint.getY()+(prayerBi.getHeight()/2);
                    graphics.drawImage(prayerBi, null, prayerCenterX, prayerCenterY);
                    graphics.setColor(Color.BLACK);
                    drawTextShadow(graphics, prayerCenterX, prayerCenterY, String.valueOf(player.getStatus().getPrayer()));
                    graphics.setColor(Color.YELLOW);
                    centerStringOnImage(graphics, prayerCenterX, prayerCenterY, String.valueOf(player.getStatus().getPrayer()));

                    // draw run
                    int runCenterX = playerPoint.getX()+(runBi.getWidth());
                    int runCenterY = playerPoint.getY()+(runBi.getHeight()/2);
                    graphics.drawImage(runBi, null, runCenterX, runCenterY);
                    graphics.setColor(Color.BLACK);
                    drawTextShadow(graphics, runCenterX, runCenterY, String.valueOf(player.getStatus().getRunEnergy()));
                    graphics.setColor(Color.YELLOW);
                    centerStringOnImage(graphics, runCenterX, runCenterY, String.valueOf(player.getStatus().getRunEnergy()));
                    break;
                case Bars:
                    int width = 40; // width of each bar
                    int height = 3; // bar height
                    int offset = 5; // bar initial offset
                    int gap = height+1; // gap between bars
                    int left_start = playerPoint.getX()-width/2;

                    // draw hp
                    int hpX = playerPoint.getY()+offset+gap;
                    int hpBar = (int) ((width)*((double) player.getStatus().getHp()/(double) player.getStatus().getBaseHp()));
                    graphics.setColor(Color.black);
                    graphics.fillRect(left_start, hpX, width, height);
                    graphics.setColor(Color.red);
                    graphics.fillRect(left_start, hpX, hpBar, height);

                    //draw prayer
                    int prayX = playerPoint.getY()+offset+height+gap;
                    int prayBar = (int)((width)*((double) player.getStatus().getPrayer()/(double) player.getStatus().getBasePrayer()));
                    graphics.setColor(Color.black);
                    graphics.fillRect(left_start, prayX, width, height);
                    graphics.setColor(Color.CYAN);
                    graphics.fillRect(left_start, prayX, prayBar, height);

                    //draw run
                    int runX = playerPoint.getY()+offset+(height*2)+gap;
                    int runBar = (int) (width *((double) player.getStatus().getRunEnergy()/(double) 100));
                    graphics.setColor(Color.black);
                    graphics.fillRect(left_start, runX, width, height);
                    graphics.setColor(Color.orange.darker().darker());
                    graphics.fillRect(left_start, runX, runBar, height);

                    break;
                case None:
                    break;
            }
        }
    }

    private void drawTextShadow(Graphics graphics, int currentX, int currentY, String string){
        FontMetrics fm = graphics.getFontMetrics();
        int centerX = currentX+1;
        int centerY = currentY + (fm.getAscent() + fm.getHeight()/4)+1;
        graphics.drawString(string, centerX, centerY);
    }

    private void centerStringOnImage(Graphics graphics, int currentX, int currentY, String string){
        FontMetrics fm = graphics.getFontMetrics();
        int centerX = currentX;
        int centerY = currentY + (fm.getAscent() + fm.getHeight()/4);
        graphics.drawString(string, centerX, centerY);
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