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

package com.neverscapealone.ui.header;

import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.WebLink;
import com.neverscapealone.ui.NeverScapeAlonePanel;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.BACKGROUND_COLOR;
import static com.neverscapealone.ui.utils.Components.cleanJButton;

public class HeaderPanelClass {
    public JPanel headerPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.LINE_END;
        c.anchor = GridBagConstraints.LINE_START;

        headerPanel.add(cleanJButton(Icons.TUTORIAL_ICON, "Open a basic plugin tutorial", NeverScapeAlonePlugin::startPluginTutorial, 16, 16), c);
        c.gridx +=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        headerPanel.add(linksPanel(),c);
        c.gridx +=1;
        c.fill = GridBagConstraints.LINE_START;
        c.anchor = GridBagConstraints.LINE_END;
        NeverScapeAlonePanel.homeButton = cleanJButton(Icons.HOME_ICON, "Head back to the Home Menu", NeverScapeAlonePlugin::switchToHomePanel, 16, 16);
        NeverScapeAlonePanel.homeButton.setDisabledIcon(Icons.HOME_DISABLED_ICON);
        headerPanel.add(NeverScapeAlonePanel.homeButton, c);

        return headerPanel;
    }

    public JPanel linksPanel(){
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        linksPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        linksPanel.setBackground(BACKGROUND_COLOR);
        for (WebLink w : WebLink.values()) {
            JLabel link = new JLabel(w.getImage());
            link.setToolTipText(w.getTooltip());
            link.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    LinkBrowser.browse(w.getLink());
                }
            });
            linksPanel.add(link);
        }
        return linksPanel;
    }
}

