package com.neverscapealone.ui.chat;

import com.neverscapealone.models.payload.chatdata.ChatData;
import com.neverscapealone.ui.utils.Icons;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.neverscapealone.ui.NeverScapeAlonePanel.*;
import static com.neverscapealone.ui.utils.Components.horizontalBar;

public class ChatPanelClass {
    public JPanel chatPanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        chatPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        chatPanel.add(Box.createVerticalStrut(4), c);
        c.gridy += 1;
        chatPanel.add(chatBar(),c);
        c.gridy += 1;
        chatPanel.add(Box.createVerticalStrut(2), c);
        c.gridy += 1;
        chatPanel.add(new JPanel(), c);
        return chatPanel;
    }

    private JPanel chatBar() {
        JPanel chatBarPanel = new JPanel();
        chatBarPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        chatBarPanel.setBackground(ALT_BACKGROUND);
        chatBarPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        chatBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        chatBar.setBackground(BACKGROUND_COLOR);
        chatBar.setIcon(Icons.CHAT);
        chatBar.setHoverBackgroundColor(BACKGROUND_COLOR);
        chatBar.setText("");
        chatBar.addActionListener(e -> plugin.sendChatMessage(e, chatBar.getText()));
        chatBarPanel.add(chatBar, c);

        c.gridy +=1;
        chatBarPanel.add(Box.createVerticalStrut(4), c);
        c.gridy +=1;
        chatBarPanel.add(horizontalBar(4, ACCENT_COLOR), c);

        return chatBarPanel;
    }

    public static JPanel drawMessage(ChatData chatData){
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        messagePanel.setBackground(ALT_BACKGROUND);
        BorderLayout c = new BorderLayout();
        c.setVgap(2);
        messagePanel.setLayout(c);

        JPanel chatInfo = drawChatUsername(chatData);
        JPanel chatText = drawChatText(chatData);
        messagePanel.add(chatInfo, BorderLayout.WEST);
        messagePanel.add(chatText, BorderLayout.CENTER);
        messagePanel.add(horizontalBar(1,ACCENT_COLOR), BorderLayout.SOUTH);
        return messagePanel;
    }

    public static JPanel drawChatUsername(ChatData chatData){
        JPanel chatUsernamePanel = new JPanel();
        chatUsernamePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        chatUsernamePanel.setBackground(ALT_BACKGROUND);
        chatUsernamePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 0;

        JLabel chatUsernameLabel = new JLabel(chatData.getUsername()+": ");
        chatUsernameLabel.setFont(FontManager.getRunescapeFont());
        chatUsernameLabel.setToolTipText(convertCurrentTimestamp(chatData.getTimestamp()));
        chatUsernamePanel.add(chatUsernameLabel, c);

        return chatUsernamePanel;
    }

    public static JPanel drawChatText(ChatData chatData){
        JPanel chatTextPanel = new JPanel();
        chatTextPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        chatTextPanel.setBackground(ALT_BACKGROUND);
        chatTextPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 0;
        c.gridy = 0;

        JTextArea chatMessage = new JTextArea(chatData.getMessage());
        chatMessage.setFont(FontManager.getRunescapeFont());
        chatMessage.setForeground(Color.YELLOW);
        chatMessage.setEditable(false);
        chatMessage.setLineWrap(true);
        chatMessage.setWrapStyleWord(true);
        chatTextPanel.add(chatMessage, c);
        return chatTextPanel;
    }
    public static String convertCurrentTimestamp(int time){
        Date date = new java.util.Date(time*1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String currentTime(int time){
        Date date = new java.util.Date(time*1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
