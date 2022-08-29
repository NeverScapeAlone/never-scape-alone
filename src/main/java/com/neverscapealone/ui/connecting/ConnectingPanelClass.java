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

package com.neverscapealone.ui.connecting;

import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.neverscapealone.NeverScapeAlonePlugin.panel;
import static com.neverscapealone.ui.utils.Components.cleanJButton;
import static com.neverscapealone.ui.utils.Components.title;
import static com.neverscapealone.ui.NeverScapeAlonePanel.connectingPanel;

public class ConnectingPanelClass {

    public static JPanel connectingPanel() {
        JPanel connectingPanel = new JPanel();
        connectingPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        connectingPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 0;
        c.gridy = 0;

        JButton escape = cleanJButton(Icons.CANCEL_ICON, "Exit", e -> panel.panelStateManagerAction(e, PanelStateEnum.QUICK), 20, 20);
        connectingPanel.add(escape, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy += 1;
        connectingPanel.add(title("Connecting..."), c);
        c.gridy += 1;
        connectingPanel.add(new JLabel("Queue Time: 00:00:00"), c);

        return connectingPanel;
    }

    public static void setConnectingPanelQueueTime(String display_text) {
        JLabel label = (JLabel) (connectingPanel.getComponent(2));
        label.setText(display_text);
    }

}
