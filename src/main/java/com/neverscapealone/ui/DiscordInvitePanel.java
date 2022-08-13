package com.neverscapealone.ui;

import com.neverscapealone.enums.MatchData;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.SUB_BACKGROUND_COLOR;

public class DiscordInvitePanel {
    public JPanel createDiscordInvitePanel(MatchData matchData){
        JPanel discord_invite_panel = new JPanel();
        discord_invite_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        discord_invite_panel.setBackground(SUB_BACKGROUND_COLOR);
        discord_invite_panel.setLayout(new GridBagLayout());
        GridBagConstraints cd = new GridBagConstraints();
        cd.weightx = 1;
        cd.fill = GridBagConstraints.HORIZONTAL;
        cd.anchor = GridBagConstraints.CENTER;
        cd.gridx = 0;
        cd.gridy = 0;
        JButton discord_invite_button = new JButton();
        discord_invite_button.setIcon(Icons.DISCORD_ICON);
        discord_invite_button.setToolTipText("Join the group's discord!");
        discord_invite_button.setText("Join Discord");
        discord_invite_button.setBackground(new Color(114,137,218).darker().darker());
        discord_invite_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                String message = "Your microphone will be ON at the time of arrival.\n Please mute your microphone now, prior to joining.";
                String title = "NeverScapeAlone Discord Match ID: "+ matchData.getId();
                if (JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, Icons.DISCORD_ICON, new String[]{"JOIN","CANCEL"}, "JOIN") == JOptionPane.YES_OPTION){
                    LinkBrowser.browse(matchData.getDiscordInvite());
                };

            }
        });
        discord_invite_panel.add(discord_invite_button, cd);
        return discord_invite_panel;
    }
}
