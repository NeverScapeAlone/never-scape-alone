package com.neverscapealone.ui;

import com.neverscapealone.enums.AccountTypeSelectionEnum;
import com.neverscapealone.enums.ActivityReferenceEnum;
import com.neverscapealone.model.MatchData;
import com.neverscapealone.model.SearchMatchData;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

import static com.neverscapealone.ui.Components.convertNotes;
import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class ActivityPanel {

    public JPanel createCurrentActivityPanel(MatchData matchData){
        JPanel activityPanel = matchDataConversion(matchData);
        return activityPanel;
    }

    public JPanel createSearchMatchDataPanel(SearchMatchData matchData){
        JPanel activityPanel = matchDataConversion(matchData);
        return activityPanel;
    }

    public JPanel matchDataConversion(Object dataInput){
        if (dataInput instanceof MatchData){
            MatchData matchData = ((MatchData) dataInput);
            String activity = matchData.getActivity();
            boolean isPrivate = matchData.getIsPrivate();
            String ID = matchData.getId();
            String fcLeader = matchData.getPlayers().get(0).getLogin();
            String playerCount = String.valueOf(matchData.getPlayers().size());
            String memberCount = matchData.getPartyMembers();
            String experience = matchData.getRequirement().getExperience();
            String splitType = matchData.getRequirement().getSplitType();
            String accounts = matchData.getRequirement().getAccounts();
            String region = matchData.getRequirement().getRegions();
            String notes = matchData.getNotes();

            return createActivityPanel(activity,
                                        isPrivate,
                                        ID,
                                        null,
                                        fcLeader,
                                        playerCount,
                                        memberCount,
                                        experience,
                                        splitType,
                                        accounts,
                                        region,
                                        notes);

        }
        if (dataInput instanceof SearchMatchData){
            SearchMatchData searchMatchData = ((SearchMatchData) dataInput);
            String activity = searchMatchData.getActivity();
            boolean isPrivate = searchMatchData.getIsPrivate();
            String ID = searchMatchData.getId();
            String partyLeader = searchMatchData.getPartyLeader();
            String playerCount = searchMatchData.getPlayerCount();
            String memberCount = searchMatchData.getPartyMembers();
            String experience = searchMatchData.getExperience();
            String splitType = searchMatchData.getSplitType();
            String accounts = searchMatchData.getAccounts();
            String region = searchMatchData.getRegions();
            String notes = searchMatchData.getNotes();

            return createActivityPanel(activity,
                                        isPrivate,
                                        ID,
                                        partyLeader,
                                        null,
                                        playerCount,
                                        memberCount,
                                        experience,
                                        splitType,
                                        accounts,
                                        region,
                                        notes);
        }
        return null;
    }

    public JPanel createActivityPanel(String activity,
                                      boolean isPrivate,
                                      String ID,
                                      String partyLeader,
                                      String fcLeader,
                                      String playerCount,
                                      String memberCount,
                                      String experience,
                                      String splitType,
                                      String accounts,
                                      String region,
                                      String notes)
    {
        JPanel current_activity_panel = new JPanel();
        current_activity_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        current_activity_panel.setBackground(SUB_BACKGROUND_COLOR);
        current_activity_panel.setLayout(new GridBagLayout());
        current_activity_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints ca = new GridBagConstraints();
        ca.weightx = 1;
        ca.fill = GridBagConstraints.HORIZONTAL;
        ca.anchor = GridBagConstraints.CENTER;
        ca.gridx = 0;
        ca.gridy = 0;

        JPanel activityHeader = new JPanel();
        activityHeader.setBorder(new EmptyBorder(0, 0, 0, 0));
        activityHeader.setBackground(SUB_BACKGROUND_COLOR);
        activityHeader.setLayout(new GridBagLayout());
        GridBagConstraints ah = new GridBagConstraints();
        ah.weightx = 1;
        ah.fill = GridBagConstraints.HORIZONTAL;
        ah.anchor = GridBagConstraints.CENTER;
        ah.gridx = 0;
        ah.gridy = 0;

        ActivityReferenceEnum activityReferenceEnum = ActivityReferenceEnum.valueOf(activity);
        ImageIcon activity_icon = activityReferenceEnum.getIcon();
        String activity_name = activityReferenceEnum.getTooltip();

        JLabel match_title = new JLabel(activity_name);
        match_title.setIcon(activity_icon);
        match_title.setFont(FontManager.getRunescapeBoldFont());

        activityHeader.add(match_title, ah);
        ah.gridx = 1;

        JLabel privateLabel = new JLabel();
        if (isPrivate) {
            privateLabel.setText("Private");
            privateLabel.setIcon(Icons.PRIVATE_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_YELLOW);
        } else {
            privateLabel.setText("Public");
            privateLabel.setIcon(Icons.PUBLIC_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_GREEN);
        }
        privateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        privateLabel.setToolTipText("Match ID: " + ID);

        ah.anchor = GridBagConstraints.LINE_END;
        ah.fill = GridBagConstraints.LINE_END;
        activityHeader.add(privateLabel, ah);

        current_activity_panel.add(activityHeader, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(2), ca);
        ca.gridy += 1;

        if (partyLeader != null){
            JLabel party_leader = new JLabel(partyLeader);
            party_leader.setIcon(Icons.CROWN_ICON);
            party_leader.setToolTipText("Party Leader");
            current_activity_panel.add(party_leader, ca);
            ca.gridy += 1;

            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;
        }

        if (fcLeader != null){
            JLabel friends_chat_label = new JLabel("FC: \""+fcLeader+"\"");
            friends_chat_label.setIcon(Icons.CHAT);
            friends_chat_label.setToolTipText("Friend's Chat");
            current_activity_panel.add(friends_chat_label, ca);
            ca.gridy += 1;

            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;
        }

        JLabel player_count_label = new JLabel(playerCount+"/"+memberCount);
        player_count_label.setIcon(Icons.PLAYERS_ICON);
        player_count_label.setToolTipText("Players");
        current_activity_panel.add(player_count_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel experience_label = new JLabel(experience);
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText("Experience");
        current_activity_panel.add(experience_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel split_label = new JLabel(splitType);
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText("Loot Split");
        current_activity_panel.add(split_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        ImageIcon account_image = AccountTypeSelectionEnum.valueOf(accounts).getImage();
        JLabel accounts_label = new JLabel(accounts);
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText("Accounts");
        current_activity_panel.add(accounts_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel region_label = new JLabel(region);
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText("Match Region");
        current_activity_panel.add(region_label, ca);
        ca.gridy +=1;

        if (notes != null){
            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;

            String convertNotes = convertNotes(notes);
            JLabel notes_label = new JLabel(convertNotes);
            notes_label.setFont(FontManager.getRunescapeSmallFont());
            notes_label.setIcon(Icons.NOTES_ICON);
            current_activity_panel.add(notes_label, ca);
            ca.gridy += 1;
        }
        return current_activity_panel;
    }

}
