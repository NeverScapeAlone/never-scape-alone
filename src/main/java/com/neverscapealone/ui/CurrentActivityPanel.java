package com.neverscapealone.ui;

import com.google.common.base.Splitter;
import com.neverscapealone.enums.AccountTypeSelection;
import com.neverscapealone.enums.ActivityReference;
import com.neverscapealone.enums.MatchData;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

import static com.neverscapealone.ui.Components.convertNotes;
import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class CurrentActivityPanel {
    public JPanel createCurrentActivityPanel(MatchData matchData){
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

        String activity = matchData.getActivity();
        ActivityReference activityReference = ActivityReference.valueOf(activity);
        ImageIcon activity_icon = activityReference.getIcon();
        String activity_name = activityReference.getTooltip();

        JLabel match_title = new JLabel(activity_name);
        match_title.setIcon(activity_icon);
        match_title.setFont(FontManager.getRunescapeBoldFont());

        activityHeader.add(match_title, ah);
        ah.gridx = 1;

        JLabel privateLabel = new JLabel();
        if (matchData.getIsPrivate()) {
            privateLabel.setText("Private");
            privateLabel.setIcon(Icons.PRIVATE_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_YELLOW);
        } else {
            privateLabel.setText("Public");
            privateLabel.setIcon(Icons.PUBLIC_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_GREEN);
        }
        privateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        privateLabel.setToolTipText("Match ID: " + matchData.getId());

        ah.anchor = GridBagConstraints.LINE_END;
        ah.fill = GridBagConstraints.LINE_END;
        activityHeader.add(privateLabel, ah);

        current_activity_panel.add(activityHeader, ca);

        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(2), ca);
        ca.gridy += 1;

        JLabel friends_chat_label = new JLabel("FC: \""+matchData.getPlayers().get(0).getLogin()+"\"");
        friends_chat_label.setIcon(Icons.CHAT);
        friends_chat_label.setToolTipText("Friend's Chat");
        current_activity_panel.add(friends_chat_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel player_count_label = new JLabel(String.valueOf(matchData.getPlayers().size())+"/"+matchData.getPartyMembers());
        player_count_label.setIcon(Icons.PLAYERS_ICON);
        player_count_label.setToolTipText("Players");
        current_activity_panel.add(player_count_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel experience_label = new JLabel(matchData.getRequirement().getExperience());
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText("Experience");
        current_activity_panel.add(experience_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel split_label = new JLabel(matchData.getRequirement().getSplitType());
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText("Loot Split");
        current_activity_panel.add(split_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        String account_string = matchData.getRequirement().getAccounts();
        ImageIcon account_image = AccountTypeSelection.valueOf(account_string).getImage();
        JLabel accounts_label = new JLabel(account_string);
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText("Accounts");
        current_activity_panel.add(accounts_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel region_label = new JLabel(matchData.getRequirement().getRegions());
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText("Match Region");
        current_activity_panel.add(region_label, ca);
        ca.gridy +=1;

        if (matchData.getNotes() != null){
            current_activity_panel.add(Box.createVerticalStrut(1), ca);
            ca.gridy += 1;

            String notes = convertNotes(matchData.getNotes());
            JLabel notes_label = new JLabel(notes);
            notes_label.setFont(FontManager.getRunescapeSmallFont());
            notes_label.setIcon(Icons.NOTES_ICON);
            current_activity_panel.add(notes_label, ca);
            ca.gridy += 1;
        }
        return current_activity_panel;
    }

}
