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

package com.neverscapealone.ui.quick;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.ActivityReferenceEnum;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;
import static com.neverscapealone.ui.utils.Components.subActivityPanel;

public class QueuePanelClass {
    JPanel display = new JPanel();
    private final MaterialTabGroup QueuePanelTab = new MaterialTabGroup(display);
    public JPanel quickPanel(NeverScapeAlonePlugin plugin) {
        JPanel quickPanel = new JPanel();
        quickPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        quickPanel.setBackground(ALT_BACKGROUND);
        quickPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        quickPanel.add(Box.createVerticalStrut(4), c);
        c.gridy += 1;
        NeverScapeAlonePanel.quickMatchButton.setText("Join a Random Activity");
        NeverScapeAlonePanel.quickMatchButton.setToolTipText("Choose some activities!");
        NeverScapeAlonePanel.quickMatchButton.setBackground(IN_PROGRESS);
        NeverScapeAlonePanel.quickMatchButton.addActionListener(plugin::quickMatchQueueStart);
        quickPanel.add(NeverScapeAlonePanel.quickMatchButton, c);
        c.gridy += 1;

        MaterialTab skillsTab = new MaterialTab(Icons.ALL_SKILLS, QueuePanelTab, skillPanel);
        MaterialTab bossesTab = new MaterialTab(Icons.TZ_TOK_JAD, QueuePanelTab, bossPanel);
        MaterialTab raidsTab = new MaterialTab(Icons.COX, QueuePanelTab, raidPanel);
        MaterialTab minigamesTab = new MaterialTab(Icons.CASTLE_WARS, QueuePanelTab, minigamePanel);
        MaterialTab miscTab = new MaterialTab(Icons.PVP_GENERIC, QueuePanelTab, miscPanel);

        QueuePanelTab.addTab(skillsTab);
        QueuePanelTab.addTab(bossesTab);
        QueuePanelTab.addTab(raidsTab);
        QueuePanelTab.addTab(minigamesTab);
        QueuePanelTab.addTab(miscTab);
        QueuePanelTab.select(skillsTab);

        quickPanel.add(QueuePanelTab, c);
        c.gridy+=1;
        quickPanel.add(display, c);
        return quickPanel;
    }

    public void addQueueButtons() {
        ActivityReferenceEnum[] values = ActivityReferenceEnum.values();
        for (ActivityReferenceEnum value : values) {
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setBackground(ALT_BACKGROUND);
            button.setName(value.getLabel());
            button.addItemListener(this::activityButtonManager); // add function here
            activity_buttons.add(button);

            switch (value.getActivity()) {
                case "random":
                    randomPanel.add(button);
                    break;
                case "skill":
                    skillPanel.add(button);
                    break;
                case "boss":
                    bossPanel.add(button);
                    break;
                case "minigame":
                    minigamePanel.add(button);
                    break;
                case "raid":
                    raidPanel.add(button);
                    break;
                case "misc":
                    miscPanel.add(button);
            }
        }
    }

    private void activityButtonManager(ItemEvent itemEvent) {
        JToggleButton button = (JToggleButton) itemEvent.getItem();
        String selection = button.getName();

        if (button.isSelected()) {
            queue_list.add(selection);
        } else {
            queue_list.remove(selection);
        }

        if (queue_list.size() > 0) {
            quickMatchButton.setText("Start Queue");
            quickMatchButton.setBackground(HIGHLIGHT_COLOR);
        } else {
            quickMatchButton.setText("Join a Random Activity");
            quickMatchButton.setBackground(IN_PROGRESS);
        }
    }

    public void constructQueuePanels() {
        NeverScapeAlonePanel.randomPanel = subActivityPanel(1, 1);
        NeverScapeAlonePanel.skillPanel = subActivityPanel(4, 6);
        NeverScapeAlonePanel.bossPanel = subActivityPanel(7, 6);
        NeverScapeAlonePanel.raidPanel = subActivityPanel(2, 2);
        NeverScapeAlonePanel.minigamePanel = subActivityPanel(7, 6);
        NeverScapeAlonePanel.miscPanel = subActivityPanel(4, 4);
    }
}
