package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.AccountTypeSelection;
import com.neverscapealone.enums.ActivityReference;
import com.neverscapealone.enums.SearchMatchData;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static com.neverscapealone.ui.Components.convertNotes;
import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class SearchMatchDataPanel {
    public JPanel createSearchMatchDataPanel(NeverScapeAlonePlugin plugin, SearchMatchData match){
        JPanel sMatch = new JPanel();
        sMatch.setBorder(new EmptyBorder(5, 5, 5, 5));
        sMatch.setBackground(SUB_BACKGROUND_COLOR);
        sMatch.setLayout(new GridBagLayout());
        sMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints cMatch = new GridBagConstraints();
        cMatch.weightx = 1;
        cMatch.fill = GridBagConstraints.HORIZONTAL;
        cMatch.anchor = GridBagConstraints.CENTER;
        cMatch.gridx = 0;
        cMatch.gridy = 0;

        /// start match block
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

        String activity = match.getActivity();
        ActivityReference activityReference = ActivityReference.valueOf(activity);
        ImageIcon activity_icon = activityReference.getIcon();
        String activity_name = activityReference.getTooltip();

        JLabel match_title = new JLabel(activity_name);
        match_title.setIcon(activity_icon);
        match_title.setFont(FontManager.getRunescapeBoldFont());

        activityHeader.add(match_title, ah);
        ah.gridx = 1;

        JLabel privateLabel = new JLabel();
        if (match.getIsPrivate()) {
            privateLabel.setText("Private");
            privateLabel.setIcon(Icons.PRIVATE_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_YELLOW);
        } else {
            privateLabel.setText("Public");
            privateLabel.setIcon(Icons.PUBLIC_ICON);
            privateLabel.setForeground(COLOR_PLUGIN_GREEN);
        }
        privateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        privateLabel.setToolTipText("Match ID: " + match.getId());

        ah.anchor = GridBagConstraints.LINE_END;
        ah.fill = GridBagConstraints.LINE_END;
        activityHeader.add(privateLabel, ah);

        sMatch.add(activityHeader, cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(2), cMatch);
        cMatch.gridy += 1;

        String party_leader = match.getPartyLeader();
        JLabel partyLeader_label = new JLabel(party_leader);
        partyLeader_label.setIcon(Icons.CROWN_ICON);
        partyLeader_label.setToolTipText("The party leader");
        sMatch.add(partyLeader_label, cMatch);
        cMatch.gridy += 1;

        JLabel size_label = new JLabel(match.getPlayerCount()+"/"+match.getPartyMembers());
        size_label.setIcon(Icons.PLAYERS_ICON);
        size_label.setToolTipText("Players");
        sMatch.add(size_label, cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(1), cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(1), cMatch);
        cMatch.gridy += 1;

        JLabel experience_label = new JLabel(match.getExperience());
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText("Experience");
        sMatch.add(experience_label, cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(1), cMatch);
        cMatch.gridy += 1;

        JLabel split_label = new JLabel(match.getSplitType());
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText("Loot Split");
        sMatch.add(split_label, cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(1), cMatch);
        cMatch.gridy += 1;

        String account_string = match.getAccounts();
        ImageIcon account_image = AccountTypeSelection.valueOf(account_string).getImage();
        JLabel accounts_label = new JLabel(account_string);
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText("Accounts");
        sMatch.add(accounts_label, cMatch);
        cMatch.gridy += 1;

        sMatch.add(Box.createVerticalStrut(1), cMatch);
        cMatch.gridy += 1;

        JLabel region_label = new JLabel(match.getRegions());
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText("Match Region");
        sMatch.add(region_label, cMatch);
        cMatch.gridy += 1;

        if (match.getNotes() != null){
            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            String notes = convertNotes(match.getNotes());
            JLabel notes_label = new JLabel(notes);
            notes_label.setFont(FontManager.getRunescapeSmallFont());
            notes_label.setIcon(Icons.NOTES_ICON);
            sMatch.add(notes_label, cMatch);
            cMatch.gridy += 1;
        }

        int v = 0;
        if (match.getIsPrivate()) {
            v = 1;
        }
        sMatch.setName(match.getId() + ":" + v);
        sMatch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                String name = panel.getName();
                String[] name_split = name.split(":");
                if (Objects.equals(name_split[1], "1")) {
                    plugin.privateMatchPasscode(name_split[0]);
                } else {
                    plugin.publicMatchJoin(name_split[0]);
                }
            }
        });
        return sMatch;
    }



}
