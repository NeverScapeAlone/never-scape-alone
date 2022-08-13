/*
 * Copyright (c) 2021, Ferrariic, Seltzer Bro, Cyborger1
 * Copyright (c) 2022, Ferrariic
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neverscapealone.ui;

import com.google.inject.Singleton;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.*;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.WorldService;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@Slf4j
@Singleton
public class NeverScapeAlonePanel extends PluginPanel {
    // COLOR SELECTIONS
    public static final Color COLOR_INPROGRESS = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker();
    public static final Color COLOR_PLUGIN_GREEN = Color.green.darker().darker();
    public static final Color COLOR_PLUGIN_YELLOW = Color.yellow.darker().darker();
    public static final Color COLOR_PLUGIN_RED = Color.red.darker();
    /// panel match statics
    public static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    public static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;

    // PANELS
    private final JPanel linksPanel;
    private final JPanel switchMenuPanel;
    private final JPanel connectingPanel;
    private final JPanel matchPanel;
    private final JPanel quickPanel;
    private final JPanel createPanel;
    private final JPanel createPanel2;
    private final JPanel searchPanel;
    private final JButton quickMatchButton = new JButton();
    private final JToggleButton quickMatchPanelButton = new JToggleButton();
    private final JToggleButton createMatchPanelButton = new JToggleButton();
    private final JToggleButton searchMatchPanelButton = new JToggleButton();
    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final EventBus eventBus;
    private final NeverScapeAloneConfig config;
    private final Components components;
    private final PlayerPanel playerPanel;
    private final SearchMatchDataPanel searchMatchDataPanel;
    private final CurrentActivityPanel currentActivityPanel;
    private final DiscordInvitePanel discordInvitePanel;
    private final NeverScapeAloneWebsocket websocket;
    private final Client user;
    private final WorldService worldService;
    // BUTTONS
    public IconTextField searchBar = new IconTextField();
    public ArrayList activity_buttons = new ArrayList<JToggleButton>();
    public ArrayList create_activity_buttons = new ArrayList<JToggleButton>();
    // GLOBAL VARIABLES
    public String step1_activity = "";
    private static boolean rating_selected = true;
    private static boolean discord_selected = true;
    private static boolean location_selected = true;
    private static boolean safety_selected = true;
    private static boolean stats_selected = true;
    public ArrayList<String> queue_list = new ArrayList<String>();
    public boolean isConnecting = false;

    SpinnerNumberModel player_size_model = new SpinnerNumberModel(2, 2, 1000, 1);
    public final JSpinner party_member_count = new JSpinner(player_size_model);
    public final JComboBox<String> experience_level = new JComboBox(new String[]{"Flexible", "Novice", "Average", "Experienced"});
    public final JComboBox<String> party_loot = new JComboBox(new String[]{"FFA", "Split"});
    public final JComboBox<String> account_type = new JComboBox(new String[]{"ANY", "NORMAL", "IM", "HCIM", "UIM", "GIM", "HCGIM", "UGIM"});
    public final JComboBox<String> region = new JComboBox(new String[]{"All Regions", "United States", "North Europe", "Central Europe", "Australia"});
    public final JTextField passcode = new JTextField();
    public final JTextField notes = new JTextField();

    @Inject
    ConfigManager configManager;
    private JPanel randomPanel;
    private JPanel skillPanel;
    private JPanel bossPanel;
    private JPanel raidPanel;
    private JPanel minigamePanel;
    private JPanel miscPanel;
    private JPanel serverWarningPanel;
    private JPanel createskillPanel;
    private JPanel createbossPanel;
    private JPanel createraidPanel;
    private JPanel createminigamePanel;
    private JPanel createmiscPanel;

    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            Components components,
            PlayerPanel playerPanel,
            SearchMatchDataPanel searchMatchDataPanel,
            CurrentActivityPanel currentActivityPanel,
            DiscordInvitePanel discordInvitePanel,
            EventBus eventBus,
            NeverScapeAloneWebsocket websocket,
            Client user,
            WorldService worldService) {
        this.config = config;
        this.plugin = plugin;
        this.components = components;
        this.playerPanel = playerPanel;
        this.searchMatchDataPanel = searchMatchDataPanel;
        this.currentActivityPanel = currentActivityPanel;
        this.discordInvitePanel = discordInvitePanel;
        this.websocket = websocket;
        this.user = user;
        this.eventBus = eventBus;
        this.worldService = worldService;

        this.eventBus.register(this);

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // panel inits
        linksPanel = linksPanel(); // add link panel perm
        serverWarningPanel = serverWarningPanel();
        serverWarningPanel.setVisible(false);
        // switch menu panel
        switchMenuPanel = switchMenuPanel();
        // connecting panel
        connectingPanel = connectingPanel();
        connectingPanel.setVisible(false);
        // match panel
        matchPanel = matchPanel();
        matchPanel.setVisible(false);
        // constructions
        constructQueuePanels(); // construct queue panels for placement in quick panel
        constructCreatePanels(); // construct create panels for placement in create panel
        // quick panel
        quickPanel = quickPanel();
        quickPanel.setVisible(true);
        quickMatchPanelButton.setSelected(true);
        // create panel
        createPanel = createPanel();
        createPanel.setVisible(false);
        createMatchPanelButton.setSelected(false);
        // create panel step 2
        createPanel2 = createPanel2();
        createPanel2.setVisible(false);
        // search panel
        searchPanel = searchPanel();
        searchPanel.setVisible(false);
        searchMatchPanelButton.setSelected(false);

        // ADD PANELS
        add(linksPanel);
        add(Box.createVerticalStrut(3));

        add(serverWarningPanel);

        add(switchMenuPanel);
        add(Box.createVerticalStrut(3));

        // add quick, create, and search panels -- these should never be visible when the other is visible. So no separator is required, held as a single group.
        add(quickPanel);
        add(createPanel);
        add(createPanel2);
        add(searchPanel);
        add(connectingPanel);
        add(matchPanel);

        // toss other builders here
        addQueueButtons();
        addCreateButtons();
    }

    @Subscribe
    public void onSearchMatches(SearchMatches searchMatches) {
        searchBar.setEditable(true);
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        SwingUtilities.invokeLater(() -> setSearchPanel(searchMatches));
        setServerWarningPanel("", false);
    }

    @Subscribe
    public void onServerMessage(ServerMessage serverMessage) {
        searchBar.setEditable(true);
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        String message = serverMessage.getServerMessage();
        setServerWarningPanel(message, true);
    }

    @Subscribe
    public void onMatchData(MatchData matchData) {
        SwingUtilities.invokeLater(() -> setMatchPanel(matchData));
        setServerWarningPanel("", false);
    }

    private JPanel serverWarningPanel() {
        JPanel serverWarningPanel = new JPanel();
        serverWarningPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        serverWarningPanel.setBackground(SUB_BACKGROUND_COLOR);
        serverWarningPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        serverWarningPanel.add(add(Box.createVerticalStrut(3)), c);
        c.gridy += 1;

        serverWarningPanel.add(new JLabel(), c);
        c.gridy += 1;

        serverWarningPanel.add(add(Box.createVerticalStrut(3)), c);
        return serverWarningPanel;
    }

    private void setServerWarningPanel(String message, boolean b) {
        JLabel label = (JLabel) serverWarningPanel.getComponent(1);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setToolTipText("Message from the server!");
        label.setIcon(Icons.NSA_ICON);
        label.setText(message);
        label.setForeground(Color.YELLOW);
        serverWarningPanel.setVisible(b);
    }

    private JPanel linksPanel() {
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

    private JPanel switchMenuPanel() {
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

        quickMatchPanelButton.setText("Quick");
        quickMatchPanelButton.setToolTipText("Quickly find a match");
        quickMatchPanelButton.addActionListener(this::quickPanelManager);
        switchMenuPanel.add(quickMatchPanelButton, c);
        c.gridx += 1;

        createMatchPanelButton.setText("Create");
        createMatchPanelButton.setToolTipText("Create a new match");
        createMatchPanelButton.addActionListener(this::createPanelManager);
        switchMenuPanel.add(createMatchPanelButton, c);
        c.gridx += 1;

        searchMatchPanelButton.setText("Search");
        searchMatchPanelButton.setToolTipText("Search for active matches");
        searchMatchPanelButton.addActionListener(this::searchPanelManager);
        switchMenuPanel.add(searchMatchPanelButton, c);

        return switchMenuPanel;
    }

    private void setMatchPanel(MatchData matchdata) {
        matchPanelManager(); // switch to match panel
        JPanel mp = (JPanel) matchPanel.getComponent(1);
        mp.removeAll();

        mp.setBorder(new EmptyBorder(0, 0, 0, 0));
        mp.setBackground(BACKGROUND_COLOR);
        mp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        mp.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        /// match ID panel

        JPanel match_ID_panel = new JPanel();
        match_ID_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        match_ID_panel.setBackground(SUB_BACKGROUND_COLOR);
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
        matchID.setToolTipText("Your match ID");
        if (matchdata.getIsPrivate()){
            matchID.setForeground(COLOR_PLUGIN_YELLOW);
        } else {
            matchID.setForeground(COLOR_PLUGIN_GREEN);
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
        mp.add(match_ID_panel, c);
        mp.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        JPanel current_activity_panel = currentActivityPanel.createCurrentActivityPanel(matchdata);
        c.gridy += 1;
        mp.add(current_activity_panel, c);

        if (matchdata.getDiscordInvite() != null){
            JPanel discord_invite_panel = discordInvitePanel.createDiscordInvitePanel(matchdata);
            c.gridy += 1;
            mp.add(discord_invite_panel, c);
        }

        c.gridy += 1;
        mp.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        for (Player player : matchdata.getPlayers()) {
            Integer matchPlayerSize = matchdata.getPlayers().size();
            if (matchPlayerSize > NeverScapeAlonePlugin.matchSize){
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.PLAYER_JOIN));
            } else if (matchPlayerSize < NeverScapeAlonePlugin.matchSize) {
                this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.PLAYER_LEAVE));
            };
            NeverScapeAlonePlugin.matchSize = matchPlayerSize;

            JPanel player_panel = playerPanel.createPlayerPanel(player,
                                                                plugin.username,
                                                                plugin,
                                                                rating_selected,
                                                                discord_selected,
                                                                safety_selected,
                                                                location_selected,
                                                                stats_selected);

            mp.add(player_panel, c);
            c.gridy += 1;
            mp.add(Box.createVerticalStrut(5), c);
            c.gridy += 1;
        }

        mp.revalidate();
        mp.repaint();
    }

    private JPanel matchPanel() {
        JPanel matchPanel = new JPanel();
        matchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        /// match header
        JPanel headermatchPanel = new JPanel();
        headermatchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        headermatchPanel.setLayout(new GridBagLayout());
        GridBagConstraints sc = new GridBagConstraints();

        sc.weightx = 1;
        sc.anchor = GridBagConstraints.LINE_END;
        sc.fill = GridBagConstraints.LINE_END;
        sc.gridx = 0;
        sc.gridy = 0;


        JToggleButton rating_button = components.matchHeaderToggle(Icons.RATING_ICON, "User Ratings", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.RATING));
        headermatchPanel.add(rating_button, sc);

        sc.gridx +=1;

        JToggleButton discord_button = components.matchHeaderToggle(Icons.DISCORD_ICON, "Discord Information", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.DISCORD));
        headermatchPanel.add(discord_button, sc);

        sc.gridx +=1;

        JToggleButton location_button = components.matchHeaderToggle(Icons.WORLD_ICON, "Location Information", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.LOCATION));
        headermatchPanel.add(location_button, sc);

        sc.gridx +=1;

        JToggleButton safety_button = components.matchHeaderToggle(Icons.SAFETY_ICON, "RuneWatch and WDR Safety", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.SAFETY));
        headermatchPanel.add(safety_button, sc);

        sc.gridx +=1;
        JToggleButton stats_button = components.matchHeaderToggle(Icons.HITPOINTS, "User Stats", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.STATS));
        headermatchPanel.add(stats_button, sc);

        sc.gridx +=1;
        JButton leaveMatch = components.cleanJButton(Icons.LOGOUT_ICON, "Leave Match", this::leaveMatch, 20, 20);
        headermatchPanel.add(leaveMatch, sc);

        matchPanel.add(headermatchPanel, c);
        c.gridy += 1;
        matchPanel.add(new JPanel(), c);
        return matchPanel;
    }

    private void switchHeaderButtonListener(ActionEvent actionEvent, MatchHeaderSwitchEnum matchHeaderSwitch){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        boolean b = true;
        if (button.isSelected()){
            button.setBackground(COLOR_PLUGIN_GREEN);
            b = true;
        } else {
            button.setBackground(COLOR_PLUGIN_RED);
            b = false;
        }
        switch(matchHeaderSwitch){
            case RATING:
                rating_selected = b;
                break;
            case STATS:
                stats_selected = b;
                break;
            case SAFETY:
                safety_selected = b;
                break;
            case DISCORD:
                discord_selected = b;
                break;
            case LOCATION:
                location_selected = b;
                break;
        }
    }

    private void leaveMatch(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            this.eventBus.post(new SoundPing().buildSound(SoundPingEnum.MATCH_LEAVE));
            websocket.logoff("Exiting match");
            quickPanelManager(actionEvent);
        }
    }

    private JPanel connectingPanel() {
        JPanel connectingPanel = new JPanel();
        connectingPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        connectingPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 0;
        c.gridy = 0;

        JButton escape = components.cleanJButton(Icons.CANCEL_ICON, "Exit", this::quickPanelManager, 20, 20);
        connectingPanel.add(escape, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy += 1;
        connectingPanel.add(components.title("Connecting..."), c);
        c.gridy += 1;
        connectingPanel.add(new JLabel("Queue Time: 00:00:00"), c);

        return connectingPanel;
    }

    public void setConnectingPanelQueueTime(String display_text) {
        JLabel label = (JLabel) (connectingPanel.getComponent(2));
        label.setText(display_text);
    }

    public void matchPanelManager() {
        NeverScapeAlonePlugin.cycleQueue = false;
        isConnecting = false;
        plugin.timer = 0;
        matchPanel.setVisible(true);
        switchMenuPanel.setVisible(false);
        connectingPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(false);
        quickMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    public void connectingPanelManager() {
        isConnecting = true;
        matchPanel.setVisible(false);
        switchMenuPanel.setVisible(false);
        connectingPanel.setVisible(true);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(false);
        quickMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    private void quickPanelManager(ActionEvent actionEvent) {
        NeverScapeAlonePlugin.cycleQueue = false;
        isConnecting = false;
        plugin.timer = 0;
        matchPanel.setVisible(false);
        switchMenuPanel.setVisible(true);
        connectingPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(true);
        searchPanel.setVisible(false);
        searchMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    private void createPanelManager(ActionEvent actionEvent) {
        isConnecting = false;
        NeverScapeAlonePlugin.cycleQueue = false;
        matchPanel.setVisible(false);
        switchMenuPanel.setVisible(true);
        connectingPanel.setVisible(false);
        createPanel.setVisible(true);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(false);
        quickMatchPanelButton.setSelected(false);
        searchMatchPanelButton.setSelected(false);
    }

    private void searchPanelManager(ActionEvent actionEvent) {
        isConnecting = false;
        NeverScapeAlonePlugin.cycleQueue = false;
        matchPanel.setVisible(false);
        switchMenuPanel.setVisible(true);
        connectingPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(true);
        quickMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    private JPanel quickPanel() {
        JPanel quickPanel = new JPanel();
        quickPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        quickPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        quickMatchButton.setText("Select Activities");
        quickMatchButton.setToolTipText("Choose activities to play!");
        quickMatchButton.setBackground(COLOR_INPROGRESS);
        quickMatchButton.addActionListener(plugin::quickMatchQueueStart);
        quickPanel.add(quickMatchButton, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Random"), c);
        c.gridy += 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        quickPanel.add(randomPanel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Skills"), c);
        c.gridy += 1;
        quickPanel.add(skillPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Bosses"), c);
        c.gridy += 1;
        quickPanel.add(bossPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Raids"), c);
        c.gridy += 1;
        quickPanel.add(raidPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Mini-games"), c);
        c.gridy += 1;
        quickPanel.add(minigamePanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(components.title("Miscellaneous"), c);
        c.gridy += 1;
        quickPanel.add(miscPanel, c);
        return quickPanel;
    }

    private JPanel subActivityPanel(int row, int column) {
        if (row == 0 || column == 0) {
            row = 5;
            column = 5;
        }
        JPanel subActivityPanel = new JPanel();
        subActivityPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        subActivityPanel.setBackground(SUB_BACKGROUND_COLOR);
        subActivityPanel.setLayout(new GridLayout(row, column));
        return subActivityPanel;
    }

    private void addQueueButtons() {
        ActivityReferenceEnum[] values = ActivityReferenceEnum.values();
        for (ActivityReferenceEnum value : values) {
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addItemListener(this::activityButtonManager); // add function here
            activity_buttons.add(button);

            switch (value.getActivity()) {
                case "random":
                    randomPanel.add(button);
                    break;
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

    private void activityButtonManager(ItemEvent itemEvent) {
        JToggleButton button = (JToggleButton) itemEvent.getItem();
        String selection = button.getName();

        if (button.isSelected()) {
            queue_list.add(selection);
        } else {
            queue_list.remove(selection);
        }

        if (queue_list.size() > 0) {
            quickMatchButton.setText("Start Queue");
            quickMatchButton.setBackground(COLOR_PLUGIN_GREEN);
        } else {
            quickMatchButton.setText("Select Activities");
            quickMatchButton.setBackground(COLOR_INPROGRESS);
        }
    }

    private void constructQueuePanels() {
        randomPanel = subActivityPanel(1, 1);
        skillPanel = subActivityPanel(4, 6);
        bossPanel = subActivityPanel(7, 6);
        raidPanel = subActivityPanel(2, 2);
        minigamePanel = subActivityPanel(6, 6);
        miscPanel = subActivityPanel(3, 5);
    }

    private void constructCreatePanels() {
        createskillPanel = subActivityPanel(4, 6);
        createbossPanel = subActivityPanel(7, 6);
        createraidPanel = subActivityPanel(2, 2);
        createminigamePanel = subActivityPanel(6, 6);
        createmiscPanel = subActivityPanel(3, 5);
    }

    private JPanel createPanel() {
        JPanel createPanel = new JPanel();
        createPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        createPanel.add(components.instructionTitle("Step 1: Select an Activity"), c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(components.title("Skills"), c);
        c.gridy += 1;
        createPanel.add(createskillPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(components.title("Bosses"), c);
        c.gridy += 1;
        createPanel.add(createbossPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(components.title("Raids"), c);
        c.gridy += 1;
        createPanel.add(createraidPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(components.title("Mini-games"), c);
        c.gridy += 1;
        createPanel.add(createminigamePanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(components.title("Miscellaneous"), c);
        c.gridy += 1;
        createPanel.add(createmiscPanel, c);

        return createPanel;
    }

    private void addCreateButtons() {
        ActivityReferenceEnum[] values = ActivityReferenceEnum.values();
        for (ActivityReferenceEnum value : values) {
            JButton button = new JButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addActionListener(this::create_activityButtonManager); // add function here
            create_activity_buttons.add(button);

            switch (value.getActivity()) {
                case "skill":
                    createskillPanel.add(button);
                    break;
                case "boss":
                    createbossPanel.add(button);
                    break;
                case "minigame":
                    createminigamePanel.add(button);
                    break;
                case "raid":
                    createraidPanel.add(button);
                    break;
                case "misc":
                    createmiscPanel.add(button);
            }
        }
    }

    private void create_activityButtonManager(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (object instanceof JButton) {
            step1_activity = ((JButton) object).getName();
            createPanel.setVisible(false);
            createPanel2.setVisible(true);
        }
    }

    private JPanel createPanel2() {
        JPanel createPanel2 = new JPanel();
        createPanel2.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        createPanel2.add(components.instructionTitle("Step 2: Choose Requirements"), c);

        c.gridy += 1;
        JPanel create_selection_panel = createSelectionPanel();
        createPanel2.add(create_selection_panel, c);

        c.gridy += 1;
        createPanel2.add(Box.createVerticalStrut(6), c);

        c.gridy += 1;
        JButton button_confirm = new JButton();
        button_confirm.setBackground(COLOR_PLUGIN_GREEN);
        button_confirm.setText("Create Group");
        button_confirm.setToolTipText("Click here to create a group with your current configuration!");
        button_confirm.setIcon(Icons.NSA_ICON);
        button_confirm.addActionListener(plugin::createMatchStart);
        createPanel2.add(button_confirm, c);

        return createPanel2;
    }

    private JPanel createSelectionPanel() {
        JPanel createSelectionPanel = new JPanel();
        createSelectionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createSelectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        createSelectionPanel.add(components.header("Group Size"), c);
        c.gridx = 1;

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        spinnerPanel.setLayout(new GridBagLayout());
        GridBagConstraints sp = new GridBagConstraints();
        sp.weightx = 1;
        sp.fill = GridBagConstraints.HORIZONTAL;
        sp.anchor = GridBagConstraints.WEST;
        sp.gridy = 0;
        sp.gridx = 0;
        party_member_count.setFont(FontManager.getRunescapeFont());
        party_member_count.setToolTipText("Maximum party size");
        spinnerPanel.add(party_member_count, sp);

        createSelectionPanel.add(spinnerPanel, c);

        c.gridx = 2;
        c.weightx = 0;
        JButton member_count_help_button = components.cleanJButton(Icons.HELP_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.COUNT), 16, 16);
        createSelectionPanel.add(member_count_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Experience"), c);
        c.gridx = 1;
        ((JLabel) experience_level.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(experience_level, c);

        c.gridx = 2;
        c.weightx = 0;
        JButton experience_help_button = components.cleanJButton(Icons.EXPERIENCE_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.EXPERIENCE), 16, 16);
        createSelectionPanel.add(experience_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Split Type"), c);
        c.gridx = 1;
        ((JLabel) party_loot.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(party_loot, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton split_help_button = components.cleanJButton(Icons.LOOTBAG_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.SPLIT), 16, 16);
        createSelectionPanel.add(split_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Accounts"), c);
        c.gridx = 1;
        ((JLabel) account_type.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(account_type, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton accounts_help_button = components.cleanJButton(Icons.NSA_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.ACCOUNTS), 16, 16);
        createSelectionPanel.add(accounts_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Region"), c);
        c.gridx = 1;
        ((JLabel) region.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(region, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton region_help_button = components.cleanJButton(Icons.WORLD_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.REGION), 16, 16);
        createSelectionPanel.add(region_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Passcode"), c);
        c.gridx = 1;
        passcode.setToolTipText("Leave blank for a Public match, DO NOT USE YOUR REAL PASSWORD");
        createSelectionPanel.add(passcode, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton passcode_help_button = components.cleanJButton(Icons.PRIVATE_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.PASSCODE), 16, 16);
        createSelectionPanel.add(passcode_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(components.header("Notes"), c);
        c.gridx = 1;
        notes.setToolTipText("Add some notes for your group! Let others know what you're looking for.");
        createSelectionPanel.add(notes, c);
        c.gridx = 2;
        c.weightx = 0;
        JButton notes_help_button = components.cleanJButton(Icons.NOTES_ICON, "Click here for help!", e -> components.help_button_switchboard(e, HelpButtonSwitchEnum.NOTES), 16, 16);
        createSelectionPanel.add(notes_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        return createSelectionPanel;
    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = 0;
        c.gridx = 0;

        JPanel activitySearchBar = activitySearchBar();
        searchPanel.add(activitySearchBar, c);
        c.gridy += 1;
        searchPanel.add(new JPanel(), c);
        return searchPanel;
    }

    public void setSearchPanel(SearchMatches searchMatches) {
        JPanel searchMatchPanel = (JPanel) searchPanel.getComponent(1);
        searchMatchPanel.removeAll();

        searchMatchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchMatchPanel.setBackground(BACKGROUND_COLOR);
        searchMatchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        searchMatchPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        if (searchMatches.getSearchMatches().size() == 0) {
            JPanel sMatch = new JPanel();
            sMatch.setBorder(new EmptyBorder(5, 5, 5, 5));
            sMatch.setBackground(SUB_BACKGROUND_COLOR);
            sMatch.setLayout(new GridBagLayout());
            sMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            GridBagConstraints cMatch = new GridBagConstraints();
            cMatch.weightx = 1;
            cMatch.fill = GridBagConstraints.HORIZONTAL;
            cMatch.anchor = GridBagConstraints.CENTER;
            cMatch.gridx = 0;
            cMatch.gridy = 0;

            JLabel no_matches_label = new JLabel("No Matches Found");
            no_matches_label.setForeground(COLOR_PLUGIN_RED);
            no_matches_label.setIcon(Icons.CANCEL_ICON);
            no_matches_label.setToolTipText("No matches found with this current search, try again!");
            no_matches_label.setFont(FontManager.getRunescapeBoldFont());

            searchMatchPanel.add(no_matches_label, c);
            return;
        }

        for (SearchMatchData match : searchMatches.getSearchMatches()) {
            JPanel sMatch = searchMatchDataPanel.createSearchMatchDataPanel(plugin, match);
            /// end match code
            c.gridy += 1;
            searchMatchPanel.add(Box.createVerticalStrut(5), c);
            c.gridy += 1;
            searchMatchPanel.add(sMatch, c);
        }

        searchMatchPanel.revalidate();
        searchMatchPanel.repaint();
    }

    private JPanel activitySearchBar() {
        JPanel searchbar_panel = new JPanel();
        searchbar_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        searchbar_panel.setBackground(BACKGROUND_COLOR);
        searchbar_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        searchBar.setBackground(SUB_BACKGROUND_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.setText("*");
        searchBar.addActionListener(plugin::searchActiveMatches);
        searchbar_panel.add(searchBar, c);

        return searchbar_panel;
    }


    @Getter
    @AllArgsConstructor
    public enum WebLink {
        DISCORD(Icons.DISCORD_ICON, "Join our Discord", "https://discord.gg/rs2AH3vnmf"),
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        PATREON(Icons.PATREON_ICON, "Support us through Patreon", "https://www.patreon.com/bot_detector"),
        PAYPAL(Icons.PAYPAL_ICON, "Support us through PayPal", "https://www.paypal.com/paypalme/osrsbotdetector"),
        BUG_REPORT_ICON(Icons.BUG_REPORT, "Submit a bug report here", "https://github.com/NeverScapeAlone/never-scape-alone/issues");

        private final ImageIcon image;
        private final String tooltip;
        private final String link;

    }
}