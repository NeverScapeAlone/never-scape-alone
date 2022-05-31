package com.neverscapealone.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.http.UnauthorizedTokenException;
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
    private static final Color SERVER_UNREACHABLE = ColorScheme.DARKER_GRAY_COLOR;
    private static final Color AUTH_FAILURE = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    private static final Color SERVER_ERROR = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    private static final Color SERVER_MAINTENANCE = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker();
    private static final Color CHECKING_SERVER = ColorScheme.GRAND_EXCHANGE_LIMIT;
    private static final Color SERVER_ONLINE = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();

    private static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    private static final Color LINK_HEADER_COLOR = ColorScheme.LIGHT_GRAY_COLOR;
    private static final Font NORMAL_FONT = FontManager.getRunescapeFont();
    private static final int SUB_PANEL_SEPARATION_HEIGHT = 10;

    private final JPanel linksPanel;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneClient client;
    private final Client user;
    private final NeverScapeAlonePlugin plugin;

    private JPanel serverPanel;

    @Getter
    @AllArgsConstructor
    public enum WebLink
    {
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter!", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone");

        private final ImageIcon image;
        private final String tooltip;
        private final String link;
    }

    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            EventBus eventBus, NeverScapeAloneClient client, Client user)
    {
        this.config = config;
        this.plugin = plugin;
        this.client = client;
        this.user = user;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        linksPanel = linksPanel();
        serverPanel = serverPanel();
        checkServer();

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
        serverPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        serverPanel.add(new JLabel());
        return serverPanel;
    }

    private void checkServer()
    {
        JLabel label = (JLabel)(serverPanel.getComponent(0));
        serverPanel.setBackground(CHECKING_SERVER);
        serverPanel.setToolTipText("Checking server for connectivity...");
        label.setText("CHECKING SERVER");

        String token = config.authToken();
        String login = "Ferrariic";

        client.requestServerStatus(login, token).whenCompleteAsync((status, ex) ->
                SwingUtilities.invokeLater(() ->
                {
                    if (status == null || ex != null)
                    {
                        serverPanel.setBackground(SERVER_ERROR);
                        label.setText("SERVER ERROR");
                        serverPanel.setToolTipText("There was a server error. Please contact support.");
                        return;
                    }

                    switch(status.getStatus()){
                        case ALIVE:
                            serverPanel.setBackground(SERVER_ONLINE);
                            label.setText("SERVER ONLINE");
                            serverPanel.setToolTipText("Server is Online. Authentication was successful.");
                            break;
                        case MAINTENANCE:
                            serverPanel.setBackground(SERVER_MAINTENANCE);
                            label.setText("SERVER MAINTENANCE");
                            serverPanel.setToolTipText("Server is undergoing Maintenance. Authentication was successful.");
                            break;
                        case UNREACHABLE:
                            serverPanel.setBackground(SERVER_UNREACHABLE);
                            label.setText("SERVER UNREACHABLE");
                            serverPanel.setToolTipText("Server is Unreachable. No connection could be made.");
                            break;
                        case AUTH_FAILURE:
                            serverPanel.setBackground(AUTH_FAILURE);
                            label.setText("AUTH FAILURE");
                            serverPanel.setToolTipText("Authentication failed. Please set a new token in the Plugin config.");
                    }
                }));
    }
}
