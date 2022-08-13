package com.neverscapealone.ui;

import com.neverscapealone.enums.WebLink;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.neverscapealone.ui.NeverScapeAlonePanel.SUB_BACKGROUND_COLOR;

public class LinksPanelClass {
    public JPanel linksPanel() {
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        linksPanel.setBackground(SUB_BACKGROUND_COLOR);
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
