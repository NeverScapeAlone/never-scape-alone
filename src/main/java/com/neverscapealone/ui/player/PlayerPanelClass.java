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

package com.neverscapealone.ui.player;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.*;
import com.neverscapealone.models.payload.matchdata.player.Player;
import com.neverscapealone.models.payload.matchdata.player.equipment.Equipment;
import com.neverscapealone.models.payload.matchdata.player.inventory.Item;
import com.neverscapealone.models.payload.matchdata.player.prayer.PrayerSlot;
import com.neverscapealone.models.payload.matchdata.player.stats.Stats;
import com.neverscapealone.models.payload.matchdata.player.stats.agility.Agility;
import com.neverscapealone.models.payload.matchdata.player.stats.attack.Attack;
import com.neverscapealone.models.payload.matchdata.player.stats.construction.Construction;
import com.neverscapealone.models.payload.matchdata.player.stats.cooking.Cooking;
import com.neverscapealone.models.payload.matchdata.player.stats.crafting.Crafting;
import com.neverscapealone.models.payload.matchdata.player.stats.defence.Defence;
import com.neverscapealone.models.payload.matchdata.player.stats.farming.Farming;
import com.neverscapealone.models.payload.matchdata.player.stats.firemaking.Firemaking;
import com.neverscapealone.models.payload.matchdata.player.stats.fishing.Fishing;
import com.neverscapealone.models.payload.matchdata.player.stats.fletching.Fletching;
import com.neverscapealone.models.payload.matchdata.player.stats.herblore.Herblore;
import com.neverscapealone.models.payload.matchdata.player.stats.hitpoints.Hitpoints;
import com.neverscapealone.models.payload.matchdata.player.stats.hunter.Hunter;
import com.neverscapealone.models.payload.matchdata.player.stats.magic.Magic;
import com.neverscapealone.models.payload.matchdata.player.stats.mining.Mining;
import com.neverscapealone.models.payload.matchdata.player.stats.overall.Overall;
import com.neverscapealone.models.payload.matchdata.player.stats.prayer.Prayer;
import com.neverscapealone.models.payload.matchdata.player.stats.ranged.Ranged;
import com.neverscapealone.models.payload.matchdata.player.stats.runecraft.Runecraft;
import com.neverscapealone.models.payload.matchdata.player.stats.slayer.Slayer;
import com.neverscapealone.models.payload.matchdata.player.stats.smithing.Smithing;
import com.neverscapealone.models.payload.matchdata.player.stats.strength.Strength;
import com.neverscapealone.models.payload.matchdata.player.stats.thieving.Thieving;
import com.neverscapealone.models.payload.matchdata.player.stats.woodcutting.Woodcutting;
import com.neverscapealone.models.soundping.SoundPing;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Components;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.api.GameState;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;
import static com.neverscapealone.ui.utils.Components.title;

public class PlayerPanelClass {
    Map<Integer, String> regionReference = RegionNameEnum.regionReference();

    public JPanel playerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        playerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        playerPanel.add(Box.createVerticalStrut(4), c);
        c.gridy += 1;

        playerPanel.add(headerMatchPanel(), c);
        c.gridy += 1;

        playerPanel.add(Box.createVerticalStrut(4), c);
        c.gridy += 1;

        playerPanel.add(new JPanel(), c);
        return playerPanel;
    }

    public void switchHeaderButtonListener(ActionEvent actionEvent, MatchHeaderSwitchEnum matchHeaderSwitch){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        boolean b = true;
        if (button.isSelected()){
            button.setBackground(HIGHLIGHT_COLOR);
            b = true;
        } else {
            button.setBackground(WARNING_COLOR);
            b = false;
        }
        switch(matchHeaderSwitch){
            case RATING:
                NeverScapeAlonePanel.rating_selected = b;
                break;
            case STATS:
                NeverScapeAlonePanel.stats_selected = b;
                break;
            case SAFETY:
                NeverScapeAlonePanel.safety_selected = b;
                break;
            case DISCORD:
                NeverScapeAlonePanel.discord_selected = b;
                break;
            case LOCATION:
                NeverScapeAlonePanel.location_selected = b;
                break;
        }
    }

    public JPanel headerMatchPanel(){
        JPanel headerMatchPanel = new JPanel();
        headerMatchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        headerMatchPanel.setLayout(new GridBagLayout());
        GridBagConstraints sc = new GridBagConstraints();

        sc.weightx = 1;
        sc.anchor = GridBagConstraints.LINE_END;
        sc.fill = GridBagConstraints.LINE_END;
        sc.gridx = 0;
        sc.gridy = 0;

        JToggleButton rating_button = Components.matchHeaderToggle(Icons.RATING_ICON, "User Ratings", HIGHLIGHT_COLOR, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.RATING));
        headerMatchPanel.add(rating_button, sc);
        sc.gridx +=1;

        JToggleButton discord_button = Components.matchHeaderToggle(Icons.DISCORD_WHITE_ICON, "Discord Information", HIGHLIGHT_COLOR, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.DISCORD));
        headerMatchPanel.add(discord_button, sc);
        sc.gridx +=1;

        JToggleButton location_button = Components.matchHeaderToggle(Icons.WORLD_ICON, "Location Information", HIGHLIGHT_COLOR, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.LOCATION));
        headerMatchPanel.add(location_button, sc);
        sc.gridx +=1;

        JToggleButton safety_button = Components.matchHeaderToggle(Icons.SAFETY_ICON, "RuneWatch and WDR Safety", HIGHLIGHT_COLOR, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.SAFETY));
        headerMatchPanel.add(safety_button, sc);
        sc.gridx +=1;

        JToggleButton stats_button = Components.matchHeaderToggle(Icons.HITPOINTS, "User Stats", HIGHLIGHT_COLOR, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.STATS));
        headerMatchPanel.add(stats_button, sc);
        sc.gridx +=1;

        JButton leaveMatch = Components.cleanJButton(Icons.LOGOUT_ICON, "Leave Match", this::leaveMatch, 20, 20);
        headerMatchPanel.add(leaveMatch, sc);
        return headerMatchPanel;
    }

    public void leaveMatch(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        Object[] options = {"Leave",
                "Stay"};
        int n = JOptionPane.showOptionDialog(frame,
                "Would you like to leave this match?",
                "Match Logout",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            NeverScapeAlonePanel.eventBus.post(new SoundPing().buildSound(SoundPingEnum.MATCH_LEAVE));
            NeverScapeAlonePanel.websocket.logoff("Exiting match");
            NeverScapeAlonePanel.setView(PanelStateEnum.HOME);
            NeverScapeAlonePanel.refreshView();
        }
    }

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
        player_panel.setBackground(BACKGROUND_COLOR);
        player_panel.setLayout(new GridBagLayout());
        player_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints cp = new GridBagConstraints();
        cp.weightx = 1;
        cp.fill = GridBagConstraints.HORIZONTAL;
        cp.anchor = GridBagConstraints.CENTER;
        cp.gridx = 0;
        cp.gridy = 0;

        if (Objects.equals(player.getLogin(), client_username)) {
            player_panel.add(playerNameButtonPanel(player, plugin, player.getLogin(), player.getUserId(), true), cp);
            cp.gridy += 1;
        } else {
            player_panel.add(playerNameButtonPanel(player, plugin, player.getLogin(), player.getUserId(), false), cp);
            cp.gridy += 1;
        }

        JPanel player_name_panel = new JPanel();
        player_name_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        player_name_panel.setBackground(BACKGROUND_COLOR);
        player_name_panel.setLayout(new GridBagLayout());
        GridBagConstraints pnp = new GridBagConstraints();
        pnp.weightx = 1;
        pnp.fill = GridBagConstraints.HORIZONTAL;
        pnp.anchor = GridBagConstraints.CENTER;
        pnp.gridx = 0;
        pnp.gridy = 0;

        JLabel player_name = new JLabel(player.getLogin());
        player_name.setFont(FontManager.getRunescapeBoldFont());

        if (player.getGamestate() != null) {
            switch (GameState.of(player.getGamestate())) {
                case LOGIN_SCREEN:
                    player_name.setIcon(Icons.LOGIN_ICON);
                    player_name.setToolTipText(player.getLogin()+" is at the login screen");
                    break;
                case LOGGING_IN:
                case LOADING:
                case HOPPING:
                    player_name.setIcon(Icons.WAITING_ICON);
                    player_name.setToolTipText(player.getLogin()+" is hopping, loading, or logging in");
                    break;
                case CONNECTION_LOST:
                    player_name.setIcon(Icons.CONNECTION_LOST_ICON);
                    player_name.setToolTipText(player.getLogin()+"has lost connection to RuneScape");
                    break;
                case UNKNOWN:
                    player_name.setIcon(Icons.ERROR_ICON);
                    player_name.setToolTipText(player.getLogin()+" has error'd out!");
                    break;
                case LOGGED_IN:
                default:
                    if (player.getIsPartyLeader()) {
                        player_name.setIcon(Icons.CROWN_ICON);
                        player_name.setToolTipText(player.getLogin()+" is the party leader");
                    } else {
                        if (player.getVerified()) {
                            player_name.setIcon(Icons.VERIFIED_ICON);
                            player_name.setToolTipText(player.getLogin()+" is verified");
                        } else {
                            player_name.setIcon(Icons.UNVERIFIED_ICON);
                            player_name.setToolTipText(player.getLogin()+" is unverified");
                        }
                    }
                    break;
            }
        } else {
            if (player.getIsPartyLeader()) {
                player_name.setIcon(Icons.CROWN_ICON);
                player_name.setToolTipText(player.getLogin()+" is the party leader");
            } else {
                if (player.getVerified()) {
                    player_name.setIcon(Icons.VERIFIED_ICON);
                    player_name.setToolTipText(player.getLogin()+" is verified");
                } else {
                    player_name.setIcon(Icons.UNVERIFIED_ICON);
                    player_name.setToolTipText(player.getLogin()+" is unverified");
                }
            }
        }

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
            runewatch_label.setForeground(WARNING_COLOR);
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
            wdr_label.setForeground(WARNING_COLOR);
            player_panel.add(wdr_label, cp);
            cp.gridy += 1;
        }

        if (location_selected & player.getLocation() != null) {
            player_panel.add(Box.createVerticalStrut(3));
            cp.gridy += 1;

            JPanel player_location = new JPanel();
            player_location.setBorder(new EmptyBorder(0, 0, 0, 0));
            player_location.setBackground(BACKGROUND_COLOR);
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
            player_status.setBackground(BACKGROUND_COLOR);
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
            Integer special_attack = player.getStatus().getSpecialAttack();

            JLabel hitpoint_label = new JLabel(hitpoints + "/" + base_hitpoints);
            hitpoint_label.setIcon(Icons.HITPOINTS);
            double hp_ratio = (double) hitpoints/ (double)base_hitpoints;
            hitpoint_label.setForeground(convertRatioToColor(hp_ratio));
            player_status.add(hitpoint_label, cs);

            cs.gridx = 1;
            JLabel prayer_label = new JLabel(prayer + "/" + base_prayer);
            double prayer_ratio = (double) prayer/ (double)base_prayer;
            prayer_label.setForeground(convertRatioToColor(prayer_ratio));
            prayer_label.setIcon(Icons.PRAYER);
            player_status.add(prayer_label, cs);

            cs.gridx = 2;
            JLabel special_label = new JLabel((special_attack/10)+"%");
            double special_ratio = (double) special_attack / (double) 1000;
            special_label.setForeground(convertRatioToColor(special_ratio));
            special_label.setIcon(Icons.SPECIAL_ICON);
            player_status.add(special_label, cs);

            cs.gridx = 3;
            JLabel run_label = new JLabel(run_energy+"%");
            double run_ratio = (double) run_energy / (double) 100;
            run_label.setForeground(convertRatioToColor(run_ratio));
            run_label.setIcon(Icons.AGILITY);
            player_status.add(run_label, cs);

            player_panel.add(player_status, cp);
            cp.gridy += 1;
        }

        JPanel playerContentsDisplay = new JPanel();
        MaterialTabGroup playerContentTabs = new MaterialTabGroup(playerContentsDisplay);
        MaterialTab empty = new MaterialTab(Icons.HIDE_ICON, playerContentTabs, emptyPanel(player));
        MaterialTab inventory = new MaterialTab(Icons.INVENTORY_ICON, playerContentTabs, playerInventory(player));
        MaterialTab equipment = new MaterialTab(Icons.EQUIPMENT_ICON, playerContentTabs, playerEquipment(player));
        MaterialTab prayer = new MaterialTab(Icons.PRAYER, playerContentTabs, playerPrayer(player));
        MaterialTab stats = new MaterialTab(Icons.ALL_SKILLS, playerContentTabs, playerStats(player));
        empty.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.EMPTY);
            }
        });

        inventory.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.INVENTORY);
            }
        });

        stats.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.STATS);
            }
        });

        equipment.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.EQUIPMENT);
            }
        });

        prayer.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
                playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.PRAYER);
            }
        });

        playerContentTabs.addTab(empty);
        playerContentTabs.addTab(stats);
        playerContentTabs.addTab(inventory);
        playerContentTabs.addTab(equipment);
        playerContentTabs.addTab(prayer);
        if (playerSelectionPanelEnumHashMap.get(String.valueOf(player.getUserId())) == null){
            playerContentTabs.select(empty);
            playerSelectionPanelEnumHashMap.put(String.valueOf(player.getUserId()), PlayerSelectionPanelEnum.EMPTY);
        } else {
            switch (playerSelectionPanelEnumHashMap.get(String.valueOf(player.getUserId()))){
                case EMPTY:
                    playerContentTabs.select(empty);
                    break;
                case STATS:
                    playerContentTabs.select(stats);
                    break;
                case INVENTORY:
                    playerContentTabs.select(inventory);
                    break;
                case EQUIPMENT:
                    playerContentTabs.select(equipment);
                    break;
                case PRAYER:
                    playerContentTabs.select(prayer);
                    break;
            }
        }

        player_panel.add(playerContentTabs, cp);
        cp.gridy += 1;
        player_panel.add(playerContentsDisplay, cp);
        cp.gridy += 1;

        return player_panel;
    }

    private Color convertRatioToColor(double ratio){
        if (ratio > 1){
            return new Color(0, 255, 200); // show cyan for above 100
        }
        float g = ((float) ratio >= (float) 0.5 ? (float) 1 : (float) ratio/(float) 0.5);
        float r = ((float) ratio <= (float) 0.5 ? (float) 1 : (float)2 - ((float)2*(float)ratio));
        return new Color(r, g, 0);
    }

    private JPanel emptyPanel(Player player){
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    private JPanel playerInventory(Player player){
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridLayout(7,4));

        if (player.getInventory() == null){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("No Inventory Available", WARNING_COLOR));
            return panel;
        }

        int real_items = 0;
        for (Item item : player.getInventory()){
            if (item.getItemID() == -1){
                continue;
            }
            real_items += 1;
        }
        if (real_items == 0){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("Empty Inventory", NOTIFIER_COLOR));
            return panel;
        }

        for (Item item : player.getInventory()){
            if (item == null){
                continue;
            }
            JLabel i = new JLabel(Icons.PADDING_INVENTORY);
            if (item.getItemID() != -1){
                plugin.addImageToLabel(i, item);
            }
            panel.add(i);
        }
        return panel;
    }
    private JPanel playerEquipment(Player player){
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(4,3));

        if (player.getEquipment() == null){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("No Equipment Available", WARNING_COLOR));
            return panel;
        }

        Equipment equipment = player.getEquipment();

        if (equipment.getHead() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getHead().getItemId(), equipment.getHead().getItemAmount()), EquipmentSlotEnum.HEAD));
        }
        if (equipment.getCape() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getCape().getItemId(), equipment.getCape().getItemAmount()), EquipmentSlotEnum.CAPE));
        }
        if (equipment.getAmulet() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getAmulet().getItemId(), equipment.getAmulet().getItemAmount()), EquipmentSlotEnum.AMULET));
        }
        if (equipment.getAmmo() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getAmmo().getItemId(), equipment.getAmmo().getItemAmount()), EquipmentSlotEnum.AMMO));
        }
        if (equipment.getWeapon() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getWeapon().getItemId(), equipment.getWeapon().getItemAmount()), EquipmentSlotEnum.WEAPON));
        }
        if (equipment.getBody() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getBody().getItemId(), equipment.getBody().getItemAmount()), EquipmentSlotEnum.BODY));
        }
        if (equipment.getShield() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getShield().getItemId(), equipment.getShield().getItemAmount()), EquipmentSlotEnum.SHIELD));
        }
        if (equipment.getLegs() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getLegs().getItemId(), equipment.getLegs().getItemAmount()), EquipmentSlotEnum.LEGS));
        }
        if (equipment.getGloves() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getGloves().getItemId(), equipment.getGloves().getItemAmount()), EquipmentSlotEnum.GLOVES));
        }
        if (equipment.getBoots() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getBoots().getItemId(), equipment.getBoots().getItemAmount()), EquipmentSlotEnum.BOOTS));
        }
        if (equipment.getRing() != null){
            panel.add(drawEquipmentItemPanel(new Item().buildItem(equipment.getRing().getItemId(), equipment.getRing().getItemAmount()), EquipmentSlotEnum.RING));
        }

        return panel;
    }

    private JPanel drawEquipmentItemPanel(Item item, EquipmentSlotEnum equipmentSlotEnum){
        JPanel itemPanel =  new JPanel();
        itemPanel.setBackground(BACKGROUND_COLOR);
        itemPanel.setLayout(new BorderLayout());

        JLabel name = new JLabel(equipmentSlotEnum.getName());
        name.setFont(FontManager.getRunescapeFont());
        name.setForeground(Color.YELLOW);
        name.setBackground(BACKGROUND_COLOR);
        itemPanel.add(name, BorderLayout.WEST);

        if (item.getItemID() == -1){
            itemPanel.add(title("No Item", WARNING_COLOR), BorderLayout.WEST);
        } else {
            JLabel itemImage = new JLabel();
            itemImage.setBackground(BACKGROUND_COLOR);
            plugin.addImageToLabel(itemImage, item);
            itemPanel.add(itemImage,BorderLayout.CENTER);
        }

        return itemPanel;
    }

    private JPanel playerPrayer(Player player){
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 0;

        if (player.getPrayer() == null){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("No Prayers Available", WARNING_COLOR));
            return panel;
        }
        if (player.getPrayer().size() == 0){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("Player Not Praying", NOTIFIER_COLOR));
            return panel;
        }
        for (PrayerSlot prayerSlot : player.getPrayer()){
            ImageIcon prayerIcon = PrayerTypeEnum.valueOf(prayerSlot.getPrayerName()).getIcon();
            JLabel prayerName = new JLabel(WordUtils.capitalize(prayerSlot.getPrayerName().replace("_"," ").toLowerCase()));
            prayerName.setIcon(prayerIcon);
            prayerName.setForeground(Color.YELLOW);
            prayerName.setFont(FontManager.getRunescapeFont());
            c.gridy +=1;
            panel.add(Box.createVerticalStrut(2));
            c.gridy +=1;
            panel.add(prayerName, c);
        }
        return panel;
    }
    private JPanel playerStats(Player player){
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(8,3));

        if (player.getStats() == null){
            panel.setLayout(new GridLayout(1,1));
            panel.add(title("No Stats Available", WARNING_COLOR));
            return panel;
        }

        Stats stats = player.getStats();

        Attack attack = stats.getAttack();
        panel.add(drawStat(attack.getBoosted(), attack.getReal(), attack.getExperience(), Icons.ATTACK));

        Hitpoints hitpoints = stats.getHitpoints();
        panel.add(drawStat(hitpoints.getBoosted(), hitpoints.getReal(), hitpoints.getExperience(), Icons.HITPOINTS));

        Mining mining = stats.getMining();
        panel.add(drawStat(mining.getBoosted(), mining.getReal(), mining.getExperience(), Icons.MINING));

        Strength strength = stats.getStrength();
        panel.add(drawStat(strength.getBoosted(), strength.getReal(), strength.getExperience(), Icons.STRENGTH));

        Agility agility = stats.getAgility();
        panel.add(drawStat(agility.getBoosted(), agility.getReal(), agility.getExperience(), Icons.AGILITY));

        Smithing smithing = stats.getSmithing();
        panel.add(drawStat(smithing.getBoosted(), smithing.getReal(), smithing.getExperience(), Icons.SMITHING));

        Defence defence = stats.getDefence();
        panel.add(drawStat(defence.getBoosted(), defence.getReal(), defence.getExperience(), Icons.DEFENCE));

        Herblore herblore = stats.getHerblore();
        panel.add(drawStat(herblore.getBoosted(), herblore.getReal(), herblore.getExperience(), Icons.HERBLORE));

        Fishing fishing = stats.getFishing();
        panel.add(drawStat(fishing.getBoosted(), fishing.getReal(), fishing.getExperience(), Icons.FISHING));

        Ranged ranged = stats.getRanged();
        panel.add(drawStat(ranged.getBoosted(), ranged.getReal(), ranged.getExperience(), Icons.RANGED));

        Thieving thieving = stats.getThieving();
        panel.add(drawStat(thieving.getBoosted(), thieving.getReal(), thieving.getExperience(), Icons.THIEVING));

        Cooking cooking = stats.getCooking();
        panel.add(drawStat(cooking.getBoosted(), cooking.getReal(), cooking.getExperience(), Icons.COOKING));

        Prayer prayer = stats.getPrayer();
        panel.add(drawStat(prayer.getBoosted(), prayer.getReal(), prayer.getExperience(), Icons.PRAYER));

        Crafting crafting = stats.getCrafting();
        panel.add(drawStat(crafting.getBoosted(), crafting.getReal(), crafting.getExperience(), Icons.CRAFTING));

        Firemaking firemaking = stats.getFiremaking();
        panel.add(drawStat(firemaking.getBoosted(), firemaking.getReal(), firemaking.getExperience(), Icons.FIREMAKING));

        Magic magic = stats.getMagic();
        panel.add(drawStat(magic.getBoosted(), magic.getReal(), magic.getExperience(), Icons.MAGIC));

        Fletching fletching = stats.getFletching();
        panel.add(drawStat(fletching.getBoosted(), fletching.getReal(), fletching.getExperience(), Icons.FLETCHING));

        Woodcutting woodcutting = stats.getWoodcutting();
        panel.add(drawStat(woodcutting.getBoosted(), woodcutting.getReal(), woodcutting.getExperience(), Icons.WOODCUTTING));

        Runecraft runecraft = stats.getRunecraft();
        panel.add(drawStat(runecraft.getBoosted(), runecraft.getReal(), runecraft.getExperience(), Icons.RUNECRAFTING));

        Slayer slayer = stats.getSlayer();
        panel.add(drawStat(slayer.getBoosted(), slayer.getReal(), slayer.getExperience(), Icons.SLAYER));

        Farming farming = stats.getFarming();
        panel.add(drawStat(farming.getBoosted(), farming.getReal(), farming.getExperience(), Icons.FARMING));

        Construction construction = stats.getConstruction();
        panel.add(drawStat(construction.getBoosted(), construction.getReal(), construction.getExperience(), Icons.CONSTRUCTION));

        Hunter hunter = stats.getHunter();
        panel.add(drawStat(hunter.getBoosted(), hunter.getReal(), hunter.getExperience(), Icons.HUNTER));

        Overall overall = stats.getOverall();
        int overallBase = attack.getReal()+hitpoints.getReal()+mining.getReal()+strength.getReal()+agility.getReal()+smithing.getReal()+defence.getReal()+herblore.getReal()+fishing.getReal()+ranged.getReal()+thieving.getReal()+cooking.getReal()+prayer.getReal()+crafting.getReal()+firemaking.getReal()+magic.getReal()+fletching.getReal()+woodcutting.getReal()+runecraft.getReal()+slayer.getReal()+farming.getReal()+construction.getReal()+hunter.getReal();
        panel.add(drawStat(null, overallBase, overall.getExperience(), Icons.ALL_SKILLS));

        return panel;
    }

    private JLabel drawStat(Integer boosted, int base, int experience, ImageIcon icon){
        JLabel stat = new JLabel();
        stat.setIcon(icon);
        stat.setToolTipText("XP: "+String.format("%,d", experience));
        if (boosted != null){
            stat.setText(boosted+"/"+base);
        } else {
            stat.setText(String.valueOf(base));
        }
        stat.setFont(FontManager.getRunescapeFont());
        stat.setForeground(Color.YELLOW);
        stat.setBackground(ALT_BACKGROUND);
        return stat;
    }

    private JPanel playerNameButtonPanel(Player player, NeverScapeAlonePlugin plugin, String login, Integer userId, Boolean isSelf){
        JPanel player_name_button_panel = new JPanel();
        player_name_button_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        player_name_button_panel.setBackground(BACKGROUND_COLOR);
        player_name_button_panel.setLayout(new GridBagLayout());
        GridBagConstraints pnbp = new GridBagConstraints();
        pnbp.anchor = GridBagConstraints.LINE_END;
        pnbp.gridx = 0;
        pnbp.gridy = 0;

        if (!isSelf){
            JButton ping_sound = new JButton();
            if (NeverScapeAlonePlugin.playerMutePingSoundArrayList.contains(player.getLogin())){
                ping_sound.setIcon(Icons.PING_SOUND_OFF_ICON);
                ping_sound.setToolTipText("Unmute " + player.getLogin()+"'s pings");
            } else {
                ping_sound.setIcon(Icons.PING_SOUND_ON_ICON);
                ping_sound.setToolTipText("Mute" + player.getLogin()+"'s pings");
            }
            ping_sound.setActionCommand(String.valueOf(player.getUserId()));
            ping_sound.addActionListener(e -> NeverScapeAlonePlugin.togglePingSound(player.getLogin(), e));
            player_name_button_panel.add(ping_sound, pnbp);
            pnbp.gridx += 1;

            // promote button

            JButton promote_button = new JButton();
            if (NeverScapeAlonePlugin.playerPromoteButtonArrayList.contains(player.getLogin())){
                promote_button.setBackground(HIGHLIGHT_COLOR);
            }
            promote_button.setIcon(Icons.CROWN_ICON);
            promote_button.setToolTipText("Promote " + login);
            promote_button.setActionCommand(String.valueOf(userId));
            promote_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.PROMOTE));
            promote_button.addActionListener(e -> NeverScapeAlonePlugin.togglePromoteButton(player.getLogin(), e));
            player_name_button_panel.add(promote_button, pnbp);
            pnbp.gridx += 1;

            // kick button

            JButton kick_button = new JButton();
            if (NeverScapeAlonePlugin.playerKickButtonArrayList.contains(player.getLogin())){
                kick_button.setBackground(NOTIFIER_COLOR);
            }
            kick_button.setIcon(Icons.KICK_ICON);
            kick_button.setToolTipText("Kick " + login);
            kick_button.setActionCommand(String.valueOf(userId));
            kick_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.KICK));
            kick_button.addActionListener(e -> NeverScapeAlonePlugin.toggleKickButton(player.getLogin(), e));
            player_name_button_panel.add(kick_button, pnbp);
            pnbp.gridx += 1;

            // like button

            JButton like_button = new JButton();
            if (NeverScapeAlonePlugin.playerLikeButtonArrayList.contains(player.getLogin())){
                like_button.setBackground(HIGHLIGHT_COLOR);
            }
            like_button.setIcon(Icons.LIKE_ICON);
            like_button.setToolTipText("Like " + login);
            like_button.setActionCommand(String.valueOf(userId));
            like_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.LIKE));
            like_button.addActionListener(e -> NeverScapeAlonePlugin.toggleLikeButton(player.getLogin(), e));
            player_name_button_panel.add(like_button, pnbp);
            pnbp.gridx += 1;

            // dislike button

            JButton dislike_button = new JButton();
            if (NeverScapeAlonePlugin.playerDislikeButtonArrayList.contains(player.getLogin())){
                dislike_button.setBackground(NOTIFIER_COLOR);
            }
            dislike_button.setIcon(Icons.DISLIKE_ICON);
            dislike_button.setToolTipText("Dislike " + login);
            dislike_button.setActionCommand(String.valueOf(userId));
            dislike_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.DISLIKE));
            dislike_button.addActionListener(e -> NeverScapeAlonePlugin.toggleDisikeButton(player.getLogin(), e));
            player_name_button_panel.add(dislike_button, pnbp);
            pnbp.gridx += 1;

//            Will be added later [Placeholder]
//            // add button
//
//            JButton add_button = new JButton();
//            if (NeverScapeAlonePlugin.playerAddButtonArrayList.contains(player.getLogin())){
//                add_button.setBackground(HIGHLIGHT_COLOR);
//            }
//            add_button.setIcon(Icons.ADD_USER_ICON);
//            add_button.setToolTipText("Add " + player.getLogin());
//            add_button.setActionCommand(String.valueOf(player.getUserId()));
//            add_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.ADD));
//            add_button.addActionListener(e -> NeverScapeAlonePlugin.toggleAddButton(player.getLogin(), e));
//            player_name_button_panel.add(add_button, pnbp);
//            pnbp.gridx += 1;
//
//            // block button
//
//            JButton block_button = new JButton();
//            if (NeverScapeAlonePlugin.playerBlockButtonArrayList.contains(player.getLogin())){
//                block_button.setBackground(NOTIFIER_COLOR);
//            }
//            block_button.setIcon(Icons.BLOCK_USER_ICON);
//            block_button.setToolTipText("Block " + player.getLogin());
//            block_button.setActionCommand(String.valueOf(player.getUserId()));
//            block_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.BLOCK));
//            block_button.addActionListener(e -> NeverScapeAlonePlugin.toggleBlockButton(player.getLogin(), e));
//            player_name_button_panel.add(block_button, pnbp);
//            pnbp.gridx += 1;

        }
        return player_name_button_panel;
    }

}
