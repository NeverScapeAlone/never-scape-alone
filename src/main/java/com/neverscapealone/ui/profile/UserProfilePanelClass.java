package com.neverscapealone.ui.profile;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.enums.PlayerButtonOptionEnum;
import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.models.payload.matchdata.player.Player;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Base64;

import static com.neverscapealone.ui.NeverScapeAlonePanel.SUB_BACKGROUND_COLOR;

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
        returnToMatch.setBackground(SUB_BACKGROUND_COLOR);
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

    public JPanel userProfileIDBlock(Player player){
        JPanel profileID = new JPanel();
        profileID.setBorder(new EmptyBorder(5, 5, 5, 5));
        profileID.setBackground(SUB_BACKGROUND_COLOR);
        profileID.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        profileID.setLayout(new GridLayout(1,2));

        /// left side
        JPanel profileIDLeft = new JPanel();
        profileIDLeft.setBorder(new EmptyBorder(0, 0, 0, 0));
        profileIDLeft.setBackground(SUB_BACKGROUND_COLOR);
        profileIDLeft.setLayout(new GridBagLayout());

        GridBagConstraints cl = new GridBagConstraints();
        cl.weightx = 1;
        cl.fill = GridBagConstraints.EAST;
        cl.anchor = GridBagConstraints.WEST;
        cl.gridx = 0;
        cl.gridy = 0;

        profileIDLeft.add(new JLabel(Icons.VERIFIED_ICON), cl);
        /// right side

        JPanel profileIDRight = new JPanel();
        profileIDRight.setBorder(new EmptyBorder(0, 0, 0, 0));
        profileIDRight.setBackground(SUB_BACKGROUND_COLOR);
        profileIDRight.setLayout(new GridBagLayout());

        GridBagConstraints cr = new GridBagConstraints();
        cr.weightx = 1;
        cr.fill = GridBagConstraints.WEST;
        cr.anchor = GridBagConstraints.EAST;
        cr.gridx = 0;
        cr.gridy = 0;

        profileIDRight.add(new JLabel(player.getLogin()), cr);
        cr.gridy += 1;
        if (player.getDiscord() != null){
            byte[] decodedBytes = Base64.getDecoder().decode(player.getDiscord());
            String decodedUsername = new String(decodedBytes);
            profileIDRight.add(new JLabel(decodedUsername), cr);
        }

        profileID.add(profileIDLeft);
        profileID.add(profileIDRight);
        return profileID;
    }
}
