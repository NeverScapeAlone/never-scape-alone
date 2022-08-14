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

package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.PlayerButtonOptionEnum;
import com.neverscapealone.enums.RegionNameEnum;
import com.neverscapealone.model.Player;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.neverscapealone.ui.NeverScapeAlonePanel.COLOR_PLUGIN_RED;
import static com.neverscapealone.ui.NeverScapeAlonePanel.SUB_BACKGROUND_COLOR;

public class PlayerPanelClass {
    Map<Integer, String> regionReference = RegionNameEnum.regionReference();

    public JPanel createPlayerPanel(Player player,
                                    String client_username,
                                    NeverScapeAlonePlugin plugin,
                                    boolean rating_selected,
                                    boolean discord_selected,
                                    boolean safety_selected,
                                    boolean location_selected,
                                    boolean stats_selected){
        JPanel player_panel = new JPanel();
        player_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        player_panel.setBackground(SUB_BACKGROUND_COLOR);
        player_panel.setLayout(new GridBagLayout());
        player_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints cp = new GridBagConstraints();
        cp.weightx = 1;
        cp.fill = GridBagConstraints.HORIZONTAL;
        cp.anchor = GridBagConstraints.CENTER;
        cp.gridx = 0;
        cp.gridy = 0;

        if (!Objects.equals(player.getLogin(), client_username)) {
            player_panel.add(playerNameButtonPanel(plugin, player.getLogin(), player.getUserId()), cp);
            cp.gridy += 1;
        }

        JPanel player_name_panel = new JPanel();
        player_name_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        player_name_panel.setBackground(SUB_BACKGROUND_COLOR);
        player_name_panel.setLayout(new GridBagLayout());
        GridBagConstraints pnp = new GridBagConstraints();
        pnp.weightx = 1;
        pnp.fill = GridBagConstraints.HORIZONTAL;
        pnp.anchor = GridBagConstraints.CENTER;
        pnp.gridx = 0;
        pnp.gridy = 0;

        JLabel player_name = new JLabel(player.getLogin());
        player_name.setFont(FontManager.getRunescapeBoldFont());
        if (player.getIsPartyLeader()) {
            player_name.setIcon(Icons.CROWN_ICON);
        } else {
            if (player.getVerified()) {
                player_name.setIcon(Icons.VERIFIED_ICON);
            } else {
                player_name.setIcon(Icons.UNVERIFIED_ICON);
            }
        }
        player_name.setToolTipText("ID: " + String.valueOf(player.getUserId()));
        player_name.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                StringSelection selection = new StringSelection(player.getLogin());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        player_name_panel.add(player_name, pnp);
        player_panel.add(player_name_panel, cp);
        cp.gridy += 1;

        if (rating_selected & (player.getRating() != -1)){
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            Float aFloat = (float)(player.getRating()/10);
            JLabel rating_label = new JLabel(String.valueOf(aFloat)+"/5.0");
            rating_label.setIcon(Icons.RATING_ICON);
            rating_label.setToolTipText("User rating");
            rating_label.setFont(FontManager.getRunescapeFont());
            player_panel.add(rating_label, cp);
            cp.gridy += 1;
        }

        if (discord_selected & (player.getDiscord() != null) & !player.getDiscord().equals("NULL")){
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            byte[] decodedBytes = Base64.getDecoder().decode(player.getDiscord());
            String decodedUsername = new String(decodedBytes);
            JLabel discord_label = new JLabel(decodedUsername);
            discord_label.setIcon(Icons.DISCORD_ICON);
            discord_label.setFont(FontManager.getRunescapeFont());
            player_panel.add(discord_label, cp);
            cp.gridy += 1;
        }

        if (safety_selected & (player.getRunewatch() != null) ){
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            JLabel runewatch_label = new JLabel("RUNEWATCH ALERT");
            runewatch_label.setToolTipText(player.getRunewatch());
            runewatch_label.setIcon(Icons.RUNEWATCH_ICON);
            runewatch_label.setFont(FontManager.getRunescapeFont());
            runewatch_label.setForeground(COLOR_PLUGIN_RED);
            player_panel.add(runewatch_label, cp);
            cp.gridy += 1;
        }

        if (safety_selected & player.getWdr() != null) {
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            JLabel wdr_label = new JLabel("WDR ALERT");
            wdr_label.setToolTipText(player.getWdr());
            wdr_label.setIcon(Icons.WDR_ICON);
            wdr_label.setFont(FontManager.getRunescapeFont());
            wdr_label.setForeground(COLOR_PLUGIN_RED);
            player_panel.add(wdr_label, cp);
            cp.gridy += 1;
        }

        if (location_selected & player.getLocation() != null) {
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            JPanel player_location = new JPanel();
            player_location.setBorder(new EmptyBorder(0, 0, 0, 0));
            player_location.setBackground(SUB_BACKGROUND_COLOR);
            player_location.setLayout(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.weightx = 1;
            cs.fill = GridBagConstraints.HORIZONTAL;
            cs.anchor = GridBagConstraints.CENTER;
            cs.gridx = 0;
            cs.gridy = 0;

            int world = player.getLocation().getWorld();
            int x = player.getLocation().getX();
            int y = player.getLocation().getY();
            int regionX = player.getLocation().getRegionX();
            int regionY = player.getLocation().getRegionY();
            int regionID = player.getLocation().getRegionID();
            int plane = player.getLocation().getPlane();

            JLabel world_label = new JLabel();
            world_label.setText(String.valueOf(world));
            world_label.setToolTipText("Player's current world");
            world_label.setIcon(Icons.WORLD_ICON);
            cs.gridx = 1;
            player_location.add(world_label, cs);

            JLabel coordinate_label = new JLabel();
            String region = regionReference.get(regionID);
            if (region == null) {
                region = "Unknown";
            }
            coordinate_label.setText(region);
            coordinate_label.setToolTipText("(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");
            coordinate_label.setIcon(Icons.COORDINATE_ICON);
            cs.gridx = 2;
            player_location.add(coordinate_label, cs);

            player_panel.add(player_location, cp);
            cp.gridy += 1;
        }

        if (stats_selected & (player.getStatus() != null)){
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            JPanel player_status = new JPanel();
            player_status.setBorder(new EmptyBorder(0, 0, 0, 0));
            player_status.setBackground(SUB_BACKGROUND_COLOR);
            player_status.setLayout(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.weightx = 1;
            cs.fill = GridBagConstraints.HORIZONTAL;
            cs.anchor = GridBagConstraints.CENTER;
            cs.gridx = 0;
            cs.gridy = 0;

            Integer base_hitpoints = player.getStatus().getBaseHp();
            Integer hitpoints = player.getStatus().getHp();
            Integer prayer = player.getStatus().getPrayer();
            Integer base_prayer = player.getStatus().getBasePrayer();
            Integer run_energy = player.getStatus().getRunEnergy();

            JLabel hitpoint_label = new JLabel(hitpoints + "/" + base_hitpoints);
            hitpoint_label.setIcon(Icons.HITPOINTS);
            player_status.add(hitpoint_label, cs);

            cs.gridx = 1;
            JLabel prayer_label = new JLabel(prayer + "/" + base_prayer);
            prayer_label.setIcon(Icons.PRAYER);
            player_status.add(prayer_label, cs);

            cs.gridx = 2;
            JLabel run_label = new JLabel(run_energy + "/100");
            run_label.setIcon(Icons.AGILITY);
            player_status.add(run_label, cs);

            player_panel.add(player_status, cp);
            cp.gridy += 1;
        }
        return player_panel;
    }

    private JPanel playerNameButtonPanel(NeverScapeAlonePlugin plugin, String login, Integer userId){
        JPanel player_name_button_panel = new JPanel();
        player_name_button_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        player_name_button_panel.setBackground(SUB_BACKGROUND_COLOR);
        player_name_button_panel.setLayout(new GridBagLayout());
        GridBagConstraints pnbp = new GridBagConstraints();
        pnbp.anchor = GridBagConstraints.LINE_END;
        pnbp.gridx = 0;
        pnbp.gridy = 0;

        JButton promote_party_leader = new JButton();
        promote_party_leader.setIcon(Icons.CROWN_ICON);
        promote_party_leader.setToolTipText("Promote " + login);
        promote_party_leader.setActionCommand(String.valueOf(userId));
        promote_party_leader.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.PROMOTE));
        player_name_button_panel.add(promote_party_leader, pnbp);
        pnbp.gridx += 1;

        JButton like_button = new JButton();
        like_button.setIcon(Icons.LIKE_ICON);
        like_button.setToolTipText("Like " + login);
        like_button.setActionCommand(String.valueOf(userId));

        like_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.LIKE));
        player_name_button_panel.add(like_button, pnbp);
        pnbp.gridx += 1;

        JButton dislike_button = new JButton();
        dislike_button.setIcon(Icons.DISLIKE_ICON);
        dislike_button.setToolTipText("Dislike " + login);
        dislike_button.setActionCommand(String.valueOf(userId));
        dislike_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.DISLIKE));
        player_name_button_panel.add(dislike_button, pnbp);
        pnbp.gridx += 1;

        JButton kick = new JButton();
        kick.setIcon(Icons.KICK_ICON);
        kick.setToolTipText("Kick " + login);
        kick.setActionCommand(String.valueOf(userId));
        kick.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.KICK));
        player_name_button_panel.add(kick, pnbp);
        pnbp.gridx += 1;
        return player_name_button_panel;
    }


}
