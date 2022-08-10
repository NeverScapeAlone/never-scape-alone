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
import java.util.Map;
import java.util.Objects;

@Slf4j
@Singleton
public class NeverScapeAlonePanel extends PluginPanel {

    public static final Color SERVER_SIDE_ERROR = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();

    // COLOR SELECTIONS
    public static final Color CLIENT_SIDE_ERROR = Color.MAGENTA.darker().darker().darker();
    public static final Color COLOR_DISABLED = ColorScheme.DARKER_GRAY_COLOR;
    public static final Color COLOR_INFO = ColorScheme.GRAND_EXCHANGE_LIMIT.darker().darker().darker();
    public static final Color COLOR_WARNING = ColorScheme.GRAND_EXCHANGE_ALCH.darker().darker().darker();
    public static final Color COLOR_COMPLETED = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();
    public static final Color COLOR_INPROGRESS = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker();
    /// panel match statics
    public static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    public static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    public static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    public JButton profile_player = new JButton();
    public JButton promote_party_leader = new JButton();;
    public JButton favorite = new JButton();;
    public JButton dislike_button = new JButton();;
    public JButton like_button = new JButton();;
    public JButton kick = new JButton();;
    public final JButton member_count_help_button = new JButton();
    public final JButton experience_help_button = new JButton();
    public final JButton split_help_button = new JButton();
    public final JButton accounts_help_button = new JButton();
    public final JButton region_help_button = new JButton();
    public final JButton passcode_help_button = new JButton();
    SpinnerNumberModel player_size_model = new SpinnerNumberModel(2, 2, 1000, 1);
    public final JSpinner party_member_count = new JSpinner(player_size_model);
    public final JComboBox<String> experience_level = new JComboBox(new String[]{"Flexible", "Novice", "Average", "Experienced"});
    public final JComboBox<String> party_loot = new JComboBox(new String[]{"FFA", "Split"});
    public final JComboBox<String> account_type = new JComboBox(new String[]{"ANY", "NORMAL", "IM", "HCIM", "UIM", "GIM", "HCGIM", "UGIM"});
    public final JComboBox<String> region = new JComboBox(new String[]{"All Regions", "United States", "North Europe", "Central Europe", "Australia"});
    public final JTextField passcode = new JTextField();
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
    private final NeverScapeAloneWebsocket websocket;
    private final Client user;
    private final WorldService worldService;
    // BUTTONS
    public IconTextField searchBar = new IconTextField();
    public ArrayList activity_buttons = new ArrayList<JToggleButton>();
    public ArrayList create_activity_buttons = new ArrayList<JToggleButton>();
    // GLOBAL VARIABLES
    public String step1_activity = "";

    // region references
    Map<Integer, String> regionReference = RegionName.regionReference();

    private static boolean rating_selected = true;
    private static boolean discord_selected = true;
    private static boolean location_selected = true;
    private static boolean safety_selected = true;
    private static boolean stats_selected = true;
    public ArrayList<String> queue_list = new ArrayList<String>();
    public boolean isConnecting = false;
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
            EventBus eventBus,
            NeverScapeAloneWebsocket websocket,
            Client user,
            WorldService worldService) {
        this.config = config;
        this.plugin = plugin;
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
            matchID.setForeground(Color.yellow.darker().darker());
        } else {
            matchID.setForeground(Color.green.darker().darker());
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

        /// current activity panel
        JPanel current_activity_panel = new JPanel();
        current_activity_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        current_activity_panel.setBackground(SUB_BACKGROUND_COLOR);
        current_activity_panel.setLayout(new GridBagLayout());
        current_activity_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints ca = new GridBagConstraints();
        ca.weightx = 1;
        ca.fill = GridBagConstraints.HORIZONTAL;
        ca.anchor = GridBagConstraints.CENTER;
        ca.gridx = 0;
        ca.gridy = 0;

        String activity = matchdata.getActivity();
        ActivityReference activityReference = ActivityReference.valueOf(activity);
        ImageIcon activity_icon = activityReference.getIcon();
        String activity_name = activityReference.getTooltip();

        JLabel match_title = new JLabel(activity_name);
        match_title.setIcon(activity_icon);
        match_title.setFont(FontManager.getRunescapeBoldFont());
        current_activity_panel.add(match_title, ca);
        ca.gridx = 1;

        JLabel privateLabel = new JLabel();
        if (matchdata.getIsPrivate()) {
            privateLabel.setText("Private");
            privateLabel.setIcon(Icons.PRIVATE_ICON);
            privateLabel.setForeground(Color.yellow.darker().darker());
        } else {
            privateLabel.setText("Public");
            privateLabel.setIcon(Icons.PUBLIC_ICON);
            privateLabel.setForeground(Color.green.darker().darker());
        }
        privateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        privateLabel.setToolTipText("Match ID: " + matchdata.getId());

        ca.anchor = GridBagConstraints.LINE_END;
        ca.fill = GridBagConstraints.LINE_END;
        current_activity_panel.add(privateLabel, ca);

        ca.anchor = GridBagConstraints.CENTER;
        ca.fill = GridBagConstraints.HORIZONTAL;
        ca.gridx = 0;
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(2), ca);
        ca.gridy += 1;

        JLabel friends_chat_label = new JLabel("FC: \""+matchdata.getPlayers().get(0).getLogin()+"\"");
        friends_chat_label.setIcon(Icons.CHAT);
        friends_chat_label.setToolTipText("Friend's Chat");
        current_activity_panel.add(friends_chat_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel player_count_label = new JLabel(String.valueOf(matchdata.getPlayers().size())+"/"+matchdata.getPartyMembers());
        player_count_label.setIcon(Icons.PLAYERS_ICON);
        player_count_label.setToolTipText("Players");
        current_activity_panel.add(player_count_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel experience_label = new JLabel(matchdata.getRequirement().getExperience());
        experience_label.setIcon(Icons.EXPERIENCE_ICON);
        experience_label.setToolTipText("Experience");
        current_activity_panel.add(experience_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel split_label = new JLabel(matchdata.getRequirement().getSplitType());
        split_label.setIcon(Icons.LOOTBAG_ICON);
        split_label.setToolTipText("Loot Split");
        current_activity_panel.add(split_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        String account_string = matchdata.getRequirement().getAccounts();
        ImageIcon account_image = AccountTypeSelection.valueOf(account_string).getImage();
        JLabel accounts_label = new JLabel(account_string);
        accounts_label.setIcon(account_image);
        accounts_label.setToolTipText("Accounts");
        current_activity_panel.add(accounts_label, ca);
        ca.gridy += 1;

        current_activity_panel.add(Box.createVerticalStrut(1), ca);
        ca.gridy += 1;

        JLabel region_label = new JLabel(matchdata.getRequirement().getRegions());
        region_label.setIcon(Icons.WORLD_ICON);
        region_label.setToolTipText("Match Region");
        current_activity_panel.add(region_label, ca);
        ca.gridy +=1;

        c.gridy += 1;
        mp.add(current_activity_panel, c);

        // post button below activity panel

        if (matchdata.getDiscordInvite() != null){
            JPanel discord_invite_panel = new JPanel();
            discord_invite_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
            discord_invite_panel.setBackground(SUB_BACKGROUND_COLOR);
            discord_invite_panel.setLayout(new GridBagLayout());
            GridBagConstraints cd = new GridBagConstraints();
            cd.weightx = 1;
            cd.fill = GridBagConstraints.HORIZONTAL;
            cd.anchor = GridBagConstraints.CENTER;
            cd.gridx = 0;
            cd.gridy = 0;
            JButton discord_invite_button = new JButton();
            discord_invite_button.setIcon(Icons.DISCORD_ICON);
            discord_invite_button.setToolTipText("Join the group's discord!");
            discord_invite_button.setText("Join Discord");
            discord_invite_button.setBackground(new Color(114,137,218).darker().darker());
            discord_invite_button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    final JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    String message = "Your microphone will be ON at the time of arrival.\n Please mute your microphone now, prior to joining.";
                    String title = "NeverScapeAlone Discord Match ID: "+ matchdata.getId();
                    if (JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, Icons.DISCORD_ICON, new String[]{"JOIN","CANCEL"}, "JOIN") == JOptionPane.YES_OPTION){
                        LinkBrowser.browse(matchdata.getDiscordInvite());
                    };

                }
            });
            discord_invite_panel.add(discord_invite_button, cd);

            c.gridy += 1;
            mp.add(discord_invite_panel, c);
        }

        c.gridy += 1;
        /// end current activity panel
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

            JPanel player_panel = new JPanel();
            player_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
            player_panel.setBackground(SUB_BACKGROUND_COLOR);
            player_panel.setLayout(new GridBagLayout());
            player_panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            GridBagConstraints cp = new GridBagConstraints();
            cp.weightx = 1;
            cp.fill = GridBagConstraints.HORIZONTAL;
            cp.anchor = GridBagConstraints.CENTER;
            cp.gridx = 0;
            cp.gridy = 0;

            if (!Objects.equals(player.getLogin(), plugin.username)) {
                /// if the panel drawn player is not the current player, draw buttons

                JPanel player_name_button_panel = new JPanel();
                player_name_button_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
                player_name_button_panel.setBackground(SUB_BACKGROUND_COLOR);
                player_name_button_panel.setLayout(new GridBagLayout());
                GridBagConstraints pnbp = new GridBagConstraints();
                pnbp.anchor = GridBagConstraints.LINE_END;
                pnbp.gridx = 0;
                pnbp.gridy = 0;

                JButton promote_party_leader = new JButton();
                promote_party_leader.setIcon(Icons.CROWN_ICON);
                promote_party_leader.setToolTipText("Promote " + player.getLogin());
                promote_party_leader.setActionCommand(String.valueOf(player.getUserId()));
                promote_party_leader.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.PROMOTE));
                player_name_button_panel.add(promote_party_leader, pnbp);
                pnbp.gridx += 1;

                JButton like_button = new JButton();
                like_button.setIcon(Icons.LIKE_ICON);
                like_button.setToolTipText("Like " + player.getLogin());
                like_button.setActionCommand(String.valueOf(player.getUserId()));
                ;
                like_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.LIKE));
                player_name_button_panel.add(like_button, pnbp);
                pnbp.gridx += 1;

                JButton dislike_button = new JButton();
                dislike_button.setIcon(Icons.DISLIKE_ICON);
                dislike_button.setToolTipText("Dislike " + player.getLogin());
                dislike_button.setActionCommand(String.valueOf(player.getUserId()));
                dislike_button.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.DISLIKE));
                player_name_button_panel.add(dislike_button, pnbp);
                pnbp.gridx += 1;

                JButton kick = new JButton();
                kick.setIcon(Icons.KICK_ICON);
                kick.setToolTipText("Kick " + player.getLogin());
                kick.setActionCommand(String.valueOf(player.getUserId()));
                kick.addActionListener(e -> plugin.playerOptionAction(e, PlayerButtonOptionEnum.KICK));
                player_name_button_panel.add(kick, pnbp);
                pnbp.gridx += 1;

                player_panel.add(player_name_button_panel, cp);
                cp.gridy += 1;
            }
            //////////////////// end player name button panel

            //////////////////// start name panel
            JPanel player_name_panel = new JPanel();
            player_name_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
            player_name_panel.setBackground(SUB_BACKGROUND_COLOR);
            player_name_panel.setLayout(new GridBagLayout());
            GridBagConstraints pnp = new GridBagConstraints();
            pnp.weightx = 1;
            pnp.fill = GridBagConstraints.HORIZONTAL;
            pnp.anchor = GridBagConstraints.CENTER;
            pnp.gridx = 0;
            pnp.gridy = 0;

            JLabel player_name = new JLabel(player.getLogin());
            player_name.setFont(FontManager.getRunescapeBoldFont());
            if (player.getIsPartyLeader()) {
                player_name.setIcon(Icons.YELLOW_PARTYHAT_ICON);
            } else {
                if (player.getVerified()) {
                    player_name.setIcon(Icons.VERIFIED_ICON);
                } else {
                    player_name.setIcon(Icons.UNVERIFIED_ICON);
                }
            }
            player_name.setToolTipText("ID: " + String.valueOf(player.getUserId()));
            player_name.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    StringSelection selection = new StringSelection(player.getLogin());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                }
            });
            player_name_panel.add(player_name, pnp);
            player_panel.add(player_name_panel, cp);
            cp.gridy += 1;
            //////////////////// end name panel

            if (rating_selected){
                if (player.getRating() != -1){
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    Float aFloat = (float)(player.getRating()/10);
                    JLabel rating_label = new JLabel(String.valueOf(aFloat)+"/5.0");
                    rating_label.setIcon(Icons.RATING_ICON);
                    rating_label.setToolTipText("User rating");
                    rating_label.setFont(FontManager.getRunescapeFont());
                    player_panel.add(rating_label, cp);
                    cp.gridy += 1;
                }
            }

            if (discord_selected){
                if (player.getDiscord() != null) {
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    JLabel discord_label = new JLabel(player.getDiscord());
                    discord_label.setIcon(Icons.DISCORD_ICON);
                    discord_label.setFont(FontManager.getRunescapeFont());
                    player_panel.add(discord_label, cp);
                    cp.gridy += 1;
                }
            }

            if (safety_selected){
                if (player.getRunewatch() != null) {
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    JLabel runewatch_label = new JLabel("RUNEWATCH ALERT");
                    runewatch_label.setToolTipText(player.getRunewatch());
                    runewatch_label.setIcon(Icons.RUNEWATCH_ICON);
                    runewatch_label.setFont(FontManager.getRunescapeFont());
                    runewatch_label.setForeground(Color.red.darker());
                    player_panel.add(runewatch_label, cp);
                    cp.gridy += 1;
                }

                if (player.getWdr() != null) {
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    JLabel wdr_label = new JLabel("WDR ALERT");
                    wdr_label.setToolTipText(player.getWdr());
                    wdr_label.setIcon(Icons.WDR_ICON);
                    wdr_label.setFont(FontManager.getRunescapeFont());
                    wdr_label.setForeground(Color.red.darker());
                    player_panel.add(wdr_label, cp);
                    cp.gridy += 1;
                }
            }

            if (location_selected){
                if (player.getLocation() != null) {
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    JPanel player_location = new JPanel();
                    player_location.setBorder(new EmptyBorder(0, 0, 0, 0));
                    player_location.setBackground(SUB_BACKGROUND_COLOR);
                    player_location.setLayout(new GridBagLayout());
                    GridBagConstraints cs = new GridBagConstraints();
                    cs.weightx = 1;
                    cs.fill = GridBagConstraints.HORIZONTAL;
                    cs.anchor = GridBagConstraints.CENTER;
                    cs.gridx = 0;
                    cs.gridy = 0;

                    int world = player.getLocation().getWorld();
                    int x = player.getLocation().getX();
                    int y = player.getLocation().getY();
                    int regionX = player.getLocation().getRegionX();
                    int regionY = player.getLocation().getRegionY();
                    int regionID = player.getLocation().getRegionID();
                    int plane = player.getLocation().getPlane();

                    JLabel world_label = new JLabel();
                    world_label.setText(String.valueOf(world));
                    world_label.setToolTipText("Player's current world");
                    world_label.setIcon(Icons.WORLD_ICON);
                    cs.gridx = 1;
                    player_location.add(world_label, cs);

                    JLabel coordinate_label = new JLabel();
                    String region = regionReference.get(regionID);
                    if (region == null){
                        region = "Unknown";
                    }
                    coordinate_label.setText(region);
                    coordinate_label.setToolTipText("("+String.valueOf(x)+", "+String.valueOf(y)+")");
                    coordinate_label.setIcon(Icons.COORDINATE_ICON);
                    cs.gridx = 2;
                    player_location.add(coordinate_label, cs);

                    player_panel.add(player_location, cp);
                    cp.gridy += 1;
                }
            }

            if (stats_selected){
                if (player.getStatus() != null) {
                    player_panel.add(Box.createVerticalStrut(3));
                    cp.gridy += 1;

                    JPanel player_status = new JPanel();
                    player_status.setBorder(new EmptyBorder(0, 0, 0, 0));
                    player_status.setBackground(SUB_BACKGROUND_COLOR);
                    player_status.setLayout(new GridBagLayout());
                    GridBagConstraints cs = new GridBagConstraints();
                    cs.weightx = 1;
                    cs.fill = GridBagConstraints.HORIZONTAL;
                    cs.anchor = GridBagConstraints.CENTER;
                    cs.gridx = 0;
                    cs.gridy = 0;

                    Integer base_hitpoints = player.getStatus().getBaseHp();
                    Integer hitpoints = player.getStatus().getHp();
                    Integer prayer = player.getStatus().getPrayer();
                    Integer base_prayer = player.getStatus().getBasePrayer();
                    Integer run_energy = player.getStatus().getRunEnergy();

                    JLabel hitpoint_label = new JLabel(hitpoints + "/" + base_hitpoints);
                    hitpoint_label.setIcon(Icons.HITPOINTS);
                    player_status.add(hitpoint_label, cs);

                    cs.gridx = 1;
                    JLabel prayer_label = new JLabel(prayer + "/" + base_prayer);
                    prayer_label.setIcon(Icons.PRAYER);
                    player_status.add(prayer_label, cs);

                    cs.gridx = 2;
                    JLabel run_label = new JLabel(run_energy + "/100");
                    run_label.setIcon(Icons.AGILITY);
                    player_status.add(run_label, cs);

                    player_panel.add(player_status, cp);
                    cp.gridy += 1;
                }
            }

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

        JToggleButton rating_button = new JToggleButton();
        rating_button.setIcon(Icons.RATING_ICON);
        rating_button.setToolTipText("User Ratings");
        rating_button.setSelected(true);
        rating_button.setBackground(Color.green.darker().darker());
        rating_button.addActionListener(this::ratingButtonListener);
        rating_button.setSize(30, 30);
        headermatchPanel.add(rating_button, sc);

        sc.gridx +=1;

        JToggleButton discord_button = new JToggleButton();
        discord_button.setIcon(Icons.DISCORD_ICON);
        discord_button.setToolTipText("Discord Information");
        discord_button.setSelected(true);
        discord_button.setBackground(Color.green.darker().darker());
        discord_button.addActionListener(this::discordButtonListener);
        discord_button.setSize(30, 30);
        headermatchPanel.add(discord_button, sc);

        sc.gridx +=1;

        JToggleButton location_button = new JToggleButton();
        location_button.setIcon(Icons.WORLD_ICON);
        location_button.setToolTipText("Location Information");
        location_button.setSelected(true);
        location_button.setBackground(Color.green.darker().darker());
        location_button.addActionListener(this::locationButtonListener);
        location_button.setSize(30, 30);
        headermatchPanel.add(location_button, sc);

        sc.gridx +=1;

        JToggleButton safety_button = new JToggleButton();
        safety_button.setIcon(Icons.SAFETY_ICON);
        safety_button.setToolTipText("RuneWatch and WDR Safety");
        safety_button.setSelected(true);
        safety_button.setBackground(Color.green.darker().darker());
        safety_button.addActionListener(this::safetyButtonListener);
        safety_button.setSize(30, 30);
        headermatchPanel.add(safety_button, sc);

        sc.gridx +=1;

        JToggleButton stats_button = new JToggleButton();
        stats_button.setIcon(Icons.HITPOINTS);
        stats_button.setToolTipText("User Stats");
        stats_button.setSelected(true);
        stats_button.setBackground(Color.green.darker().darker());
        stats_button.addActionListener(this::statsButtonListener);
        stats_button.setSize(30, 30);
        headermatchPanel.add(stats_button, sc);

        sc.gridx +=1;
        JButton escape = new JButton();
        escape.setIcon(Icons.LOGOUT_ICON);
        escape.setToolTipText("Exit");
        escape.setSize(30, 30);
        escape.setBorderPainted(false);
        escape.setFocusPainted(false);
        escape.setContentAreaFilled(false);
        escape.addActionListener(this::leaveMatch);
        headermatchPanel.add(escape, sc);

        matchPanel.add(headermatchPanel, c);
        c.gridy += 1;
        matchPanel.add(new JPanel(), c);
        return matchPanel;
    }

    private void ratingButtonListener(ActionEvent actionEvent){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        if (button.isSelected()){
            rating_selected = true;
            button.setBackground(Color.green.darker().darker());
        } else {
            rating_selected = false;
            button.setBackground(Color.red.darker());
        }
    }

    private void discordButtonListener(ActionEvent actionEvent){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        if (button.isSelected()){
            discord_selected = true;
            button.setBackground(Color.green.darker().darker());
        } else {
            discord_selected = false;
            button.setBackground(Color.red.darker());
        }
    }

    private void locationButtonListener(ActionEvent actionEvent){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        if (button.isSelected()){
            location_selected = true;
            button.setBackground(Color.green.darker().darker());
        } else {
            location_selected = false;
            button.setBackground(Color.red.darker());
        }
    }

    private void safetyButtonListener(ActionEvent actionEvent){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        if (button.isSelected()){
            safety_selected = true;
            button.setBackground(Color.green.darker().darker());
        } else {
            safety_selected = false;
            button.setBackground(Color.red.darker());
        }
    }

    private void statsButtonListener(ActionEvent actionEvent){
        JToggleButton button = (JToggleButton) actionEvent.getSource();
        if (button.isSelected()){
            stats_selected = true;
            button.setBackground(Color.green.darker().darker());
        } else {
            stats_selected = false;
            button.setBackground(Color.red.darker());
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
        JButton escape = new JButton();
        escape.setIcon(Icons.CANCEL_ICON);
        escape.setToolTipText("Exit");
        escape.setSize(20, 20);
        escape.setBorderPainted(false);
        escape.setFocusPainted(false);
        escape.setContentAreaFilled(false);
        escape.addActionListener(this::quickPanelManager);
        connectingPanel.add(escape, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy += 1;
        connectingPanel.add(title("Connecting..."), c);
        c.gridy += 1;
        connectingPanel.add(new JLabel("Queue Time: 00:00:00"), c);

        return connectingPanel;
    }

    public void setConnectingPanelQueueTime(String display_text) {
        JLabel label = (JLabel) (connectingPanel.getComponent(2));
        label.setText(display_text);
    }

    public void connectingPanelManager(ActionEvent actionEvent) {
        connectingPanelManager();
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
        quickPanel.add(title("Random"), c);
        c.gridy += 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        quickPanel.add(randomPanel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy += 1;

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
        ActivityReference[] values = ActivityReference.values();
        for (ActivityReference value : values) {
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
            quickMatchButton.setBackground(COLOR_COMPLETED);
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
        createPanel.add(instructionTitle("Step 1: Select an Activity"), c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Skills"), c);
        c.gridy += 1;
        createPanel.add(createskillPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Bosses"), c);
        c.gridy += 1;
        createPanel.add(createbossPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Raids"), c);
        c.gridy += 1;
        createPanel.add(createraidPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Mini-games"), c);
        c.gridy += 1;
        createPanel.add(createminigamePanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Miscellaneous"), c);
        c.gridy += 1;
        createPanel.add(createmiscPanel, c);

        return createPanel;
    }

    private void addCreateButtons() {
        ActivityReference[] values = ActivityReference.values();
        for (ActivityReference value : values) {
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

    private void constructCreatePanels() {
        createskillPanel = subActivityPanel(4, 6);
        createbossPanel = subActivityPanel(7, 6);
        createraidPanel = subActivityPanel(2, 2);
        createminigamePanel = subActivityPanel(6, 6);
        createmiscPanel = subActivityPanel(3, 5);
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

        createPanel2.add(instructionTitle("Step 2: Choose Requirements"), c);

        c.gridy += 1;
        JPanel createSelectionPanel = createSelectionPanel();
        createPanel2.add(createSelectionPanel, c);

        c.gridy += 1;
        createPanel2.add(Box.createVerticalStrut(6), c);

        c.gridy += 1;
        JButton button_confirm = new JButton();
        button_confirm.setBackground(Color.green.darker().darker());
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

        createSelectionPanel.add(header("Group Size"), c);
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
        member_count_help_button.setIcon(Icons.HELP_ICON);
        member_count_help_button.setSize(16, 16);
        member_count_help_button.setToolTipText("Click here for help!");
        member_count_help_button.addActionListener(this::count_help_button_panel);
        member_count_help_button.setBorderPainted(false);
        member_count_help_button.setFocusPainted(false);
        member_count_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(member_count_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Experience"), c);
        c.gridx = 1;
        ((JLabel) experience_level.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(experience_level, c);

        c.gridx = 2;
        c.weightx = 0;
        experience_help_button.setIcon(Icons.EXPERIENCE_ICON);
        experience_help_button.setSize(16, 16);
        experience_help_button.setToolTipText("Click here for help!");
        experience_help_button.addActionListener(this::experience_help_button_panel);
        experience_help_button.setBorderPainted(false);
        experience_help_button.setFocusPainted(false);
        experience_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(experience_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Split Type"), c);
        c.gridx = 1;
        ((JLabel) party_loot.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(party_loot, c);
        c.gridx = 2;
        c.weightx = 0;
        split_help_button.setIcon(Icons.LOOTBAG_ICON);
        split_help_button.setSize(16, 16);
        split_help_button.setToolTipText("Click here for help!");
        split_help_button.addActionListener(this::split_help_button_panel);
        split_help_button.setBorderPainted(false);
        split_help_button.setFocusPainted(false);
        split_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(split_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Accounts"), c);
        c.gridx = 1;
        ((JLabel) account_type.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(account_type, c);
        c.gridx = 2;
        c.weightx = 0;
        accounts_help_button.setIcon(Icons.NSA_ICON);
        accounts_help_button.setSize(16, 16);
        accounts_help_button.setToolTipText("Click here for help!");
        accounts_help_button.addActionListener(this::accounts_help_button_panel);
        accounts_help_button.setBorderPainted(false);
        accounts_help_button.setFocusPainted(false);
        accounts_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(accounts_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Region"), c);
        c.gridx = 1;
        ((JLabel) region.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(region, c);
        c.gridx = 2;
        c.weightx = 0;
        region_help_button.setIcon(Icons.WORLD_ICON);
        region_help_button.setSize(16, 16);
        region_help_button.setToolTipText("Click here for help!");
        region_help_button.addActionListener(this::region_help_button_panel);
        region_help_button.setBorderPainted(false);
        region_help_button.setFocusPainted(false);
        region_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(region_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Passcode"), c);
        c.gridx = 1;
        passcode.setToolTipText("Leave blank for a Public match, DO NOT USE YOUR REAL PASSWORD");
        createSelectionPanel.add(passcode, c);
        c.gridx = 2;
        c.weightx = 0;
        passcode_help_button.setIcon(Icons.PRIVATE_ICON);
        passcode_help_button.setSize(16, 16);
        passcode_help_button.setToolTipText("Click here for help!");
        passcode_help_button.addActionListener(this::passcode_help_button_panel);
        passcode_help_button.setBorderPainted(false);
        passcode_help_button.setFocusPainted(false);
        passcode_help_button.setContentAreaFilled(false);
        createSelectionPanel.add(passcode_help_button, c);
        c.weightx = 1;
        c.gridy += 1;

        return createSelectionPanel;
    }

    private void count_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Group Size Help" + "\n" +
                "The group size string indicates the maximum" + "\n"+
                "number of players that your group should have.";
        JOptionPane.showMessageDialog(frame, message);
    }

    private void experience_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Experience Help" + "\n" +
                "Different experience values can be used to indicate how competent your party is." + "\n" +
                "Note: Anyone can join a party, regardless of the designation." + "\n" +
                "1. Flexible - Anyone, of any experience level, can join." + "\n" +
                "2. Novice - Those with some experience in the activity should join." + "\n" +
                "3. Average - Those that see themselves as average in the activity, should join." + "\n" +
                "4. Experienced - Those with plenty of experience should join in this activity.";
        JOptionPane.showMessageDialog(frame, message);
    }

    private void split_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Split Help" + "\n" +
                "How you would like to split your loot." + "\n" +
                "This can be ignored if you're doing an activity which doesn't require loot splitting." + "\n" +
                "1. FFA - Free for all, everyone picks up their own loot." + "\n" +
                "2. Splits - Everyone splits the loot evenly at the end.";
        JOptionPane.showMessageDialog(frame, message);
    }

    private void accounts_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Account Help" + "\n" +
                "The types of accounts that you would like to have in your party" + "\n" +
                "Note: Anyone can join a party, regardless of the designation." + "\n" +
                "1. All Accounts - Every account type should join." + "\n" +
                "2. Normal - Accounts without special designations should join." + "\n" +
                "3. IM - Regular Ironmen should join." + "\n" +
                "4. HCIM - Hardcore Ironmen should join." + "\n" +
                "5. UIM - Ultimate Ironmen should join.." + "\n" +
                "6. GIM - Group Ironmen should join." + "\n" +
                "7. HCGIM - Hardcore Group Ironmen should join." + "\n" +
                "8. UGIM - Unranked Group Ironmen should join.";
        JOptionPane.showMessageDialog(frame, message);
    }

    private void region_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Region Help" + "\n" +
                "What region would like to set the group in." + "\n" +
                "Note: Anyone can join a party, regardless of the designation.";
        JOptionPane.showMessageDialog(frame, message);
    }

    private void passcode_help_button_panel(ActionEvent actionEvent) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String message = "Passcode Help" + "\n" +
                "Passcode to set for a private match." + "\n" +
                "Leave the passcode field blank in order for your match to be public." + "\n" +
                "-- Rules --" + "\n" +
                "Any string of size <64 characters is allowed, with permitted characters: [A-Za-z0-9_- ]";
        JOptionPane.showMessageDialog(frame, message);
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
            no_matches_label.setForeground(Color.red.darker().darker());
            no_matches_label.setIcon(Icons.CANCEL_ICON);
            no_matches_label.setToolTipText("No matches found with this current search, try again!");
            no_matches_label.setFont(FontManager.getRunescapeBoldFont());

            searchMatchPanel.add(no_matches_label, c);
            return;
        }

        for (SearchMatchData match : searchMatches.getSearchMatches()) {
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

            /// start match block
            String activity = match.getActivity();
            ActivityReference activityReference = ActivityReference.valueOf(activity);
            ImageIcon activity_icon = activityReference.getIcon();
            String activity_name = activityReference.getTooltip();

            JLabel match_title = new JLabel(activity_name);
            match_title.setIcon(activity_icon);
            match_title.setFont(FontManager.getRunescapeBoldFont());
            sMatch.add(match_title, cMatch);
            cMatch.gridx = 1;

            JLabel privateLabel = new JLabel();
            if (match.getIsPrivate()) {
                privateLabel.setText("Private");
                privateLabel.setIcon(Icons.PRIVATE_ICON);
                privateLabel.setForeground(Color.yellow.darker().darker());
            } else {
                privateLabel.setText("Public");
                privateLabel.setIcon(Icons.PUBLIC_ICON);
                privateLabel.setForeground(Color.green.darker().darker());
            }
            privateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            privateLabel.setToolTipText("Match ID: " + match.getId());
            cMatch.anchor = GridBagConstraints.LINE_END;
            cMatch.fill = GridBagConstraints.LINE_END;
            sMatch.add(privateLabel, cMatch);
            cMatch.anchor = GridBagConstraints.CENTER;
            cMatch.fill = GridBagConstraints.HORIZONTAL;
            cMatch.gridx = 0;
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(2), cMatch);
            cMatch.gridy += 1;

            String party_leader = match.getPartyLeader();
            JLabel partyLeader_label = new JLabel(party_leader);
            partyLeader_label.setIcon(Icons.YELLOW_PARTYHAT_ICON);
            partyLeader_label.setToolTipText("The party leader");
            sMatch.add(partyLeader_label, cMatch);
            cMatch.gridy += 1;

            JLabel size_label = new JLabel(match.getPlayerCount()+"/"+match.getPartyMembers());
            size_label.setIcon(Icons.PLAYERS_ICON);
            size_label.setToolTipText("Players");
            sMatch.add(size_label, cMatch);
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            JLabel experience_label = new JLabel(match.getExperience());
            experience_label.setIcon(Icons.EXPERIENCE_ICON);
            experience_label.setToolTipText("Experience");
            sMatch.add(experience_label, cMatch);
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            JLabel split_label = new JLabel(match.getSplitType());
            split_label.setIcon(Icons.LOOTBAG_ICON);
            split_label.setToolTipText("Loot Split");
            sMatch.add(split_label, cMatch);
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            String account_string = match.getAccounts();
            ImageIcon account_image = AccountTypeSelection.valueOf(account_string).getImage();
            JLabel accounts_label = new JLabel(account_string);
            accounts_label.setIcon(account_image);
            accounts_label.setToolTipText("Accounts");
            sMatch.add(accounts_label, cMatch);
            cMatch.gridy += 1;

            sMatch.add(Box.createVerticalStrut(1), cMatch);
            cMatch.gridy += 1;

            JLabel region_label = new JLabel(match.getRegions());
            region_label.setIcon(Icons.WORLD_ICON);
            region_label.setToolTipText("Match Region");
            sMatch.add(region_label, cMatch);
            cMatch.gridy += 1;

            /// end match code
            c.gridy += 1;
            searchMatchPanel.add(Box.createVerticalStrut(5), c);
            c.gridy += 1;

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

    private JPanel title(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
    }

    private JPanel header(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(FontManager.getRunescapeSmallFont());
        label_holder.add(label);
        return label_holder;
    }

    private JPanel instructionTitle(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setForeground(Color.green.darker());
        label_holder.add(label);
        return label_holder;
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