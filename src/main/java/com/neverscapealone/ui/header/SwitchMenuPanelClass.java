package com.neverscapealone.ui.header;

import com.neverscapealone.enums.PanelStateEnum;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class SwitchMenuPanelClass {
    public JPanel switchMenuPanel() {
        JPanel switchMenuPanel = new JPanel();
        switchMenuPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        switchMenuPanel.setBackground(SUB_BACKGROUND_COLOR);
        switchMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;

        quickMatchPanelButton.setText("Quick");
        quickMatchPanelButton.setToolTipText("Quickly find a match");
        quickMatchPanelButton.addActionListener(e-> setRefreshView(e, PanelStateEnum.QUICK));
        switchMenuPanel.add(quickMatchPanelButton, c);
        c.gridx += 1;

        createMatchPanelButton.setText("Create");
        createMatchPanelButton.setToolTipText("Create a new match");
        createMatchPanelButton.addActionListener(e-> setRefreshView(e, PanelStateEnum.CREATE));
        switchMenuPanel.add(createMatchPanelButton, c);
        c.gridx += 1;

        searchMatchPanelButton.setText("Search");
        searchMatchPanelButton.setToolTipText("Search for active matches");
        searchMatchPanelButton.addActionListener(e-> setRefreshView(e, PanelStateEnum.SEARCH));
        switchMenuPanel.add(searchMatchPanelButton, c);

        return switchMenuPanel;
    }
}
