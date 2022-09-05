package com.neverscapealone.ui.profile;

import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.neverscapealone.ui.NeverScapeAlonePanel.BACKGROUND_COLOR;

public class UserProfilePanelClass {
    public JPanel userProfilePanel() {
        JPanel userProfilePanel = new JPanel();
        userProfilePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        userProfilePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 0;
        c.gridx = 0;
        userProfilePanel.add(new JPanel(), c);
        return userProfilePanel;
    }

    public JPanel returnToMatchButton(){
        JPanel returnToMatch = new JPanel();
        returnToMatch.setBorder(new EmptyBorder(0, 0, 0, 0));
        returnToMatch.setBackground(BACKGROUND_COLOR);
        returnToMatch.setLayout(new GridBagLayout());

        GridBagConstraints cd = new GridBagConstraints();
        cd.weightx = 1;
        cd.fill = GridBagConstraints.HORIZONTAL;
        cd.anchor = GridBagConstraints.CENTER;
        cd.gridx = 0;
        cd.gridy = 0;

        JButton button = new JButton();
        button.setIcon(Icons.NSA_ICON);
        button.setToolTipText("Return to Match");
        button.setText("Return to Match");
        button.setBackground(Color.GREEN.darker().darker());
        button.addActionListener(e -> NeverScapeAlonePanel.setRefreshView(e, PanelStateEnum.MATCH));

        returnToMatch.add(button, cd);
        return returnToMatch;
    }
}
