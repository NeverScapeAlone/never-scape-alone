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

package com.neverscapealone.ui.match;

import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.BACKGROUND_COLOR;

public class DiscordInvitePanelClass {
    public JPanel createDiscordInvitePanel(MatchData matchData){
        JPanel discord_invite_panel = new JPanel();
        discord_invite_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        discord_invite_panel.setBackground(BACKGROUND_COLOR);
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
                frame.setAlwaysOnTop(true);
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
