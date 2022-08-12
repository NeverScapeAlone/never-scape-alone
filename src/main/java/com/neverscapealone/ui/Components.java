package com.neverscapealone.ui;

import net.runelite.client.ui.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Components {

    public JButton cleanJButton(Icon icon, String toolTip, ActionListener actionListener, int w, int h){
        JButton button = new JButton();
        button.setIcon(icon);
        button.setToolTipText(toolTip);
        button.setSize(w, h);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(actionListener);
        return button;
    }


    public JToggleButton matchHeaderToggle(Icon icon, String toolTip, Color color, ActionListener actionListener){
        JToggleButton headerToggleButton = new JToggleButton();
        headerToggleButton.setIcon(icon);
        headerToggleButton.setToolTipText(toolTip);
        headerToggleButton.setSelected(true);
        headerToggleButton.setBackground(color);
        headerToggleButton.addActionListener(actionListener);
        headerToggleButton.setSize(30, 30);
        return headerToggleButton;
    }

    public JPanel title(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
    }

    public JPanel header(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(FontManager.getRunescapeSmallFont());
        label_holder.add(label);
        return label_holder;
    }

    public JPanel instructionTitle(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setForeground(Color.green.darker());
        label_holder.add(label);
        return label_holder;
    }


}
