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

import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.enums.SoundPingEnum;
import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.models.soundping.SoundPing;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Components;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;

public class MatchPanelClass {

    public JPanel matchPanel() {
        JPanel matchPanel = new JPanel();
        matchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        matchPanel.add(Box.createVerticalStrut(4), c);
        c.gridy += 1;
        matchPanel.add(new JPanel(), c);
        return matchPanel;
    }

    public static JPanel headerMatchPanel(MatchData matchData){
        JPanel headerMatchPanel = new JPanel();
        headerMatchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        headerMatchPanel.setLayout(new BorderLayout());

        JPanel matchIDPanel = matchIDPanel(matchData);
        headerMatchPanel.add(matchIDPanel,BorderLayout.CENTER);
        JButton leaveMatch = Components.cleanJButton(Icons.LOGOUT_ICON, "Leave Match", MatchPanelClass::leaveMatch, 20, 20);
        headerMatchPanel.add(leaveMatch,BorderLayout.EAST);
        return headerMatchPanel;
    }

    public static void leaveMatch(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        Object[] options = {"Leave",
                "Stay"};
        int n = JOptionPane.showOptionDialog(frame,
                "Would you like to leave this match?",
                "Match Logout",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            NeverScapeAlonePanel.eventBus.post(new SoundPing().buildSound(SoundPingEnum.MATCH_LEAVE));
            NeverScapeAlonePanel.websocket.logoff("Exiting match");
            NeverScapeAlonePanel.setView(PanelStateEnum.HOME);
            NeverScapeAlonePanel.refreshView();
        }
    }

    public static JPanel matchIDPanel(MatchData matchdata){
        JPanel match_ID_panel = new JPanel();
        match_ID_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        match_ID_panel.setBackground(BACKGROUND_COLOR);
        match_ID_panel.setLayout(new GridBagLayout());
        match_ID_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints cm = new GridBagConstraints();
        cm.weightx = 1;
        cm.fill = GridBagConstraints.HORIZONTAL;
        cm.anchor = GridBagConstraints.CENTER;
        cm.gridx = 0;
        cm.gridy = 0;

        JLabel matchID = new JLabel("ID: "+matchdata.getId());
        matchID.setIcon(Icons.NSA_ICON);
        matchID.setToolTipText("Click to copy your match ID to clipboard!");
        if (matchdata.getIsPrivate()){
            matchID.setForeground(NOTIFIER_COLOR);
        } else {
            matchID.setForeground(HIGHLIGHT_COLOR);
        }

        matchID.setFont(FontManager.getRunescapeBoldFont());
        matchID.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                StringSelection selection = new StringSelection(matchdata.getId());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        match_ID_panel.add(matchID, cm);
        return match_ID_panel;
    }

}
