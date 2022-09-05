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

package com.neverscapealone.ui.utils;

import com.neverscapealone.enums.HelpButtonSwitchEnum;
import net.runelite.client.ui.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.neverscapealone.ui.NeverScapeAlonePanel.BACKGROUND_COLOR;
import static com.neverscapealone.ui.NeverScapeAlonePanel.HIGHLIGHT_COLOR;

public class Components {

    public static JButton cleanJButton(Icon icon, String toolTip, ActionListener actionListener, int w, int h){
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

    public static JToggleButton matchHeaderToggle(Icon icon, String toolTip, Color color, ActionListener actionListener){
        JToggleButton headerToggleButton = new JToggleButton();
        headerToggleButton.setIcon(icon);
        headerToggleButton.setToolTipText(toolTip);
        headerToggleButton.setSelected(true);
        headerToggleButton.setBackground(color);
        headerToggleButton.addActionListener(actionListener);
        headerToggleButton.setSize(30, 30);
        return headerToggleButton;
    }

    public static JPanel title(String title_text, Color color) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        label_holder.setBackground(color);
        return label_holder;
    }

    public static JPanel header(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(FontManager.getRunescapeSmallFont());
        label_holder.add(label);
        return label_holder;
    }

    public static JPanel instructionTitle(String title_text, Color color) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setForeground(HIGHLIGHT_COLOR);
        label.setBackground(color);
        label_holder.add(label);
        return label_holder;
    }

    public static String convertNotes(String inputString){
        String output = "<html><p style=\"overflow-wrap: break-word;\">" + inputString + "</p></html>";
        return output;
    }
    public static JPanel subActivityPanel(int row, int column) {
        if (row == 0 || column == 0) {
            row = 5;
            column = 5;
        }
        JPanel subActivityPanel = new JPanel();
        subActivityPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        subActivityPanel.setBackground(BACKGROUND_COLOR);
        subActivityPanel.setLayout(new GridLayout(row, column));
        return subActivityPanel;
    }

    public static JPanel horizontalBar(int h, Color color){
        JPanel horizontalBar = new JPanel();
        horizontalBar.setBorder(new EmptyBorder(0, 0, 0, 0));
        horizontalBar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        horizontalBar.setBackground(color);
        horizontalBar.add(Box.createVerticalStrut(h));
        return horizontalBar;
    }

    public static void helpButtonSwitchboard(ActionEvent actionEvent, HelpButtonSwitchEnum helpButtonSwitchEnum) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        String message = "";
        switch (helpButtonSwitchEnum){
            case COUNT:
                message = "Group Size Help" + "\n" +
                        "The group size string indicates the maximum" + "\n"+
                        "number of players that your group should have.";
                break;
            case NOTES:
                message = "Notes Help" + "\n" +
                        "You're allowed 200 characters per notes section." + "\n"+
                        "The Notes description can be used to add key information" + "\n"+
                        "that other players should know about your group!" + "\n"+
                        "Note that offensive language will be censored, and you may" + "\n"+
                        "be banned from using the plugin on severe offenses." + "\n"+
                        "Please follow the Rules of RuneScape when describing your group.";
                break;
            case EXPERIENCE:
                message = "Experience Help" + "\n" +
                        "Different experience values can be used to indicate how competent your party is." + "\n" +
                        "Note: Anyone can join a party, regardless of the designation." + "\n" +
                        "1. Flexible - Anyone, of any experience level, can join." + "\n" +
                        "2. Novice - Those with some experience in the activity should join." + "\n" +
                        "3. Average - Those that see themselves as average in the activity, should join." + "\n" +
                        "4. Experienced - Those with plenty of experience should join in this activity.";
                break;
            case SPLIT:
                message = "Split Help" + "\n" +
                        "How you would like to split your loot." + "\n" +
                        "This can be ignored if you're doing an activity which doesn't require loot splitting." + "\n" +
                        "1. FFA - Free for all, everyone picks up their own loot." + "\n" +
                        "2. Splits - Everyone splits the loot evenly at the end.";
                break;
            case ACCOUNTS:
                message = "Account Help" + "\n" +
                        "The types of accounts that you would like to have in your party" + "\n" +
                        "Note: Anyone can join a party, regardless of the designation." + "\n" +
                        "1. All Accounts - Every account type should join." + "\n" +
                        "2. Normal - Accounts without special designations should join." + "\n" +
                        "3. IM - Regular Ironmen should join." + "\n" +
                        "4. HCIM - Hardcore Ironmen should join." + "\n" +
                        "5. UIM - Ultimate Ironmen should join." + "\n" +
                        "6. GIM - Group Ironmen should join." + "\n" +
                        "7. HCGIM - Hardcore Group Ironmen should join." + "\n" +
                        "8. UGIM - Unranked Group Ironmen should join.";
                break;
            case REGION:
                message = "Region Help" + "\n" +
                        "What region would like to set the group in." + "\n" +
                        "Note: Anyone can join a party, regardless of the designation.";
                break;
            case PASSCODE:
                message = "Passcode Help" + "\n" +
                        "Passcode to set for a private match." + "\n" +
                        "Leave the passcode field blank in order for your match to be public." + "\n" +
                        "-- Rules --" + "\n" +
                        "Any string of size <64 characters is allowed, with permitted characters: [A-Za-z0-9_- ]";
                break;
        }

        JOptionPane.showMessageDialog(frame, message);
    }

}
