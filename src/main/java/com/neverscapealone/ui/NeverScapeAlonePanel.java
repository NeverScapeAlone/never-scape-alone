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
import com.neverscapealone.enums.MatchHeaderSwitchEnum;
import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.enums.SoundPingEnum;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import com.neverscapealone.model.*;
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

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

import static com.neverscapealone.ui.ServerWarningPanelClass.serverWarningPanel;

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

    private final JPanel switchMenuPanel;
    public static JPanel connectingPanel;
    private final JPanel matchPanel;
    private final JPanel quickPanel;
    public static JPanel createPanel;
    public static JPanel createPanel2;
    private final JPanel searchPanel;
    public static JButton quickMatchButton = new JButton();
    private final JToggleButton quickMatchPanelButton = new JToggleButton();
    private final JToggleButton createMatchPanelButton = new JToggleButton();
    private final JToggleButton searchMatchPanelButton = new JToggleButton();
    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final EventBus eventBus;
    private final NeverScapeAloneConfig config;
    private final Components components;
    private final PlayerPanelClass playerPanelClass;
    private final ActivityPanelClass activityPanelClass;
    private final DiscordInvitePanelClass discordInvitePanelClass;
    private final CreatePanelClass createPanelClass;
    private final QueuePanelClass queuePanelClass;
    private final ConnectingPanelClass connectingPanelClass;
    private final LinksPanelClass linksPanelClass;
    private final NeverScapeAloneWebsocket websocket;
    private final Client user;
    private final WorldService worldService;
    // BUTTONS
    public static IconTextField searchBar = new IconTextField();
    public static ArrayList activity_buttons = new ArrayList<JToggleButton>();
    public static ArrayList create_activity_buttons = new ArrayList<JToggleButton>();
    // GLOBAL VARIABLES
    public static String step1_activity = "";
    private static boolean rating_selected = true;
    private static boolean discord_selected = true;
    private static boolean location_selected = true;
    private static boolean safety_selected = true;
    private static boolean stats_selected = true;
    public static ArrayList<String> queue_list = new ArrayList<String>();
    public boolean isConnecting = false;

    private static SpinnerNumberModel player_size_model = new SpinnerNumberModel(2, 2, 1000, 1);
    public static final JSpinner party_member_count = new JSpinner(player_size_model);
    public static final JComboBox<String> experience_level = new JComboBox(new String[]{"Flexible", "Novice", "Average", "Experienced"});
    public static final JComboBox<String> party_loot = new JComboBox(new String[]{"FFA", "Split"});
    public static final JComboBox<String> account_type = new JComboBox(new String[]{"ANY", "NORMAL", "IM", "HCIM", "UIM", "GIM", "HCGIM", "UGIM"});
    public static final JComboBox<String> region = new JComboBox(new String[]{"All Regions", "United States", "North Europe", "Central Europe", "Australia"});
    public static final JTextField passcode = new JTextField();
    public static final JTextField notes = new JTextField();

    @Inject
    ConfigManager configManager;
    public static JPanel randomPanel;
    public static JPanel skillPanel;
    public static JPanel bossPanel;
    public static JPanel raidPanel;
    public static JPanel minigamePanel;
    public static JPanel miscPanel;
    public static JPanel serverWarningPanel;
    public static JPanel createskillPanel;
    public static JPanel createbossPanel;
    public static JPanel createraidPanel;
    public static JPanel createminigamePanel;
    public static JPanel createmiscPanel;

    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            Components components,
            PlayerPanelClass playerPanelClass,
            ActivityPanelClass activityPanelClass,
            DiscordInvitePanelClass discordInvitePanelClass,
            CreatePanelClass createPanelClass,
            QueuePanelClass queuePanelClass,
            ConnectingPanelClass connectingPanelClass,
            LinksPanelClass linksPanelClass,
            EventBus eventBus,
            NeverScapeAloneWebsocket websocket,
            Client user,
            WorldService worldService) {
        this.config = config;
        this.plugin = plugin;
        this.components = components;
        this.playerPanelClass = playerPanelClass;
        this.activityPanelClass = activityPanelClass;
        this.discordInvitePanelClass = discordInvitePanelClass;
        this.createPanelClass = createPanelClass;
        this.queuePanelClass = queuePanelClass;
        this.connectingPanelClass = connectingPanelClass;
        this.linksPanelClass = linksPanelClass;
        this.websocket = websocket;
        this.user = user;
        this.eventBus = eventBus;
        this.worldService = worldService;

        this.eventBus.register(this);

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // panel inits
        // PANELS
        JPanel linksPanel = linksPanelClass.linksPanel(); // add link panel perm
        serverWarningPanel = serverWarningPanel();
        serverWarningPanel.setVisible(false);
        // switch menu panel
        switchMenuPanel = switchMenuPanel();
        // connecting panel
        connectingPanel = ConnectingPanelClass.connectingPanel();
        connectingPanel.setVisible(false);
        // match panel
        matchPanel = matchPanel();
        matchPanel.setVisible(false);
        // constructions
        queuePanelClass.constructQueuePanels(); // construct queue panels for placement in quick panel
        createPanelClass.constructCreatePanels(); // construct create panels for placement in create panel
        // quick panel
        quickPanel = queuePanelClass.quickPanel(plugin);
        quickPanel.setVisible(true);
        quickMatchPanelButton.setSelected(true);
        // create panel
        createPanel = createPanelClass.createPanel();
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
        queuePanelClass.addQueueButtons();
        createPanelClass.addCreateButtons();
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

    private void setServerWarningPanel(String message, boolean b) {
        JLabel label = (JLabel) serverWarningPanel.getComponent(1);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setToolTipText("Message from the server!");
        label.setIcon(Icons.NSA_ICON);
        label.setText(message);
        label.setForeground(Color.YELLOW);
        serverWarningPanel.setVisible(b);
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
        quickMatchPanelButton.addActionListener(e->panelStateManagerAction(e, PanelStateEnum.QUICK));
        switchMenuPanel.add(quickMatchPanelButton, c);
        c.gridx += 1;

        createMatchPanelButton.setText("Create");
        createMatchPanelButton.setToolTipText("Create a new match");
        createMatchPanelButton.addActionListener(e->panelStateManagerAction(e, PanelStateEnum.CREATE));
        switchMenuPanel.add(createMatchPanelButton, c);
        c.gridx += 1;

        searchMatchPanelButton.setText("Search");
        searchMatchPanelButton.setToolTipText("Search for active matches");
        searchMatchPanelButton.addActionListener(e->panelStateManagerAction(e, PanelStateEnum.SEARCH));
        switchMenuPanel.add(searchMatchPanelButton, c);

        return switchMenuPanel;
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


        JToggleButton rating_button = Components.matchHeaderToggle(Icons.RATING_ICON, "User Ratings", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.RATING));
        headermatchPanel.add(rating_button, sc);

        sc.gridx +=1;

        JToggleButton discord_button = Components.matchHeaderToggle(Icons.DISCORD_ICON, "Discord Information", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.DISCORD));
        headermatchPanel.add(discord_button, sc);

        sc.gridx +=1;

        JToggleButton location_button = Components.matchHeaderToggle(Icons.WORLD_ICON, "Location Information", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.LOCATION));
        headermatchPanel.add(location_button, sc);

        sc.gridx +=1;

        JToggleButton safety_button = Components.matchHeaderToggle(Icons.SAFETY_ICON, "RuneWatch and WDR Safety", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.SAFETY));
        headermatchPanel.add(safety_button, sc);

        sc.gridx +=1;
        JToggleButton stats_button = Components.matchHeaderToggle(Icons.HITPOINTS, "User Stats", COLOR_PLUGIN_GREEN, e->switchHeaderButtonListener(e, MatchHeaderSwitchEnum.STATS));
        headermatchPanel.add(stats_button, sc);

        sc.gridx +=1;
        JButton leaveMatch = Components.cleanJButton(Icons.LOGOUT_ICON, "Leave Match", this::leaveMatch, 20, 20);
        headermatchPanel.add(leaveMatch, sc);

        matchPanel.add(headermatchPanel, c);
        c.gridy += 1;
        matchPanel.add(new JPanel(), c);
        return matchPanel;
    }
    private void setMatchPanel(MatchData matchdata) {
        panelStateManager(PanelStateEnum.MATCH); // switch to match panel
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

        JPanel activity_panel = activityPanelClass.createCurrentActivityPanel(matchdata);
        c.gridy += 1;
        mp.add(activity_panel, c);

        if (matchdata.getDiscordInvite() != null){
            JPanel discord_invite_panel = discordInvitePanelClass.createDiscordInvitePanel(matchdata);
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

            JPanel player_panel = playerPanelClass.createPlayerPanel(player,
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
            panelStateManager(PanelStateEnum.QUICK);
        }
    }
    public void panelStateManagerAction(ActionEvent event, PanelStateEnum panelStateEnum){
        panelStateManager(panelStateEnum);
    }
    public void panelStateManager(PanelStateEnum panelStateEnum){
        switch(panelStateEnum){
            case MATCH:
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
                break;
            case QUICK:
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
                break;
            case CREATE:
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
                break;
            case SEARCH:
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
                break;
            case CONNECTING:
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
                break;
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

        createPanel2.add(Components.instructionTitle("Step 2: Choose Requirements"), c);

        c.gridy += 1;
        JPanel create_selection_panel = createPanelClass.createSelectionPanel();
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
            JPanel sMatch = activityPanelClass.createSearchMatchDataPanel(match);
            int v = 0;
            if (match.getIsPrivate()) {
                v = 1;
            }
            sMatch.setName(match.getId() + ":" + v);
            sMatch.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    String name = panel.getName();
                    String[] name_split = name.split(":");
                    if (Objects.equals(name_split[1], "1")) {
                        plugin.privateMatchPasscode(name_split[0]);
                    } else {
                        plugin.publicMatchJoin(name_split[0]);
                    }
                }
            });
            /// end match code
            c.gridy += 1;
            searchMatchPanel.add(Box.createVerticalStrut(5), c);
            c.gridy += 1;
            searchMatchPanel.add(sMatch, c);
        }

        searchMatchPanel.revalidate();
        searchMatchPanel.repaint();
    }

}