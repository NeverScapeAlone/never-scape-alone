package com.neverscapealone.ui;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class SearchPanelClass {
    public JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = 0;
        c.gridx = 0;

        JPanel activitySearchBar = activitySearchBar();
        searchPanel.add(activitySearchBar, c);
        c.gridy += 1;
        searchPanel.add(new JPanel(), c);
        return searchPanel;
    }
    private JPanel activitySearchBar() {
        JPanel searchbar_panel = new JPanel();
        searchbar_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        searchbar_panel.setBackground(BACKGROUND_COLOR);
        searchbar_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        searchBar.setBackground(SUB_BACKGROUND_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.setText("*");
        searchBar.addActionListener(plugin::searchActiveMatches);
        searchbar_panel.add(searchBar, c);

        return searchbar_panel;
    }
}
