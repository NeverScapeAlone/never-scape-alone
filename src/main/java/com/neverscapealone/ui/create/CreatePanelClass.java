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

package com.neverscapealone.ui.create;

import com.neverscapealone.enums.ActivityReferenceEnum;
import com.neverscapealone.enums.HelpButtonSwitchEnum;
import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.ui.utils.Components;
import com.neverscapealone.ui.utils.Icons;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.neverscapealone.NeverScapeAlonePlugin.panel;
import static com.neverscapealone.ui.utils.Components.*;
import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class CreatePanelClass {
    JPanel display = new JPanel();
    private final MaterialTabGroup CreatePanelTab = new MaterialTabGroup(display);

    public JPanel createPanel() {
        JPanel createPanel = new JPanel();
        createPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel.setLayout(new GridBagLayout());
        createPanel.setBackground(ALT_BACKGROUND);
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        createPanel.add(instructionTitle("Step 1: Select an Activity", ALT_BACKGROUND), c);
        c.gridy += 1;

        MaterialTab skillsTab = new MaterialTab(Icons.ALL_SKILLS, CreatePanelTab, createskillPanel);
        MaterialTab bossesTab = new MaterialTab(Icons.TZ_TOK_JAD, CreatePanelTab, createbossPanel);
        MaterialTab raidsTab = new MaterialTab(Icons.COX, CreatePanelTab, createraidPanel);
        MaterialTab minigamesTab = new MaterialTab(Icons.CASTLE_WARS, CreatePanelTab, createminigamePanel);
        MaterialTab miscTab = new MaterialTab(Icons.PVP_GENERIC, CreatePanelTab, createmiscPanel);

        CreatePanelTab.addTab(skillsTab);
        CreatePanelTab.addTab(bossesTab);
        CreatePanelTab.addTab(raidsTab);
        CreatePanelTab.addTab(minigamesTab);
        CreatePanelTab.addTab(miscTab);
        CreatePanelTab.select(skillsTab);

        createPanel.add(CreatePanelTab, c);
        c.gridy+=1;
        createPanel.add(display, c);

        return createPanel;
    }

    public JPanel createPanel2() {
        JPanel createPanel2 = new JPanel();
        createPanel2.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 3;

        JButton escape = cleanJButton(Icons.CANCEL_ICON, "Exit", e -> setRefreshView(e, PanelStateEnum.HOME), 20, 20);
        createPanel2.add(escape, c);

        c.gridx = 0;
        createPanel2.add(Components.instructionTitle("Step 2: Choose Requirements", ALT_BACKGROUND), c);

        c.gridy += 1;
        JPanel create_selection_panel = createSelectionPanel();
        createPanel2.add(create_selection_panel, c);

        c.gridy += 1;
        createPanel2.add(Box.createVerticalStrut(6), c);

        c.gridy += 1;
        JButton button_confirm = new JButton();
        button_confirm.setBackground(HIGHLIGHT_COLOR);
        button_confirm.setText("Create Group");
        button_confirm.setToolTipText("Click here to create a group with your current configuration!");
        button_confirm.setIcon(Icons.NSA_ICON);
        button_confirm.addActionListener(plugin::createMatchStart);
        createPanel2.add(button_confirm, c);

        return createPanel2;
    }

    public JPanel createSelectionPanel() {
        JPanel createSelectionPanel = new JPanel();
        createSelectionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createSelectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        createSelectionPanel.add(Components.header("Group Size"), c);
        c.gridx = 1;

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        spinnerPanel.setLayout(new GridBagLayout());
        GridBagConstraints sp = new GridBagConstraints();
        sp.weightx = 1;
        sp.fill = GridBagConstraints.HORIZONTAL;
        sp.anchor = GridBagConstraints.WEST;
        sp.gridy = 0;
        sp.gridx = 0;
        party_member_count.setFont(FontManager.getRunescapeFont());
        party_member_count.setToolTipText("Maximum party size");
        spinnerPanel.add(party_member_count, sp);

        createSelectionPanel.add(spinnerPanel, c);

        c.gridx = 2;
        c.weightx = 0;
        JButton member_count_help_button = Components.cleanJButton(Icons.HELP_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.COUNT), 16, 16);
        createSelectionPanel.add(member_count_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Experience"), c);
        c.gridx = 1;
        ((JLabel) experience_level.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(experience_level, c);

        c.gridx = 2;
        c.weightx = 0;
        JButton experience_help_button = Components.cleanJButton(Icons.EXPERIENCE_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.EXPERIENCE), 16, 16);
        createSelectionPanel.add(experience_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Split Type"), c);
        c.gridx = 1;
        ((JLabel) party_loot.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(party_loot, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton split_help_button = Components.cleanJButton(Icons.LOOTBAG_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.SPLIT), 16, 16);
        createSelectionPanel.add(split_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Accounts"), c);
        c.gridx = 1;
        ((JLabel) account_type.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(account_type, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton accounts_help_button = Components.cleanJButton(Icons.NSA_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.ACCOUNTS), 16, 16);
        createSelectionPanel.add(accounts_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Region"), c);
        c.gridx = 1;
        ((JLabel) region.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(region, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton region_help_button = Components.cleanJButton(Icons.WORLD_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.REGION), 16, 16);
        createSelectionPanel.add(region_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Passcode"), c);
        c.gridx = 1;
        passcode.setToolTipText("Leave blank for a Public match, DO NOT USE YOUR REAL PASSWORD");
        createSelectionPanel.add(passcode, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton passcode_help_button = Components.cleanJButton(Icons.PRIVATE_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.PASSCODE), 16, 16);
        createSelectionPanel.add(passcode_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(Components.header("Notes"), c);
        c.gridx = 1;
        notes.setToolTipText("Add some notes for your group! Let others know what you're looking for.");
        createSelectionPanel.add(notes, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton notes_help_button = Components.cleanJButton(Icons.NOTES_ICON, "Click here for help!", e -> helpButtonSwitchboard(e, HelpButtonSwitchEnum.NOTES), 16, 16);
        createSelectionPanel.add(notes_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        return createSelectionPanel;
    }

    public void addCreateButtons() {
        ActivityReferenceEnum[] values = ActivityReferenceEnum.values();
        for (ActivityReferenceEnum value : values) {
            JButton button = new JButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setBackground(ALT_BACKGROUND);
            button.setName(value.getLabel());
            button.addActionListener(this::create_activityButtonManager); // add function here
            create_activity_buttons.add(button);

            switch (value.getActivity()) {
                case "skill":
                    createskillPanel.add(button);
                    break;
                case "boss":
                    createbossPanel.add(button);
                    break;
                case "minigame":
                    createminigamePanel.add(button);
                    break;
                case "raid":
                    createraidPanel.add(button);
                    break;
                case "misc":
                    createmiscPanel.add(button);
            }
        }
    }

    public void create_activityButtonManager(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (object instanceof JButton) {
            step1_activity = ((JButton) object).getName();
            NeverScapeAlonePanel.setRefreshView(actionEvent, PanelStateEnum.CREATE_MATCH_PANEL);
        }
    }

    public void constructCreatePanels() {
        NeverScapeAlonePanel.createskillPanel = subActivityPanel(4, 6);
        NeverScapeAlonePanel.createbossPanel = subActivityPanel(7, 6);
        NeverScapeAlonePanel.createraidPanel = subActivityPanel(2, 2);
        NeverScapeAlonePanel.createminigamePanel = subActivityPanel(7, 6);
        NeverScapeAlonePanel.createmiscPanel = subActivityPanel(4, 4);
    }
}
