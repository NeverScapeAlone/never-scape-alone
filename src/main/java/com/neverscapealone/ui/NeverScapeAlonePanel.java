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
import com.neverscapealone.enums.PanelStateEnum;
import com.neverscapealone.enums.PlayerSelectionPanelEnum;
import com.neverscapealone.enums.SoundPingEnum;
import com.neverscapealone.models.payload.matchdata.MatchData;
import com.neverscapealone.models.payload.matchdata.player.Player;
import com.neverscapealone.models.payload.searchmatches.SearchMatchData;
import com.neverscapealone.models.payload.searchmatches.SearchMatches;
import com.neverscapealone.models.payload.servermessage.ServerMessage;
import com.neverscapealone.models.soundping.SoundPing;
import com.neverscapealone.socket.NeverScapeAloneWebsocket;
import com.neverscapealone.ui.connecting.ConnectingPanelClass;
import com.neverscapealone.ui.create.CreatePanelClass;
import com.neverscapealone.ui.header.HeaderPanelClass;
import com.neverscapealone.ui.match.ActivityPanelClass;
import com.neverscapealone.ui.match.DiscordInvitePanelClass;
import com.neverscapealone.ui.match.MatchPanelClass;
import com.neverscapealone.ui.match.PlayerPanelClass;
import com.neverscapealone.ui.quick.QueuePanelClass;
import com.neverscapealone.ui.search.SearchPanelClass;
import com.neverscapealone.ui.utils.Components;
import com.neverscapealone.ui.utils.Icons;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.game.WorldService;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.neverscapealone.ui.header.ServerWarningPanelClass.serverWarningPanel;
import static com.neverscapealone.ui.utils.Components.horizontalBar;

@Slf4j
@Singleton
public class NeverScapeAlonePanel extends PluginPanel {
    // COLOR SELECTIONS
    public static final Color IN_PROGRESS = new Color(144, 50, 61);
    public static final Color HIGHLIGHT_COLOR = new Color(95, 173, 86);
    public static final Color ALT_BACKGROUND = new Color(39, 39, 39);
    public static final Color BACKGROUND_COLOR = new Color(27, 32, 33);
    public static final Color ACCENT_COLOR = new Color(75, 88, 66);
    public static final Color WARNING_COLOR = new Color(191, 33, 30);
    public static final Color NOTIFIER_COLOR = new Color(226, 132, 19);
    public static JPanel connectingPanel;
    private static JPanel matchPanel;
    private static JPanel quickPanel;
    public static JPanel createPanel;
    public static JPanel createPanel2;
    private static JPanel searchPanel;
    public static JButton quickMatchButton = new JButton();
    // CLASSES
    public static NeverScapeAlonePlugin plugin;
    public static EventBus eventBus;
    private final NeverScapeAloneConfig config;
    private final Components components;
    private static PlayerPanelClass playerPanelClass;
    private static ActivityPanelClass activityPanelClass;
    private static DiscordInvitePanelClass discordInvitePanelClass;
    private final CreatePanelClass createPanelClass;
    public final QueuePanelClass queuePanelClass;
    public final ConnectingPanelClass connectingPanelClass;
    public final HeaderPanelClass linksPanelClass;
    public final MatchPanelClass matchPanelClass;
    public final SearchPanelClass searchPanelClass;
    public static NeverScapeAloneWebsocket websocket;
    public static SpriteManager spriteManager;
    public final ClientThread clientThread;
    private final Client user;
    private final WorldService worldService;
    public static IconTextField searchBar = new IconTextField();
    public static ArrayList activity_buttons = new ArrayList<JToggleButton>();
    public static ArrayList create_activity_buttons = new ArrayList<JToggleButton>();
    // GLOBAL VARIABLES
    public static String step1_activity = "";
    public static Player selectedUserProfileData = null;
    public static boolean rating_selected = true;
    public static boolean discord_selected = true;
    public static boolean location_selected = true;
    public static boolean safety_selected = true;
    public static boolean stats_selected = true;
    public static ArrayList<String> queue_list = new ArrayList<String>();
    public static boolean isConnecting = false;
    public static MatchData oldMatchData = null;
    public static SearchMatches oldSearchMatches = null;
    public static PanelStateEnum panelViewState = PanelStateEnum.HOME;

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
    // material tab groups
    private static final JPanel homeDisplay = new JPanel();
    private static final MaterialTabGroup mainTabs = new MaterialTabGroup(homeDisplay);
    private static final JPanel matchDisplay = new JPanel();
    private static final MaterialTabGroup matchTabs = new MaterialTabGroup(matchDisplay);
    public static HashMap<String, PlayerSelectionPanelEnum> playerSelectionPanelEnumHashMap = new HashMap<>();
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
                                HeaderPanelClass headerPanelClass,
                                MatchPanelClass matchPanelClass,
                                SearchPanelClass searchPanelClass,
                                ClientThread clientThread,
                                SpriteManager spriteManager,
                                EventBus eventBus,
                                NeverScapeAloneWebsocket websocket,
                                Client user,
                                WorldService worldService
                                )
    {
        this.config = config;
        NeverScapeAlonePanel.plugin = plugin;
        this.components = components;
        this.playerPanelClass = playerPanelClass;
        this.activityPanelClass = activityPanelClass;
        this.discordInvitePanelClass = discordInvitePanelClass;
        this.createPanelClass = createPanelClass;
        this.queuePanelClass = queuePanelClass;
        this.connectingPanelClass = connectingPanelClass;
        this.linksPanelClass = headerPanelClass;
        this.matchPanelClass = matchPanelClass;
        this.searchPanelClass = searchPanelClass;
        this.clientThread = clientThread;
        this.spriteManager = spriteManager;
        NeverScapeAlonePanel.websocket = websocket;
        NeverScapeAlonePanel.eventBus = eventBus;
        this.user = user;
        this.worldService = worldService;

        NeverScapeAlonePanel.eventBus.register(this);

        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel headerPanel = headerPanelClass.headerPanel();
        serverWarningPanel = serverWarningPanel();
        serverWarningPanel.setVisible(false);
        connectingPanel = ConnectingPanelClass.connectingPanel();
        connectingPanel.setVisible(false);
        matchPanel = matchPanelClass.matchPanel();
        queuePanelClass.constructQueuePanels();
        createPanelClass.constructCreatePanels();
        quickPanel = queuePanelClass.quickPanel(plugin);
        createPanel = createPanelClass.createPanel();
        createPanel2 = createPanelClass.createPanel2();
        createPanel2.setVisible(false);
        searchPanel = searchPanelClass.searchPanel();

        // ADD HEADERS
        add(headerPanel);
        add(serverWarningPanel);

        // ADD HOME TABS
        MaterialTab quickTab = new MaterialTab("Quick", mainTabs, quickPanel);
        MaterialTab createTab = new MaterialTab("Create", mainTabs, createPanel);
        MaterialTab searchTab = new MaterialTab("Search", mainTabs, searchPanel);
        mainTabs.addTab(quickTab);
        mainTabs.addTab(createTab);
        mainTabs.addTab(searchTab);
        mainTabs.select(quickTab);

        // ADD MATCH TABS
        MaterialTab matchTab = new MaterialTab("Match", matchTabs, matchPanel);
        MaterialTab chatTab = new MaterialTab("Chat", matchTabs, matchPanel); // replace with chat panel
        matchTabs.addTab(matchTab);
        matchTabs.addTab(chatTab);
        matchTabs.select(matchTab);
        matchTabs.setVisible(false);
        matchDisplay.setVisible(false);

        add(mainTabs);
        add(matchTabs);
        add(Box.createVerticalStrut(4));
        add(horizontalBar(4, ACCENT_COLOR));
        add(homeDisplay);
        add(matchDisplay);

        add(createPanel2);
        add(connectingPanel);

        queuePanelClass.addQueueButtons();
        createPanelClass.addCreateButtons();
    }

    @Subscribe
    public void onSearchMatches(SearchMatches searchMatches) {
        searchBar.setEditable(true);
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        oldSearchMatches = searchMatches;
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
        oldMatchData = matchData;
        SwingUtilities.invokeLater(() -> setMatchPanel(matchData));
        setServerWarningPanel("", false);
    }
    public static void refreshMatchPanel(){
        SwingUtilities.invokeLater(() -> setMatchPanel(oldMatchData));
    }
    public static void refreshSearchPanel(){
        SwingUtilities.invokeLater(() -> setSearchPanel(oldSearchMatches));
    }
    private void setServerWarningPanel(String message, boolean b) {
        JLabel label = (JLabel) serverWarningPanel.getComponent(1);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setToolTipText("Message from the server!");
        label.setIcon(Icons.NSA_ICON);
        label.setText(message);
        label.setForeground(NOTIFIER_COLOR);
        serverWarningPanel.setVisible(b);
    }
    public static void setSearchPanel(SearchMatches searchMatches) {
        JPanel searchMatchPanel = (JPanel) searchPanel.getComponent(1);
        searchMatchPanel.removeAll();

        searchMatchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchMatchPanel.setBackground(ALT_BACKGROUND);
        searchMatchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        searchMatchPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        if (searchMatches.getSearchMatches() == null) {
            JPanel sMatch = new JPanel();
            sMatch.setBorder(new EmptyBorder(5, 5, 5, 5));
            sMatch.setBackground(BACKGROUND_COLOR);
            sMatch.setLayout(new GridBagLayout());
            sMatch.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            GridBagConstraints cMatch = new GridBagConstraints();
            cMatch.weightx = 1;
            cMatch.fill = GridBagConstraints.HORIZONTAL;
            cMatch.anchor = GridBagConstraints.CENTER;
            cMatch.gridx = 0;
            cMatch.gridy = 0;

            JLabel no_matches_label = new JLabel("No Matches Found");
            no_matches_label.setForeground(WARNING_COLOR);
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
    public static void setMatchPanel(MatchData matchdata) {
        JPanel mp = (JPanel) matchPanel.getComponent(2);
        mp.removeAll();

        mp.setBorder(new EmptyBorder(0, 0, 0, 0));
        mp.setBackground(ALT_BACKGROUND);
        mp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        mp.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;

        JPanel match_ID_panel = MatchPanelClass.matchIDPanel(matchdata);
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
                NeverScapeAlonePanel.eventBus.post(new SoundPing().buildSound(SoundPingEnum.PLAYER_JOIN));
            } else if (matchPlayerSize < NeverScapeAlonePlugin.matchSize) {
                NeverScapeAlonePanel.eventBus.post(new SoundPing().buildSound(SoundPingEnum.PLAYER_LEAVE));
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
    public static void setRefreshView(ActionEvent event, PanelStateEnum panelStateEnum){
        setView(panelStateEnum);
        refreshView();
    }
    public static void setView(PanelStateEnum panelStateEnum){
        panelViewState = panelStateEnum;
    }
    public static void refreshView(){
        switch(panelViewState){
            case MATCH:
                turnOffAllPanels();
                startQueue(false);
                matchTabs.setVisible(true);
                matchDisplay.setVisible(true);
                matchPanel.setVisible(true);
                break;
            case HOME:
                turnOffAllPanels();
                startQueue(false);
                mainTabs.setVisible(true);
                homeDisplay.setVisible(true);
                quickPanel.setVisible(true);
                createPanel.setVisible(true);
                searchPanel.setVisible(true);
                break;
            case CREATE_MATCH_PANEL:
                turnOffAllPanels();
                startQueue(false);
                createPanel2.setVisible(true);
                break;
            case CONNECTING:
                turnOffAllPanels();
                startQueue(true);
                connectingPanel.setVisible(true);
                break;
        }
    }
    public static void turnOffAllPanels(){
        // tabs and displays
        mainTabs.setVisible(false);
        homeDisplay.setVisible(false);
        matchTabs.setVisible(false);
        matchDisplay.setVisible(false);
        // panels
        quickPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        searchPanel.setVisible(false);
        connectingPanel.setVisible(false);
    }
    public static void startQueue(boolean b){
        if (b){
            NeverScapeAlonePlugin.cycleQueue = false;
            isConnecting = false;
            plugin.timer = 0;
        } else {
            isConnecting = true;
        }
    }
}