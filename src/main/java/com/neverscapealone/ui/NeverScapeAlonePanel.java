package com.neverscapealone.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.*;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.game.WorldService;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.LinkBrowser;
import net.runelite.http.api.worlds.World;
import org.apache.commons.text.WordUtils;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NeverScapeAlonePanel extends PluginPanel {

    @Inject
    ConfigManager configManager;

    // COLOR SELECTIONS

    public static final Color SERVER_SIDE_ERROR = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    public static final Color CLIENT_SIDE_ERROR = Color.MAGENTA.darker().darker().darker();
    public static final Color COLOR_DISABLED = ColorScheme.DARKER_GRAY_COLOR;
    public static final Color COLOR_INFO = ColorScheme.GRAND_EXCHANGE_LIMIT.darker().darker().darker();
    public static final Color COLOR_WARNING = ColorScheme.GRAND_EXCHANGE_ALCH.darker().darker().darker();
    public static final Color COLOR_COMPLETED = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();
    public static final Color COLOR_INPROGRESS = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker().darker();

    /// panel match statics
    public static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    public static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    public static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    // PANELS
    private final JPanel linksPanel;
    private final JPanel switchMenuPanel;
    private JPanel skillPanel;
    private JPanel bossPanel;
    private JPanel raidPanel;
    private JPanel minigamePanel;
    private JPanel miscPanel;
    private JPanel quickPanel;

    // BUTTONS
    private final JButton quickMatchButton = new JButton();
    private final JButton searchMatchButton = new JButton();
    public ArrayList activity_buttons = new ArrayList<JToggleButton>();

    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneWebsocket websocket;
    private final Client user;

    private final WorldService worldService;

    @Getter
    @AllArgsConstructor
    public enum WebLink
    {
        DISCORD(Icons.DISCORD_ICON, "Join our Discord","https://discord.gg/rs2AH3vnmf"),
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        PATREON(Icons.PATREON_ICON, "Support us through Patreon","https://www.patreon.com/bot_detector"),
        PAYPAL(Icons.PAYPAL_ICON, "Support us through PayPal","https://www.paypal.com/paypalme/osrsbotdetector"),
        ETH_ICON(Icons.ETH_ICON, "Support us with Ethereum, you will be sent to our Github", "https://github.com/NeverScapeAlone"),
        BTC_ICON(Icons.BTC_ICON, "Support us with Bitcoin,  you will be sent to our Github","https://github.com/NeverScapeAlone"),
        BUG_REPORT_ICON(Icons.BUG_REPORT, "Submit a bug report here","https://github.com/NeverScapeAlone/never-scape-alone/issues");

        private final ImageIcon image;
        private final String tooltip;
        private final String link;

    }


    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            EventBus eventBus,
            NeverScapeAloneWebsocket websocket,
            Client user,
            WorldService worldService)
    {
        this.config = config;
        this.plugin = plugin;
        this.websocket = websocket;
        this.user = user;
        this.worldService = worldService;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // panel inits
        linksPanel = linksPanel(); // add link panel perm
        switchMenuPanel = switchMenuPanel(); // add switch menu panel perm
        constructQueuePanels(); // construct queue panels for placement in quick panel
        quickPanel = quickPanel();
        quickPanel.setVisible(true);


        // ADD PANELS
        add(linksPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));

        add(switchMenuPanel);
        add(Box.createVerticalStrut(5));

        add(quickPanel);

        addQueueButtons();
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

    private JPanel switchMenuPanel(){
        JPanel switchMenuPanel = new JPanel();
        switchMenuPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        switchMenuPanel.setBackground(SUB_BACKGROUND_COLOR);
        switchMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;

        quickMatchButton.setText("Quick Match");
        quickMatchButton.setToolTipText("Find a match by queueing");
        quickMatchButton.addActionListener(e -> quickPanelManager(e));
        switchMenuPanel.add(quickMatchButton, c);

        c.gridx = 1;
        searchMatchButton.setText("Search Match");
        searchMatchButton.setToolTipText("Search for active matches");
        searchMatchButton.addActionListener(e -> searchPanelManager(e));
        switchMenuPanel.add(searchMatchButton, c);

        return switchMenuPanel;
    }

    private void quickPanelManager(ActionEvent actionEvent){
        System.out.println("quick panel pressed");
        quickPanel.setVisible(true);
    }

    private void searchPanelManager(ActionEvent actionEvent){
        quickPanel.setVisible(false);
    }

    private JPanel quickPanel(){
        JPanel quickPanel = new JPanel();
        quickPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        quickPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Skills"), c);
        c.gridy += 1;
        quickPanel.add(skillPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Bosses"), c);
        c.gridy += 1;
        quickPanel.add(bossPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Raids"), c);
        c.gridy += 1;
        quickPanel.add(raidPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Mini-games"), c);
        c.gridy += 1;
        quickPanel.add(minigamePanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Miscellaneous"), c);
        c.gridy += 1;
        quickPanel.add(miscPanel, c);
        return quickPanel;
    }

    private JPanel queuePanel(int row, int column){
        if (row==0 || column==0){
            row=5;
            column=5;
        }
        JPanel queuePanel = new JPanel();
        queuePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        queuePanel.setBackground(SUB_BACKGROUND_COLOR);
        queuePanel.setLayout(new GridLayout(row, column));
        return queuePanel;
    }

    private void addQueueButtons(){
        ActivityReference[] values = ActivityReference.values();
        for(ActivityReference value: values) {
            // button construction
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addItemListener(e -> {}); // add function here
            activity_buttons.add(button);

            switch(value.getActivity()){
                case "skill":
                    skillPanel.add(button);
                    break;
                case "boss":
                    bossPanel.add(button);
                    break;
                case "minigame":
                    minigamePanel.add(button);
                    break;
                case "raid":
                    raidPanel.add(button);
                    break;
                case "misc":
                    miscPanel.add(button);
            }
        }
    }
    private void constructQueuePanels(){
        skillPanel = queuePanel(4,6);
        bossPanel = queuePanel(7,6);
        raidPanel = queuePanel(2,2);
        minigamePanel = queuePanel(6,6);
        miscPanel = queuePanel(1,3);
    }

    private JPanel title(String title_text){
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
    }
}