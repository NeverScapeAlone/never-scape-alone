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

package com.neverscapealone.ui.match;

import com.neverscapealone.enums.AccountTypeSelectionEnum;
import com.neverscapealone.enums.ActivityReferenceEnum;
import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.models.payload.searchmatches.SearchMatchData;
import com.neverscapealone.ui.utils.Components;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;
import static com.neverscapealone.ui.utils.Components.htmlWrap;

public class ActivityPanelClass {

    public static ArrayList<String> minimizedMatches = new ArrayList<>();

    public JPanel createCurrentActivityPanel(MatchData matchData){
        return matchDataConversion(matchData);
    }

    public JPanel createSearchMatchDataPanel(SearchMatchData matchData){
        return matchDataConversion(matchData);
    }

    public JPanel matchDataConversion(Object dataInput){
        if (dataInput instanceof MatchData){
            MatchData matchData = ((MatchData) dataInput);
            String activity = matchData.getActivity();
            boolean isPrivate = matchData.getIsPrivate();
            boolean RuneGuard = matchData.getRuneGuard();
            String ID = matchData.getId();
            String fcLeader = matchData.getPlayers().get(0).getLogin();
            String playerCount = String.valueOf(matchData.getPlayers().size());
            String memberCount = matchData.getPartyMembers();
            String experience = matchData.getRequirement().getExperience();
            String splitType = matchData.getRequirement().getSplitType();
            String accounts = matchData.getRequirement().getAccounts();
            String region = matchData.getRequirement().getRegions();
            String notes = matchData.getNotes();
            String matchVersion = matchData.getMatchVersion();

            if (minimizedMatches.contains(ID)){
                return compressedActivityPanel(activity,
                        isPrivate,
                        RuneGuard,
                        ID,
                        null,
                        fcLeader,
                        playerCount,
                        memberCount,
                        experience,
                        splitType,
                        accounts,
                        region,
                        notes,
                        true,
                        matchVersion);
            } else {
                return createActivityPanel(activity,
                        isPrivate,
                        RuneGuard,
                        ID,
                        null,
                        fcLeader,
                        playerCount,
                        memberCount,
                        experience,
                        splitType,
                        accounts,
                        region,
                        notes,
                        true,
                        matchVersion);
            }


        }
        if (dataInput instanceof SearchMatchData){
            SearchMatchData searchMatchData = ((SearchMatchData) dataInput);
            String activity = searchMatchData.getActivity();
            boolean isPrivate = searchMatchData.getIsPrivate();
            boolean RuneGuard = searchMatchData.getRuneGuard();
            String ID = searchMatchData.getId();
            String partyLeader = searchMatchData.getPartyLeader();
            String playerCount = searchMatchData.getPlayerCount();
            String memberCount = searchMatchData.getPartyMembers();
            String experience = searchMatchData.getExperience();
            String splitType = searchMatchData.getSplitType();
            String accounts = searchMatchData.getAccounts();
            String region = searchMatchData.getRegions();
            String notes = searchMatchData.getNotes();
            String matchVersion = searchMatchData.getMatchVersion();

            if (minimizedMatches.contains(ID)){
            return compressedActivityPanel(activity,
                                        isPrivate,
                                        RuneGuard,
                                        ID,
                                        partyLeader,
                                        null,
                                        playerCount,
                                        memberCount,
                                        experience,
                                        splitType,
                                        accounts,
                                        region,
                                        notes,
                                        false,
                                        matchVersion);
            } else {
            return createActivityPanel(activity,
                                        isPrivate,
                                        RuneGuard,
                                        ID,
                                        partyLeader,
                                        null,
                                        playerCount,
                                        memberCount,
                                        experience,
                                        splitType,
                                        accounts,
                                        region,
                                        notes,
                                        false,
                                        matchVersion);
            }
        }
        return null;
    }

    public void miniMaximizer(ActionEvent actionEvent){
        JButton button = (JButton) actionEvent.getSource();
        String command = actionEvent.getActionCommand();
        if (Objects.equals(command, "match_minimize")){
            minimizedMatches.add(button.getName());
            refreshMatchPanel();
        }
        if (Objects.equals(command, "match_maximize")){
            minimizedMatches.remove(button.getName());
            refreshMatchPanel();
        }
        if (Objects.equals(command, "search_minimize")){
            minimizedMatches.add(button.getName());
            refreshSearchPanel();
        }
        if (Objects.equals(command, "search_maximize")){
            minimizedMatches.remove(button.getName());
            refreshSearchPanel();
        }
    }

    public JPanel compressedActivityPanel(String activity,
                                          boolean isPrivate,
                                          boolean RuneGuard,
                                          String ID,
                                          String partyLeader,
                                          String fcLeader,
                                          String playerCount,
                                          String memberCount,
                                          String experience,
                                          String splitType,
                                          String accounts,
                                          String region,
                                          String notes,
                                          boolean isMatchData,
                                          String matchVersion){

        JPanel compressedActivityPanel = new JPanel();
        compressedActivityPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        compressedActivityPanel.setBackground(BACKGROUND_COLOR);
        compressedActivityPanel.setLayout(new GridBagLayout());
        compressedActivityPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        // activity image
        ActivityReferenceEnum activityReferenceEnum = ActivityReferenceEnum.valueOf(activity);
        ImageIcon activity_icon = activityReferenceEnum.getIcon();
        String activity_name = activityReferenceEnum.getTooltip();
        JLabel activity_label = new JLabel(activity_icon);
        activity_label.setToolTipText(activity_name);

        compressedActivityPanel.add(activity_label, c);
        c.gridx += 1;

        // private icon
        JLabel private_label = new JLabel();
        if (isPrivate) {
            private_label.setIcon(Icons.PRIVATE_ICON);
            private_label.setForeground(NOTIFIER_COLOR);
        } else {
            private_label.setIcon(Icons.PUBLIC_ICON);
            private_label.setForeground(HIGHLIGHT_COLOR);
        }
        private_label.setToolTipText("Match ID: " + ID);

        compressedActivityPanel.add(private_label, c);
        c.gridx += 1;

        // fc/party lead

        JLabel chat_leader_slot = new JLabel();
        if (partyLeader != null){
            chat_leader_slot.setIcon(Icons.CROWN_ICON);
            chat_leader_slot.setToolTipText(partyLeader);
        } else if (fcLeader != null) {
            chat_leader_slot.setIcon(Icons.CHAT);
            chat_leader_slot.setToolTipText("FC: \""+fcLeader+"\"");
        }

        compressedActivityPanel.add(chat_leader_slot, c);
        c.gridx += 1;

        // experience
        JLabel experience_label = new JLabel();
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText(experience);

        compressedActivityPanel.add(experience_label, c);
        c.gridx += 1;

        // player count label
        JLabel player_count_label = new JLabel();
        player_count_label.setIcon(Icons.PLAYERS_ICON);
        player_count_label.setToolTipText(playerCount+"/"+memberCount);

        compressedActivityPanel.add(player_count_label, c);
        c.gridx += 1;

        // split label
        JLabel split_label = new JLabel();
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText(splitType);

        compressedActivityPanel.add(split_label, c);
        c.gridx += 1;

        // accounts
        ImageIcon account_image = AccountTypeSelectionEnum.valueOf(accounts).getImage();
        JLabel accounts_label = new JLabel();
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText(accounts);

        compressedActivityPanel.add(accounts_label, c);
        c.gridx += 1;

        // region
        JLabel region_label = new JLabel();
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText(region);

        compressedActivityPanel.add(region_label, c);
        c.gridx += 1;

        // notes
        if ((Objects.requireNonNull(notes).length() >= 1)){
            String convertNotes = htmlWrap(notes);
            JLabel notes_label = new JLabel();
            notes_label.setIcon(Icons.NOTES_ICON);
            notes_label.setToolTipText(convertNotes);
            compressedActivityPanel.add(notes_label, c);
            c.gridx += 1;
        }

        // RuneGuard
        JLabel runeguard_label = new JLabel();
        if (RuneGuard){
            runeguard_label.setIcon(Icons.RUNEGUARD_ENABLED_ICON);
            runeguard_label.setToolTipText("RuneGuard Enabled");
        } else {
            runeguard_label.setIcon(Icons.RUNEGUARD_DISABLED_ICON);
            runeguard_label.setToolTipText("RuneGuard Disabled");
        }

        compressedActivityPanel.add(runeguard_label, c);
        c.gridx += 1;

        String pluginVersion = websocket.getPluginVersion();
        if (!Objects.equals(matchVersion, pluginVersion)){
            JLabel matchVersionLabel = new JLabel();
            matchVersionLabel.setIcon(Icons.CANCEL_ICON);
            matchVersionLabel.setToolTipText("Current Version Incompatible. Plugin: "+pluginVersion+" Match : "+matchVersion);
            compressedActivityPanel.add(matchVersionLabel, c);
            c.gridx +=1;
        }

        JButton expandCollapse = Components.cleanJButton(Icons.MINIMIZED_ICON,"Maximize panel", this::miniMaximizer,10,10);
        expandCollapse.setName(ID);
        if (isMatchData){
            expandCollapse.setActionCommand("match_maximize");
        } else {
            expandCollapse.setActionCommand("search_maximize");
        }

        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.LINE_END;
        compressedActivityPanel.add(expandCollapse, c);

        return compressedActivityPanel;
    }

    public JPanel createActivityPanel(String activity,
                                      boolean isPrivate,
                                      boolean RuneGuard,
                                      String ID,
                                      String partyLeader,
                                      String fcLeader,
                                      String playerCount,
                                      String memberCount,
                                      String experience,
                                      String splitType,
                                      String accounts,
                                      String region,
                                      String notes,
                                      boolean isMatchData,
                                      String matchVersion)
    {
        JPanel current_activity_panel = new JPanel();
        current_activity_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        current_activity_panel.setBackground(BACKGROUND_COLOR);
        current_activity_panel.setLayout(new GridBagLayout());
        current_activity_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints ca = new GridBagConstraints();
        ca.weightx = 1;
        ca.fill = GridBagConstraints.HORIZONTAL;
        ca.anchor = GridBagConstraints.CENTER;
        ca.gridx = 0;
        ca.gridy = 0;

        // activity/match label
        ActivityReferenceEnum activityReferenceEnum = ActivityReferenceEnum.valueOf(activity);
        ImageIcon activity_icon = activityReferenceEnum.getIcon();
        String activity_name = activityReferenceEnum.getTooltip();

        JLabel match_title = new JLabel("<html><p style=\"overflow-wrap: break-word;\">" + activity_name + "</p></html>");
        match_title.setIcon(activity_icon);
        match_title.setFont(FontManager.getRunescapeBoldFont());

        JButton expandCollapse = Components.cleanJButton(Icons.MAXIMIZED_ICON,"Minimize panel", this::miniMaximizer,10,10);
        expandCollapse.setName(ID);
        if (isMatchData){
            expandCollapse.setActionCommand("match_minimize");
        } else {
            expandCollapse.setActionCommand("search_minimize");
        }


        current_activity_panel.add(doubleLabelPanel(match_title, expandCollapse), ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        // leader/fc leader & party label

        JLabel privateLabel = new JLabel();
        if (isPrivate) {
            privateLabel.setText("Private");
            privateLabel.setIcon(Icons.PRIVATE_ICON);
            privateLabel.setForeground(NOTIFIER_COLOR);
        } else {
            privateLabel.setText("Public");
            privateLabel.setIcon(Icons.PUBLIC_ICON);
            privateLabel.setForeground(HIGHLIGHT_COLOR);
        }
        privateLabel.setToolTipText("Match ID: " + ID);

        if (partyLeader != null){
            JLabel party_leader = new JLabel(partyLeader);
            party_leader.setIcon(Icons.CROWN_ICON);
            party_leader.setToolTipText("Party Leader");
            current_activity_panel.add(doubleLabelPanel(party_leader, privateLabel), ca);
            ca.gridy += 1;

            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;
        } else if (fcLeader != null) {
            JLabel friends_chat_label = new JLabel("FC: \""+fcLeader+"\"");
            friends_chat_label.setIcon(Icons.CHAT);
            friends_chat_label.setToolTipText("Friend's Chat");
            current_activity_panel.add(doubleLabelPanel(friends_chat_label, privateLabel), ca);
            ca.gridy += 1;

            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;
        }

        JLabel experience_label = new JLabel(experience);
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText("Experience");

        JLabel player_count_label = new JLabel(playerCount+"/"+memberCount);
        player_count_label.setIcon(Icons.PLAYERS_ICON);
        player_count_label.setToolTipText("Players");

        current_activity_panel.add(doubleLabelPanel(player_count_label, experience_label), ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel split_label = new JLabel(splitType);
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText("Loot Split");

        ImageIcon account_image = AccountTypeSelectionEnum.valueOf(accounts).getImage();
        JLabel accounts_label = new JLabel(accounts);
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText("Accounts");

        current_activity_panel.add(doubleLabelPanel(split_label, accounts_label), ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel region_label = new JLabel(region);
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText("Match Region");

        JLabel runeguard_label = new JLabel();
        if (RuneGuard){
            runeguard_label.setIcon(Icons.RUNEGUARD_ENABLED_ICON);
            runeguard_label.setText("Enabled");
            runeguard_label.setForeground(HIGHLIGHT_COLOR);
            runeguard_label.setToolTipText("RuneGuard Enabled");
        } else {
            runeguard_label.setIcon(Icons.RUNEGUARD_DISABLED_ICON);
            runeguard_label.setText("Disabled");
            runeguard_label.setForeground(NOTIFIER_COLOR);
            runeguard_label.setToolTipText("RuneGuard Disabled");
        }

        current_activity_panel.add(doubleLabelPanel(region_label, runeguard_label), ca);
        ca.gridy +=1;

        if (Objects.requireNonNull(notes).length() >= 1){
            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;

            String convertNotes = htmlWrap(notes);
            JLabel notes_label = new JLabel(convertNotes);
            notes_label.setFont(FontManager.getRunescapeSmallFont());
            notes_label.setIcon(Icons.NOTES_ICON);
            current_activity_panel.add(notes_label, ca);
            ca.gridy += 1;
        }

        String pluginVersion = websocket.getPluginVersion();
        if (!Objects.equals(matchVersion, pluginVersion)){
            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;

            JLabel matchVersionLabel = new JLabel(matchVersion);
            matchVersionLabel.setIcon(Icons.CANCEL_ICON);
            matchVersionLabel.setFont(FontManager.getRunescapeSmallFont());
            matchVersionLabel.setForeground(WARNING_COLOR);
            matchVersionLabel.setToolTipText("Current Version Incompatible. Plugin: "+pluginVersion+" Match : "+matchVersion);
            current_activity_panel.add(matchVersionLabel, ca);
            ca.gridy += 1;
        }
        return current_activity_panel;
    }

    private JPanel doubleLabelPanel(JComponent left, JComponent right){
        JPanel doubleLabelPanel = new JPanel();
        doubleLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        doubleLabelPanel.setBackground(BACKGROUND_COLOR);
        doubleLabelPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        doubleLabelPanel.add(left, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.LINE_END;

        if (right instanceof JLabel) {
            ((JLabel) right).setHorizontalTextPosition(SwingConstants.LEFT);
            doubleLabelPanel.add(right, c);
            return doubleLabelPanel;
        }

        doubleLabelPanel.add(right, c);
        return doubleLabelPanel;
    }

}
