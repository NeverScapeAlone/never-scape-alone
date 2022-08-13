package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.ActivityReferenceEnum;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

import static com.neverscapealone.ui.Components.subActivityPanel;
import static com.neverscapealone.ui.NeverScapeAlonePanel.*;
import static com.neverscapealone.ui.Components.*;

public class QueuePanelClass {
    public JPanel quickPanel(NeverScapeAlonePlugin plugin) {
        JPanel quickPanel = new JPanel();
        quickPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        quickPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        NeverScapeAlonePanel.quickMatchButton.setText("Select Activities");
        NeverScapeAlonePanel.quickMatchButton.setToolTipText("Choose activities to play!");
        NeverScapeAlonePanel.quickMatchButton.setBackground(COLOR_INPROGRESS);
        NeverScapeAlonePanel.quickMatchButton.addActionListener(plugin::quickMatchQueueStart);
        quickPanel.add(NeverScapeAlonePanel.quickMatchButton, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Random"), c);
        c.gridy += 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        quickPanel.add(randomPanel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Skills"), c);
        c.gridy += 1;
        quickPanel.add(skillPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Bosses"), c);
        c.gridy += 1;
        quickPanel.add(bossPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Raids"), c);
        c.gridy += 1;
        quickPanel.add(raidPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Mini-games"), c);
        c.gridy += 1;
        quickPanel.add(minigamePanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Miscellaneous"), c);
        c.gridy += 1;
        quickPanel.add(miscPanel, c);
        return quickPanel;
    }

    public void addQueueButtons() {
        ActivityReferenceEnum[] values = ActivityReferenceEnum.values();
        for (ActivityReferenceEnum value : values) {
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
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
            quickMatchButton.setBackground(COLOR_PLUGIN_GREEN);
        } else {
            quickMatchButton.setText("Select Activities");
            quickMatchButton.setBackground(COLOR_INPROGRESS);
        }
    }

    public void constructQueuePanels() {
        NeverScapeAlonePanel.randomPanel = subActivityPanel(1, 1);
        NeverScapeAlonePanel.skillPanel = subActivityPanel(4, 6);
        NeverScapeAlonePanel.bossPanel = subActivityPanel(7, 6);
        NeverScapeAlonePanel.raidPanel = subActivityPanel(2, 2);
        NeverScapeAlonePanel.minigamePanel = subActivityPanel(6, 6);
        NeverScapeAlonePanel.miscPanel = subActivityPanel(3, 5);
    }
}
