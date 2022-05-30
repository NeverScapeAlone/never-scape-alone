package com.neverscapealone.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.model.ServerStatus;
import net.runelite.api.Client;
import com.neverscapealone.http.NeverScapeAloneClient;
import com.neverscapealone.NeverScapeAlonePlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.LinkBrowser;

public class NeverScapeAlonePanel extends PluginPanel {
    private static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    private static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    private static final Color LINK_HEADER_COLOR = ColorScheme.LIGHT_GRAY_COLOR;
    private static final Font NORMAL_FONT = FontManager.getRunescapeFont();
    private static final int SUB_PANEL_SEPARATION_HEIGHT = 10;

    private final JPanel linksPanel;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneClient client;
    private final Client user;

    @Getter
    @AllArgsConstructor
    public enum WebLink
    {
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter!", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        ;

        private final ImageIcon image;
        private final String tooltip;
        private final String link;
    }

    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            EventBus eventBus, JPanel serverPanel, NeverScapeAloneConfig config1, NeverScapeAloneClient client, Client user)
    {
        this.config = config;
        this.client = client;
        this.user = user;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        linksPanel = linksPanel();
        serverPanel = serverPanel();

        add(linksPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(serverPanel);
    }



    private JPanel linksPanel()
    {
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        linksPanel.setBackground(SUB_BACKGROUND_COLOR);
        for (WebLink w : WebLink.values())
        {
            JLabel link = new JLabel(w.getImage());
            link.setToolTipText(w.getTooltip());
            link.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    LinkBrowser.browse(w.getLink());
                }
            });
            linksPanel.add(link);
        }
        return linksPanel;
    }

    private JPanel serverPanel()
    {
        JPanel serverPanel = new JPanel();
        JLabel title = new JLabel("Server Status:");
        title.setHorizontalAlignment(JLabel.LEFT);
        serverPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        serverPanel.setBackground(SUB_BACKGROUND_COLOR);
        serverPanel.add(title);

        String token = config.authToken();
        try {
            String login = user.getLocalPlayer().getName();
            System.out.println(token);
            System.out.println(login);
        } catch(Exception e) { }
        return serverPanel;
    }
}
